package com.tiy.hackathon;

import com.sun.java.accessibility.util.EventID;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Brett on 9/29/16.
 */
public interface EventRepository  extends CrudRepository <Event, Integer>{
//    List<Event> findByUser(User user);
    Event findById(int id);
}
