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
		User tada = new User();

		testingEvent.name = "A reason for Celebration";
		testingEvent.location = "Trading Places";
		testingEvent.dateAndTime = "1/2/1989 ~ 5:55 PM";
		testingEvent.details= "";

		events.save(testingEvent);

		tada.email = "tada@gmail.com";
		tada.displayName = "Baow!";
		tada.password = "herpderp";



		
		int eventID = testingEvent.getId();

		Event retrievedEvent = events.findOne(eventID);

		assertEquals(testingEvent.id, retrievedEvent.id);












		events.delete(testingEvent);
	}
//
//	@Test
//	public void testListForUsersCheckedInAtEvent() throws Exception {
//
//	}
//
//	@Test
//	public void testRequestUserInfo() throws Exception {
//
//	}
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
