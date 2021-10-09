package com.thesis.testsite.repository;

import com.thesis.testsite.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from users where username = ?1", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "select * from users where id = ?1", nativeQuery = true)
    User getUserByMessageId(Long id);

    List<User> findAll();

    @Modifying
    @Transactional
    @Query(value = "update users set password = ?2 where username = ?1", nativeQuery = true)
    void changePassword(String userName, String password);
}
