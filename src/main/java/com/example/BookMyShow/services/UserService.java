package com.example.BookMyShow.services;

import com.example.BookMyShow.models.User;
import com.example.BookMyShow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User logIn(String emailId, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordStoredInDB = optionalUser.get().getPassword();
        return optionalUser.get();
    }
    public User signUp(String emailId, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        //if user is present goto login else follow sign up workflow
        if(optionalUser.isPresent()) {
            return logIn(emailId, password);
        }
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail(emailId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }
}
