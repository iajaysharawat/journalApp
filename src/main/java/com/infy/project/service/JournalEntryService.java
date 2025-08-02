package com.infy.project.service;

import com.infy.project.Repository.JournalEntryRepository;
import com.infy.project.Repository.UserRepository;
import com.infy.project.entity.JournalEntry;
import com.infy.project.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public List<JournalEntry> getAll() {
       return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user=userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);

        userService.saveNewEntry(user);

        }
        catch(Exception e){
            log.error("Exception:",e);
            throw new RuntimeException("An error occurred while saving journal entry",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String userName){
        try {
            User user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);

            }
        }catch (Exception e){
            System.out.println("Exception:"+e);
            throw new RuntimeException("An error occurred while deleting journal entry",e);
        }

    }

    public User findByUserName(String userName){
       return  userRepository.findByUsername(userName);
    }






}
