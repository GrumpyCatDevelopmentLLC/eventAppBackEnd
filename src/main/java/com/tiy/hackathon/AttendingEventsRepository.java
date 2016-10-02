package com.tiy.hackathon;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by bearden-tellez on 10/1/16.
 */
public interface AttendingEventsRepository extends CrudRepository<AttendingEvents, Integer> {
//    ArrayList<User> findUsersByEvent(Event event);
    ArrayList<AttendingEvents> findUsersByEvent(Event event);
    ArrayList<User> findUserByEvent(Event event);
}