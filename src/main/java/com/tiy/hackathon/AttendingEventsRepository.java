package com.tiy.hackathon;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by bearden-tellez on 10/1/16.
 */
public interface AttendingEventsRepository extends CrudRepository<Event, Integer> {

}