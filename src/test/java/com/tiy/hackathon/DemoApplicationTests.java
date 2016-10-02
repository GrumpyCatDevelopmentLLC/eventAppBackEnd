package com.tiy.hackathon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	EventRepository events;
	@Autowired
	UserRepository users;

    @Autowired
	ContactsRepository contacts;

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

	@Test
	public void testListForUsersCheckedInAtEvent() throws Exception {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();


		user1.email = "walltheusers.com";
		user1.displayName = "qwudup";
		user1.password = "toolate";
		users.save(user1);

		user2.email = "wdatboi@gmail.com";
		user2.displayName = "qmemes";
		user2.password = "yeabuddy";
		users.save(user2);

		user3.email = "wohnooooo.com";
		user3.displayName = "qjk";
		user3.password = "bacchus";
		users.save(user3);

		user4.email = "wrenfest@gmail.com";
		user4.displayName = "qturkeyleg";
		user4.password = "randomar";
		users.save(user4);

		Event everybodysEvent = new Event();
		everybodysEvent.name = "Party people Part 2";
		everybodysEvent.location = "Turner Field";
		everybodysEvent.dateAndTime = "10/2/2016 ~ 4:45 PM";
		everybodysEvent.details = "Bring it back yall bring it back yall here we go";
		events.save(everybodysEvent);

		AttendingEvents eventSave1 = new AttendingEvents(everybodysEvent, user1);
		attendingEvents.save(eventSave1);

		AttendingEvents eventSave2 = new AttendingEvents(everybodysEvent, user2);
		attendingEvents.save(eventSave2);

		AttendingEvents eventSave3 = new AttendingEvents(everybodysEvent, user3);
		attendingEvents.save(eventSave3);

		AttendingEvents eventSave4 = new AttendingEvents(everybodysEvent, user4);
		attendingEvents.save(eventSave4);

//      trying things here

		Iterable<AttendingEvents> eventsFound = attendingEvents.findUsersByEvent(everybodysEvent);
		ArrayList<AttendingEvents> eventList = new ArrayList<AttendingEvents>();
		for (AttendingEvents currentEvent : eventsFound) {
			eventList.add(currentEvent);
		}

		int alSize = eventList.size();


		assertEquals(4, alSize);

//		finish here

		attendingEvents.delete(eventSave1);
		attendingEvents.delete(eventSave2);
		attendingEvents.delete(eventSave3);
		attendingEvents.delete(eventSave4);
		users.delete(user1);
		users.delete(user2);
		users.delete(user3);
		users.delete(user4);
	}

	@Test
	public void testRequestUserInfoAccept() throws Exception {
		User userIntiatiated = new User();
		User userContacted = new User();

		userIntiatiated.email = "hbemd@gmail.com";
		userIntiatiated.displayName = "dehmpc";
		userIntiatiated.password = "jb";
		users.save(userIntiatiated);

		userContacted.email = "trmebtsd@gmail.com";
		userContacted.displayName = "degd3br";
		userContacted.password = "chibbs";
		users.save(userContacted);


		Contacts testContact = new Contacts(userIntiatiated, userContacted);
		testContact.giveInfo = true;
		contacts.save(testContact);

		Iterable <Contacts> contactsFound =  contacts.findByInitialContact(userIntiatiated);
		ArrayList<Contacts> contactsList = new ArrayList<Contacts>();
		for(Contacts contact: contactsFound) {
			contactsList.add(contact);
		}

		Contacts thisContact = contacts.findOne(testContact.id);

		assertTrue(thisContact.giveInfo);


		contacts.delete(testContact);
		users.delete(userIntiatiated);
		users.delete(userContacted);

	}

	@Test
	public void testRequestUserInfoReject() throws Exception {
		User userIntiatiated = new User();
		User userContacted = new User();

		userIntiatiated.email = "binrads@gmail.com";
		userIntiatiated.displayName = "poppers";
		userIntiatiated.password = "jb";
		users.save(userIntiatiated);

		userContacted.email = "shdbow@gmail.com";
		userContacted.displayName = "spizzar";
		userContacted.password = "chibbs";
		users.save(userContacted);


		Contacts testContact = new Contacts(userIntiatiated, userContacted);
		testContact.giveInfo = false;
		contacts.save(testContact);

		Iterable <Contacts> contactsFound =  contacts.findByInitialContact(userIntiatiated);
		ArrayList<Contacts> contactsList = new ArrayList<Contacts>();
		for(Contacts contact: contactsFound) {
			contactsList.add(contact);
		}

		Contacts thisContact = contacts.findOne(testContact.id);

		assertFalse(thisContact.giveInfo);


		contacts.delete(testContact);
		users.delete(userIntiatiated);
		users.delete(userContacted);
	}

 	@Test
	public void testRequestUserInfo() throws Exception {
		User userIntiatiated = new User();
		User userContacted = new User();

		userIntiatiated.email = "testermc@gmail.com";
		userIntiatiated.displayName = "testy";
		userIntiatiated.password = "geez";
		users.save(userIntiatiated);

		userContacted.email = "iyao@gmail.com";
		userContacted.displayName = "gah";
		userContacted.password = "usthesedays";
		users.save(userContacted);


		Contacts testContact = new Contacts(userIntiatiated, userContacted);
//		ensuring it auto sets boolean to false
		contacts.save(testContact);

		Iterable <Contacts> contactsFound =  contacts.findByInitialContact(userIntiatiated);
		ArrayList<Contacts> contactsList = new ArrayList<Contacts>();
		for(Contacts contact: contactsFound) {
			contactsList.add(contact);
		}

		Contacts thisContact = contacts.findOne(testContact.id);

		assertFalse(thisContact.giveInfo);


		contacts.delete(testContact);
		users.delete(userIntiatiated);
		users.delete(userContacted);
	}

	@Test
	public void testPeopleIHaveTriedToContact() throws Exception {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();
		User user5 = new User();
		User userInt = new User();

		user1.email = "tme.com";
		user1.displayName = "meept";
		user1.password = "fwbw";
		users.save(user1);

		user2.email = "tewia@gmail.com";
		user2.displayName = "tot";
		user2.password = "onedance";
		users.save(user2);

		user3.email = "ttuek@yahoo.com";
		user3.displayName = "hptcomt";
		user3.password = "sag";
		users.save(user3);

		user4.email = "tnmifme@gmail.com";
		user4.displayName = "Stickt";
		user4.password = "reply";
		users.save(user4);

		user5.email = "titwy@gmail.com";
		user5.displayName = "Ghwyt";
		user5.password = "wat";
		users.save(user5);

		userInt.email = "tiwantallthefriends@gmail.com";
		userInt.displayName = "Like mee";
		userInt.password = "friendsaregreat";
		users.save(userInt);


		Contacts testContact = new Contacts(userInt, user1);
		Contacts testContact2 = new Contacts(userInt, user2);
		Contacts testContact3 = new Contacts(userInt, user3);
		Contacts testContact4 = new Contacts(userInt, user4);
		Contacts testContact5 = new Contacts(userInt, user5);
		contacts.save(testContact);
		contacts.save(testContact2);
		contacts.save(testContact3);
		contacts.save(testContact4);
		contacts.save(testContact5);


		Iterable <Contacts> contactsFound =  contacts.findByInitialContact(userInt);
		ArrayList<Contacts> contactsList = new ArrayList<Contacts>();
		for(Contacts contact: contactsFound) {
			contactsList.add(contact);
		}


		int alSize = contactsList.size();

		assertEquals(5, alSize);


		contacts.delete(testContact);
		contacts.delete(testContact2);
		contacts.delete(testContact3);
		contacts.delete(testContact4);
		contacts.delete(testContact5);
		users.delete(user1);
		users.delete(user2);
		users.delete(user3);
		users.delete(user4);
		users.delete(user5);
		users.delete(userInt);
	}

	@Test
	public void testPeopleThatHaveTriedToContactMe() throws Exception {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();
		User user5 = new User();
		User userInt = new User();

		user1.email = "stme.com";
		user1.displayName = "smeept";
		user1.password = "fwbw";
		users.save(user1);

		user2.email = "stewia@gmail.com";
		user2.displayName = "stot";
		user2.password = "onedance";
		users.save(user2);

		user3.email = "sttueks@yahoo.com";
		user3.displayName = "shptcomts";
		user3.password = "sag";
		users.save(user3);

		user4.email = "stnmifme@gmail.com";
		user4.displayName = "Stickts";
		user4.password = "reply";
		users.save(user4);

		user5.email = "stitwy@gmail.com";
		user5.displayName = "Ghwyts";
		user5.password = "wat";
		users.save(user5);

		userInt.email = "stiwantallthefriends@gmail.com";
		userInt.displayName = "Like mees";
		userInt.password = "friendsaregreat";
		users.save(userInt);


		Contacts testContact = new Contacts(userInt, user1);
		Contacts testContact2 = new Contacts(user2, user1);
		Contacts testContact3 = new Contacts(user3, user1);
		Contacts testContact4 = new Contacts(user4, user1);
		Contacts testContact5 = new Contacts(user5, user1);
		contacts.save(testContact);
		contacts.save(testContact2);
		contacts.save(testContact3);
		contacts.save(testContact4);
		contacts.save(testContact5);


		Iterable <Contacts> contactsFound =  contacts.findByContacted(user1);
		ArrayList<Contacts> contactsList = new ArrayList<Contacts>();
		for(Contacts contact: contactsFound) {
			contactsList.add(contact);
		}


		int alSize = contactsList.size();

		assertEquals(5, alSize);

		contacts.delete(testContact);
		contacts.delete(testContact2);
		contacts.delete(testContact3);
		contacts.delete(testContact4);
		contacts.delete(testContact5);
		users.delete(user1);
		users.delete(user2);
		users.delete(user3);
		users.delete(user4);
		users.delete(user5);
		users.delete(userInt);
	}

}
