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
//    public ArrayList<Event> login(HttpSession session, String email, String password) throws Exception {
        User newUser = users.findFirstByEmail(user.email);
        if (newUser == null) {
            myResponse.errorMessage = "User does not exist or was input incorrectly";
        } else if (!user.password.equals(newUser.getPassword())) {
            myResponse.errorMessage = "Incorrect password";
        } else if(newUser != null && newUser.password.equals(newUser.getPassword())) {
			System.out.println(user.email + " is logging in");
			session.setAttribute("user", newUser);
//        return getMyEvents();
//        return getAllEvents();
			myResponse.responseUser = newUser;
		}
		return myResponse;
    }

//    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
//    public User login(HttpSession session, @RequestBody User user) throws Exception {
////    public ArrayList<Event> login(HttpSession session, String email, String password) throws Exception {
//        user = users.findFirstByEmail(user.email);
//        if (user == null) {
//            throw new Exception("User does not exist or was input incorrectly");
//
//        } else if (!user.password.equals(user.getPassword())) {
//            throw new Exception("Incorrect password");
//        }
//        session.setAttribute("user", user);
//
////        return getMyEvents();
////        return getAllEvents();
//        return user;
//    }

    @RequestMapping(path = "/logout.json", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        session.invalidate();

    }

    @RequestMapping(path = "/createUser.json", method = RequestMethod.POST)
    public UserResponseContainer newUser(HttpSession session, @RequestBody User user) throws Exception{
//        public ArrayList<Event> newUser(HttpSession session, String email, String displayName, String password) throws Exception{
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
    public ArrayList<Event> newEvent(HttpSession session, @RequestBody Event thisEvent) throws Exception{
        User user = (User) session.getAttribute("user");

        thisEvent = new Event(thisEvent.name, thisEvent.location, thisEvent.dateAndTime, thisEvent.details);
//        thisEvent.user = user;


        System.out.println("My runtime repo: " + thisEvent.toString());
        events.save(thisEvent);

//        return getMyEvents();
        return getAllEvents();
    }

    @RequestMapping(path = "/saveEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> saveEvent(HttpSession session, @RequestBody Event event) throws Exception{
//        event = (Event) session.getAttribute("event");
//
//        event.name = name;
//        event.location = location;
//        event.dateAndTime = dateAndTime;
//        event.details = details;

        System.out.println("My runtime repo: " + event.toString());
        events.save(event);

//        return getMyEvents();
        return getAllEvents();
    }

    @RequestMapping(path = "/deleteEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> deleteEvent(HttpSession session) throws Exception{
        Event event = (Event) session.getAttribute("event");

        System.out.println("My runtime repo to delete event: " + event.toString());
        events.delete(event);

//        return getMyEvents();
        return getAllEvents();
    }


//    @RequestMapping(path = "/myEvents", method = RequestMethod.POST)
//    public ArrayList<Event> myEvents(HttpSession session) throws Exception{
//
//        return getMyEvents();
//    }


    @RequestMapping(path = "/allEvents.json", method = RequestMethod.POST)
    public ArrayList<Event> allEvents(HttpSession session) throws Exception{

        return getAllEvents();
    }

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


//    ArrayList<Event> getMyEvents() {
//        ArrayList<Event> eventList = new ArrayList<Event>();
//        Iterable<Event> allEvents = events.findByUser(user);
//
//        if (user != null){
//            for (Event currentEvent : allEvents) {
//                eventList.add(currentEvent);
//            }
//        }
//        return eventList;
//    }

    ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findAll();

        if (user != null){
            for (Event currentEvent : allEvents) {
                eventList.add(currentEvent);
            }
        }
        return eventList;
    }

    @RequestMapping(path = "/allUsers.json", method = RequestMethod.GET)
    public List<User> allUsers() {
        Iterable<User> userIterable = users.findAll();
        ArrayList<User> myUsers = new ArrayList<>();

        for (User myUser : userIterable) {
            myUsers.add(myUser);
        }

        return myUsers;
    }
//    @RequestMapping(path = "/myContacts.json", method = RequestMethod.POST)
//    public ArrayList<Contacts> allFriends(HttpSession session) throws Exception{
//    User user = (User) session.getAttribute("user");
//        return getAllMyContacts();
//    }


//    ArrayList<Contacts> getAllMyContacts() {
//        ArrayList<Contacts> friendList = new ArrayList<Contacts>();
//        Iterable<Contacts> allContacts = contacts.findByUser(user);
//
//        if (user != null){
//            for (Contacts currentFriend : allContacts) {
//                friendList.add(currentFriend);
//            }
//        }
//        return friendList;
//    }

    @RequestMapping(path = "/usersAtEvent.json", method = RequestMethod.POST)
    public ArrayList<User> attendingEvent(HttpSession session) throws Exception{
//        User user = (User) session.getAttribute("user");
        Event event = (Event) session.getAttribute("event");
        return getAllAttendees();
    }

    ArrayList<User> getAllAttendees() {
        ArrayList<User> attendeeList = new ArrayList<User>();
        Iterable<User> allUsersAtEvent = attendingEvents.findUsersByEvent(event);
        if (user != null){
            for (User currentUser : allUsersAtEvent) {
                attendeeList.add(currentUser);
            }
        }
        return attendeeList;
    }

    @RequestMapping(path = "/checkIn.json", method = RequestMethod.POST)
    public ArrayList<User> checkInAtEvent(HttpSession session) throws Exception{
        User user = (User) session.getAttribute("user");
        Event event = (Event) session.getAttribute("event");

        AttendingEvents aEvent = new AttendingEvents(event, user);

        attendingEvents.save(aEvent);

        return getAllAttendees();
    }

}
