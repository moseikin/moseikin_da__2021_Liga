package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolService schoolService;

    public String addUser(Usr usr) {
        Long schoolNumber = usr.getSchool().getNumber();
        schoolService.findSchoolByNumber(schoolNumber, usr.getSchool().getAddress());
        userRepository.save(usr);
        return "added: " + usr;
    }

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
