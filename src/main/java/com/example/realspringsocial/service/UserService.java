package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final SchoolService schoolService;

    public Iterable<Usr> allUsers(){
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String addUser(Usr usr) {
        School schoolFound = null;
        if (usr.getSchool() != null) {
            Long schoolNumber = usr.getSchool().getNumber();
            schoolFound = schoolService.findSchoolByNumber(schoolNumber, usr.getSchool().getAddress());
        }

        if (schoolFound != null) {
            usr.setSchool(schoolFound);
        }
        userRepository.save(usr);
        return "added: " + usr;
    }

    @Transactional
    public String editUser(Usr usrNewData) {
        Optional<Usr> optional = userRepository.findById(usrNewData.getId());
        if (optional.isPresent()){
            Usr usr = optional.get();
            if (usrNewData.getName() != null) {
                usr.setName(usrNewData.getName());
            }
            if (usrNewData.getLastName() != null) {
                usr.setLastName(usrNewData.getLastName());
            }
            if (usrNewData.getAge() != null) {
                usr.setAge(usrNewData.getAge());
            }

            if (usrNewData.getSchool() != null) {
                schoolService.findSchoolByNumber(usrNewData.getSchool().getNumber(),
                                                usrNewData.getSchool().getAddress());
                usr.setSchool(usrNewData.getSchool());
            }
            userRepository.save(usr);
            return "edited: " + usr;
        } else {
            return "not found";
            // или можно завести нового юзера
        }
    }

    @Transactional
    public String deleteUser(String id) {
        Long idLong = Long.valueOf(id);
        Optional<Usr> optional = userRepository.findById(idLong);
        if (optional.isPresent()) {
            userRepository.delete(optional.get());
            return "deleted";
        } else {
            return "not found";
        }
    }
}
