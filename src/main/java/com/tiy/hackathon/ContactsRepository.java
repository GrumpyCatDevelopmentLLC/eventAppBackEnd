package com.tiy.hackathon;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bearden-tellez on 10/1/16.
 */
public interface ContactsRepository extends CrudRepository<Contacts, Integer> {
   ArrayList<Contacts> findByInitialContact(User initialContact);
}