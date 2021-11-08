package com.thesis.testsite.repository;

import com.thesis.testsite.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.username = ?1"/*, nativeQuery = true*/)
    User findByUsername(String username);

    @Query(value = "select u from User u where u.id = ?1"/*, nativeQuery = true*/)
    //select * from users where id = ?1
    User getUserByMessageId(Long id);

    List<User> findAll();

    @Modifying
    @Transactional
    @Query(value = "update users set password = ?2 where username = ?1", nativeQuery = true)
    void changePassword(String userName, String password);


    @Modifying
    @Transactional
    @Query(value = "update users set failed_attempt = ?1 where username = ?2", nativeQuery = true)
    void updateFailedAttempts(int failAttempts, String username);
}
