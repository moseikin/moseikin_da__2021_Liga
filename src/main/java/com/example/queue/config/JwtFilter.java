package com.example.queue.config;

import com.example.queue.Constants;
import com.example.queue.entities.CustomUserDetails;
import com.example.queue.entities.User;
import com.example.queue.entities.requests.AuthRequest;
import com.example.queue.services.CustomUserDetailsService;
import com.example.queue.services.UserService;
import com.example.queue.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;

    private final RequestMatcher authRequestMatcher = new AntPathRequestMatcher("/auth");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (authRequestMatcher.matches((HttpServletRequest) servletRequest)) {
            // если ulr - страница аутентификации ("/auth"), ищем  логин и пароль в теле запроса.
            // в контроллере @RequestBody (required = false) AuthRequest request - т.к. тело до контроллера не дойдет
            AuthRequest authRequest = new JsonUtil().getAuthRequest(servletRequest.getInputStream());

            // из AuthRequest берем логин и пароль и ищем в БД.
            // Если нашли, генерируем токен. Если нет, возвращаем пустую строку
            String token = createToken(authRequest);
            if (!token.isBlank()){
                setAuthentication(token);
                putTokenToHeader(token, servletResponse);
            }
        } else {
            // для любой другой страницы ищем токен в заголовках запроса
            String tokenFromRequest = ((HttpServletRequest) servletRequest).getHeader(Constants.AUTHORIZATION);
            if (tokenFromRequest != null) {
                setAuthentication(tokenFromRequest);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String createToken(AuthRequest authRequest) {
        if (authRequest != null) {
            User user = userService.findByLoginAndPassword(authRequest);
            if (user != null) {
                return jwtProvider.generateToken(user.login());
            }
        }
        return "";
    }

    // добавить токен в заголовки ответа
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