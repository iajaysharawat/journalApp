package com.infy.project.service;

import com.infy.project.Repository.UserRepository;
import com.infy.project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user=userRepository.findByUsername("Ram");
        assertTrue(!user.getJournalEntries().isEmpty());
        assertNotNull(userRepository.findByUsername("Ram"));

    }

}
