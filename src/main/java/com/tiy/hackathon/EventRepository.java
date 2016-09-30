package com.tiy.hackathon;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Brett on 9/29/16.
 */
public interface EventRepository  extends CrudRepository <Event, Integer>{
    List<Event> findByUser(User user);
}
