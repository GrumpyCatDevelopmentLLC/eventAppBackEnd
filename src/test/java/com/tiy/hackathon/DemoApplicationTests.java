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
		User tester = new User();

		tester.email = "Unittester@gmail.com";
		tester.displayName = "OMG Unit Tester";
		tester.password = "theBestTesterInTheWorld";

		users.save(tester);

		int userID = tester.getId();

		
		assertEquals();
	}

	@Test
	public void testCreateUser() throws Exception {

	}

	@Test
	public void testCheckInForUser() throws Exception {

	}

	@Test
	public void testEditEvent() throws Exception {

	}

	@Test
	public void testListForUsersAtEvent() throws Exception {

	}

	@Test
	public void testGetMyFriends() throws Exception {

	}

}
