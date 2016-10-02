package com.tiy.hackathon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bearden-tellez on 9/29/16.
 */
@RestController
public class EventJsonController {
    @Autowired
     EventRepository events;
    @Autowired
     UserRepository users;

    @Autowired
     ContactsRepository contacts;

    @Autowired
     AttendingEventsRepository attendingEvents;



    User user;
    Event event;


    @RequestMapping(path = "/createAdmin.json", method = RequestMethod.POST)
    public User adminUser(HttpSession session) throws Exception {
        User theAdmin = new User();


        theAdmin.email = "Admin@gmail.com";
        theAdmin.displayName = "Admin";
        theAdmin.password = "admin";

        users.save(theAdmin);
        return theAdmin;
    }

    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public UserResponseContainer login(HttpSession session, @RequestBody User user) throws Exception {
    	UserResponseContainer myResponse = new UserResponseContainer();
        User newUser = users.findFirstByEmail(user.email);
        if (newUser == null) {
            myResponse.errorMessage = "User does not exist or was input incorrectly";
        } else if (!user.password.equals(newUser.getPassword())) {
            myResponse.errorMessage = "Incorrect password";
        } else if(newUser != null && newUser.password.equals(newUser.getPassword())) {
			System.out.println(user.email + " is logging in");
			session.setAttribute("user", newUser);
			myResponse.responseUser = newUser;
		}
		return myResponse;
    }


    @RequestMapping(path = "/logout.json", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        session.invalidate();

    }

    @RequestMapping(path = "/createUser.json", method = RequestMethod.POST)
    public UserResponseContainer newUser(HttpSession session, @RequestBody User user) throws Exception{
		UserResponseContainer myResponse = new UserResponseContainer();
        User newUser = users.findFirstByEmail(user.email);
		System.out.println(user.email + " is trying to get created");
		if (newUser == null) {
            user = new User(user.email, user.displayName, user.password);
			System.out.println("Creating user with username: " + user.email + " display name: " + user.displayName + " and password: " + user.password + ".");
			users.save(user);
			myResponse.responseUser = user;
			session.setAttribute("user", user);
		} else {
			myResponse.errorMessage = "User already exists";
		}
        return myResponse;
    }

    @RequestMapping(path = "/createEvent.json", method = RequestMethod.POST)
    public EventResponseContainer newEvent(HttpSession session, @RequestBody Event thisEvent) throws Exception{
        User user = (User) session.getAttribute("user");
		EventResponseContainer myResponse = new EventResponseContainer();

        thisEvent = new Event(thisEvent.name, thisEvent.location, thisEvent.dateAndTime, thisEvent.details);
        System.out.println("My runtime repo: " + thisEvent.toString());
        events.save(thisEvent);

		System.out.println("Creating event");

        myResponse.responseEventContainer = getAllEvents();
		System.out.println("Returning list of events");
		return myResponse;
    }

    @RequestMapping(path = "/saveEvent.json", method = RequestMethod.POST)
    public EventResponseContainer saveEvent(HttpSession session, @RequestBody Event event) throws Exception{
    	EventResponseContainer myResponse = new EventResponseContainer();
        System.out.println("My runtime repo: " + event.toString());
        events.save(event);

        myResponse.responseEventContainer = getAllEvents();
		return myResponse;
    }

    @RequestMapping(path = "/deleteEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> deleteEvent(HttpSession session) throws Exception{
        Event event = (Event) session.getAttribute("event");

        System.out.println("My runtime repo to delete event: " + event.toString());
        events.delete(event);

        return getAllEvents();
    }


    @RequestMapping(path = "/allEvents.json", method = RequestMethod.POST)
    public EventResponseContainer allEvents(HttpSession session) throws Exception{
		EventResponseContainer myResponse = new EventResponseContainer();

        ArrayList<Event> myEvents = getAllEvents();
		for (Event myEvent : myEvents) {
			myResponse.responseEventContainer.add(myEvent);
		}
		System.out.println("returning list of events");
		return myResponse;
    }

    // can the user be saved in a cache?
    @RequestMapping(path= "/profile.json", method = RequestMethod.POST)
    public User thisUsersProfile(HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");

        return (user);
    }

    @RequestMapping(path= "/userInfo.json", method = RequestMethod.POST)
    public String clickedUser(HttpSession session, @RequestBody User user) throws Exception {
        User findUser = users.findOne(user.id);

        return (findUser.displayName);
    }

    ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findAll();

            for (Event currentEvent : allEvents) {
                eventList.add(currentEvent);

        }
        return eventList;
    }

    //get rid of?
    @RequestMapping(path = "/allUsers.json", method = RequestMethod.GET)
    public List<User> allUsers() {
        Iterable<User> userIterable = users.findAll();
        ArrayList<User> myUsers = new ArrayList<>();

        for (User myUser : userIterable) {
            myUsers.add(myUser);
        }

        return myUsers;
    }
    @RequestMapping(path = "/myContacts.json", method = RequestMethod.POST)
    public ContactResponseContainer allContacts(HttpSession session) throws Exception{
    User user = (User) session.getAttribute("user");
    ContactResponseContainer contactResponse = new ContactResponseContainer();

        ArrayList<Contacts> contactList = getAllMyContacts();
        int contactSize = contactList.size();

        if (contactSize == 0){
            contactResponse.errorMessage = "User does not have any contacts....";
        }else {
            contactResponse.contactsALContainer = contactList;
            System.out.println(user.displayName + "contacts should be displaying momentarily");
        }
        return contactResponse;
    }

    ArrayList<Contacts> getAllMyContacts() {
        ArrayList<Contacts> friendList = new ArrayList<Contacts>();
        Iterable<Contacts> allContacts = contacts.findByInitialContact(user); //user from session should be one initializing so I think this works

        if (user != null) {
            for (Contacts currentFriend : allContacts) {
                if (currentFriend.giveInfo == true)
                    friendList.add(currentFriend);
            }
        }
        return friendList;
    }

    @RequestMapping(path = "/peopleIInitializedContactWith.json", method = RequestMethod.POST)
    public ContactResponseContainer triedToContact(HttpSession session) throws Exception{
        User user = (User) session.getAttribute("user");
        ContactResponseContainer contactResponse = new ContactResponseContainer();

        ArrayList<Contacts> contactList = getPeopleIInitializedContactWith();
        int contactSize = contactList.size();

        if (contactSize == 0){
            contactResponse.errorMessage = "User has not tried to initialize contact with other people.";
        }else {
            contactResponse.contactsALContainer = contactList;
            System.out.println(user.displayName + "contacts they have tried to contact should display momentarily");
        }
        return contactResponse;
    }

    ArrayList<Contacts> getPeopleIInitializedContactWith() {
        ArrayList<Contacts> friendList = new ArrayList<Contacts>();
        Iterable<Contacts> allContacts = contacts.findByInitialContact(user); //user from session should be one initializing so I think this works

        if (user != null) {
            for (Contacts currentFriend : allContacts) {
                friendList.add(currentFriend);
            }
        }
        return friendList;

    }

    @RequestMapping(path = "/peopleThatHaveInitializedContactWithMe.json", method = RequestMethod.POST)
    public ContactResponseContainer triedToContactMe(HttpSession session) throws Exception{
        User user = (User) session.getAttribute("user");
        ContactResponseContainer contactResponse = new ContactResponseContainer();

        ArrayList<Contacts> contactList = getPeopleThatWantMyInfo();
        int contactSize = contactList.size();

        if (contactSize == 0){
            contactResponse.errorMessage = "People have not tried to contact you.";
        }else {
            contactResponse.contactsALContainer = contactList;
            System.out.println(user.displayName + "people that have tried to contact this user should display momentarily");
        }
        return contactResponse;
    }

    ArrayList<Contacts> getPeopleThatWantMyInfo() {
        ArrayList<Contacts> wannabeFriendList = new ArrayList<Contacts>();
        Iterable<Contacts> allContacts = contacts.findByContacted(user);

        if (user != null) {
            for (Contacts currentFriend : allContacts) {
                wannabeFriendList.add(currentFriend);
            }
        }
        return wannabeFriendList;
    }



//    @RequestMapping(path = "/usersAtEvent.json", method = RequestMethod.POST)
//    public ArrayList<User> attendingEvent(HttpSession session) throws Exception{
////        User user = (User) session.getAttribute("user");
//        Event event = (Event) session.getAttribute("event");
//        return getAllAttendees();
//    }

//    ArrayList<User> getAllAttendees() {
//        ArrayList<User> attendeeList = new ArrayList<User>();
//        Iterable<User> allUsersAtEvent = attendingEvents.findUsersByEvent(event);
//        if (user != null){
//            for (User currentUser : allUsersAtEvent) {
//                attendeeList.add(currentUser);
//            }
//        }
//        return attendeeList;
//    }

//    @RequestMapping(path = "/checkIn.json", method = RequestMethod.POST)
//    public ArrayList<User> checkInAtEvent(HttpSession session) throws Exception{
//        User user = (User) session.getAttribute("user");
//        Event event = (Event) session.getAttribute("event");
//
//        AttendingEvents aEvent = new AttendingEvents(event, user);
//
//        attendingEvents.save(aEvent);
//
//        return getAllAttendees();
//    }

	@RequestMapping(path = "/getListOfEvents.json", method = RequestMethod.GET)
	public EventResponseContainer getListOfEvents() {
		EventResponseContainer myResponse = new EventResponseContainer();
		ArrayList<Event> gotEvents = getAllEvents();
		for(Event myEvent : gotEvents) {
			myResponse.responseEventContainer.add(myEvent);
		}
		return myResponse;
	}

}
