package com.example.queue.config;

import com.example.queue.Constants;
import com.example.queue.utils.MultiReadHttpServletRequest;
import com.example.queue.entities.CustomUserDetails;
import com.example.queue.entities.User;
import com.example.queue.entities.requests.AuthRequest;
import com.example.queue.services.CustomUserDetailsService;
import com.example.queue.services.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean{
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        // тело запроса может быть прочитано единожды, после чего оно теряется и не доходит до контроллера
        // решение: враппер, который копирует inputstream to outputstream
        // таким образом будем работать со вторым
        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);

        // ищем в запросе логин и пароль
        boolean isSucceed = parseRequestForAuthData(multiReadRequest.getInputStream(), servletResponse);

        // если в запросе нет логина и пароля либо user не найден
        if (!isSucceed) {
            // ищем в запросе токен
            HttpServletRequest request = (HttpServletRequest) multiReadRequest.getRequest();
            String tokenFromRequest = request.getHeader(Constants.AUTHORIZATION);
            if (tokenFromRequest != null) {
                setAuthentication(tokenFromRequest);
            }
        }
        filterChain.doFilter(multiReadRequest, servletResponse);
    }

    private boolean parseRequestForAuthData(InputStream inputStream, ServletResponse servletResponse) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));
            int c = 0;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            Gson gson = new Gson();
            AuthRequest authRequest = gson.fromJson(String.valueOf(stringBuilder), AuthRequest.class);
            if (authRequest != null) {
                User user = userService.findByLoginAndPassword(authRequest);

                if(user != null) {
                    String token = jwtProvider.generateToken(user.login());
                    setAuthentication(token);

                    putTokenToResponseBody(token, servletResponse);
                    putTokenToHeader(token, servletResponse);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    // добавить токен в тело ответа
    private void putTokenToResponseBody(String token, ServletResponse servletResponse) throws IOException {
        byte[] bytes = token.getBytes(StandardCharsets.UTF_8);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    // добавить токен в заголовки ответа. Выбрать что-то одно
    private void putTokenToHeader(String token, ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader(Constants.AUTHORIZATION, token);
    }

    public void setAuthentication(String token) {
        if (token != null && jwtProvider.validateToken(token)) {
            String login = jwtProvider.getLoginFromToken(token);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}