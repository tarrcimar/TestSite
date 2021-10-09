package com.thesis.testsite.repository;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();

    @Modifying
    @Transactional
    @Query(value = "delete from messages where id = ?1", nativeQuery = true)
    void deleteMessageById(Long messageId);

}
