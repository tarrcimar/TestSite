package com.thesis.testsite.repository;

import com.thesis.testsite.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from users where username = ?1", nativeQuery = true)
    User findByUsername(String username);
}
