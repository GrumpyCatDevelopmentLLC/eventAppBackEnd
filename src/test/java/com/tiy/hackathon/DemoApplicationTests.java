package com.tiy.hackathon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	EventRepository events;
	@Autowired
	UserRepository users;

//    @Autowired
//     ContactRepository contacts;

	@Autowired
	AttendingEventsRepository attendingEvents;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testCreateEvent() throws Exception {
		Event testingEvent = new Event();

		testingEvent.name = "Take the long way home";
		testingEvent.location = "Jackson State";
		testingEvent.dateAndTime = "11/27/2017 ~ 10:30 PM";
		testingEvent.details= "A light hearted story of a girl and her pets.";

		events.save(testingEvent);

		int eventID = testingEvent.getId();

		Event retrievedEvent = events.findOne(eventID);

		assertEquals(testingEvent.id, retrievedEvent.id);

		events.delete(testingEvent);
	}

	@Test
	public void testCreateUser() throws Exception {
		User tester = new User();

		tester.email = "guccimane@gmail.com";
		tester.displayName = "Yellow Diamonds";
		tester.password = "theBestTesterInTheWorld";

		users.save(tester);

		int userID = tester.getId();

		User retrievedUser = users.findFirstByEmail("guccimane@gmail.com");

		assertEquals(tester.id, retrievedUser.id);

		users.delete(tester);
	}


	@Test
	public void testCreateUserThatAlreadyExists() throws Exception {
		boolean thrown = false;
		try {
			User tester = new User();

			tester.email = "Admin@gmail.com";
			tester.displayName = "Admin";
			tester.password = "admin";

			users.save(tester);

		} catch (DataIntegrityViolationException exception) {
			thrown = true;
		}
			assertTrue(thrown);
	}

	@Test
	public void testEnteringAnEmptyFieldForEvent() throws Exception {
		boolean thrown = false;
		try {
			User tester = new User();

			tester.email = "Admin@gmail.com";
			tester.displayName = "Admin";
			tester.password = "admin";

			users.save(tester);

		} catch (DataIntegrityViolationException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testEnteringAnEmptyFieldForUser() throws Exception {
		boolean thrown = false;
		try {
			User tester = new User();

			tester.email = null;
			tester.displayName = "Admin";
			tester.password = "admin";

			users.save(tester);

		} catch (DataIntegrityViolationException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	@Test
	public void testEditEvent() throws Exception {
		Event testingEvent = new Event();

		testingEvent.name = "Don't Drown";
		testingEvent.location = "Atlantis";
		testingEvent.dateAndTime = "12/13/2016 ~ 4:00 AM";
		testingEvent.details= "Exploration of the lost city";

		events.save(testingEvent);


		Event retEvent = events.findOne(testingEvent.id);


		retEvent.name = "Under the sea adventures";
		retEvent.location = "Atlantis";
		retEvent.dateAndTime = "12/13/2016 ~ 3:00 AM";
		retEvent.details = "Exploration of the lost city";

		events.save(retEvent);

		assertEquals(testingEvent.id, retEvent.id);
		System.out.println(testingEvent.name +" " + retEvent.name);

		events.delete(testingEvent);
	}

	@Test
	public void testCheckInForUser() throws Exception {
		Event testingEvent = new Event();
		User tb = new User();

		testingEvent.name = "Bey & Jay-z";
		testingEvent.location = "Philps Arena";
		testingEvent.dateAndTime = "6/25/2019 ~ 1:00 PM";
		testingEvent.details= "Sign ups";

		events.save(testingEvent);

		tb.email = "working?@gmail.com";
		tb.displayName = "Halp maaaa";
		tb.password = "yoquierotacobell";
		users.save(tb);

		AttendingEvents testEvent = new AttendingEvents(testingEvent, tb);

		attendingEvents.save(testEvent);


		Iterable<AttendingEvents> eventsFound = attendingEvents.findUsersByEvent(testingEvent);

		ArrayList<AttendingEvents> eventList = new ArrayList<AttendingEvents>();
		for (AttendingEvents currentEvent : eventsFound) {
				eventList.add(currentEvent);
			}

		AttendingEvents thisEvent = attendingEvents.findOne(testEvent.id);

		assertEquals(tb.email, eventList.get(0).user.email);

		attendingEvents.delete(testEvent);
		events.delete(testingEvent);
		users.delete(tb);
	}

//	@Test //do we need this test????
//	public void testListForUsersCheckedInAtEvent() throws Exception {
//
//	}
//
	@Test
	public void testRequestUserInfo() throws Exception {

	}
//
//	@Test
//	public void testRequestUserInfoAccept() throws Exception {
//
//	}
//
// 	@Test
//	public void testRequestUserInfoReject() throws Exception {
//
//	}
//
}
