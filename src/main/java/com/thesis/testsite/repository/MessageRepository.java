package com.thesis.testsite.repository;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
