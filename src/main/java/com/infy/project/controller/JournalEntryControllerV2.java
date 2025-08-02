package com.infy.project.controller;

import com.infy.project.entity.JournalEntry;
import com.infy.project.entity.User;
import com.infy.project.service.JournalEntryService;
import com.infy.project.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


     @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String userName=authentication.getName();
         User user=userService.findByUserName(userName);
         List<JournalEntry> all=user.getJournalEntries();
         if(all!=null && !all.isEmpty()){
             return new ResponseEntity<>(all, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
         journalEntryService.saveEntry( myEntry ,userName);
         return new ResponseEntity<>(myEntry, HttpStatus.CREATED);}
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
       List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());//user kinlist me se ye ye id nikalege
       if(!collect.isEmpty()){
           Optional<JournalEntry> journalEntry= journalEntryService.getById(id);
           if(journalEntry.isPresent()){
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
           }
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String userName=authentication.getName();
          journalEntryService.deleteById(id,userName);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());//user kinlist me se ye ye id nikalege
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry= journalEntryService.getById(id);
            if(journalEntry.isPresent()){
                JournalEntry oldEntry=journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle():oldEntry.getTitle() );
                oldEntry.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }







}
