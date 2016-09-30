package com.tiy.hackathon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	EventRepository events;
	@Autowired
	UserRepository users;

//    @Autowired
//     ContactRepository contacts;


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
	public void testEditEvent() throws Exception {
		Event testingEvent = new Event();

		testingEvent.name = "Don't Drown";
		testingEvent.location = "Atlantis";
		testingEvent.dateAndTime = "12/13/2016 ~ 4:00 AM";
		testingEvent.details= "Exploration of the lost city";

		events.save(testingEvent);

		int eventID = testingEvent.getId();

		Event retrievedEvent = events.findOne(eventID);











		events.delete(testingEvent);
	}

	@Test
	public void testCheckInForUser() throws Exception {

	}

	@Test
	public void testListForUsersCheckedInAtEvent() throws Exception {

	}

	@Test
	public void testGetMyFriends() throws Exception {

	}

}
