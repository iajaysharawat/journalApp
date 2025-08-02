package com.infy.project.Repository;

import com.infy.project.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);
    void deleteByUsername(String username);
}
