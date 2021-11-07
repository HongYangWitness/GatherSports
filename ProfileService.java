package com.example.sportgather.service;

import com.example.sportgather.domain.User;
import com.example.sportgather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private static UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> queryAll(){
        List<User> list = userRepository.findAll();
        return list;
    }

    public static List<User> queryUserById(String id) {
        List<User> users = userRepository.findUserById(id);
        return users;
    }

    public static String modifyInfo(String lastName, String gender, Integer age, String phone, String location, String type, String id) {
        userRepository.updateInfo(lastName, gender, age, phone, location, type, id);
        return "update info done";
    }
}
