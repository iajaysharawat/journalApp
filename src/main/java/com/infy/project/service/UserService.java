package com.infy.project.service;

import com.infy.project.Repository.JournalEntryRepository;
import com.infy.project.Repository.UserRepository;
import com.infy.project.entity.JournalEntry;
import com.infy.project.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
       return userRepository.findAll();
    }

    public void saveNewEntry(User user){
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);}
        catch(Exception e){
            log.error("Exception:",e);
        }
    }
    public void saveNewAdmin(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);}
        catch(Exception e){
            log.error("Exception:",e);
        }
    }

    public void saveUser(User user){
        try{

            userRepository.save(user);}
        catch(Exception e){
            log.error("Exception:",e);
        }
    }
    public User findByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }


    public void saveAdmin(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);}
        catch(Exception e){
            log.error("Exception:",e);
        }
    }
}
