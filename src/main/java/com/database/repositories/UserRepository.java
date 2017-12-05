package com.database.repositories;
import org.springframework.data.repository.CrudRepository;

import com.database.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
    public User findById(int Id);
    public User findByUserName(String Username);
}