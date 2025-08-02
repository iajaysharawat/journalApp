package com.infy.project.Repository;


import com.infy.project.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
