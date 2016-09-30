package com.tiy.hackathon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by bearden-tellez on 9/29/16.
 */
@RestController
public class EventJsonController {
    @Autowired
     EventRepository events;
    @Autowired
     UserRepository users;

//    @Autowired
//     ContactRepository contacts;

    User user;


    @RequestMapping(path = "/createAdmin.json", method = RequestMethod.POST)
    public User adminUser(HttpSession session) throws Exception {
        User theAdmin = new User();

//        theAdmin.id = 1;
        theAdmin.email = "Admin@gmail.com";
        theAdmin.displayName = "Admin";
        theAdmin.password = "admin";

        users.save(theAdmin);
        return theAdmin;
    }


    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public ArrayList<Event> login(HttpSession session, String email, String password) throws Exception {
        User user = users.findFirstByEmail(email);
        if (user == null) {
            throw new Exception("User does not exist or was input incorrectly");

        } else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
//        return "redirect:/myEvents";
        return getMyEvents();
    }

    @RequestMapping(path = "/logout.json", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        session.invalidate();
//        return "redirect:/";
    }

    @RequestMapping(path = "/createUser.json", method = RequestMethod.POST)
    public User newUser(HttpSession session, String email, String displayName, String password) throws Exception{
        User user = users.findFirstByEmail(email);
        if (user == null) {
            user = new User(email, displayName, password);
            users.save(user);
        }
        session.setAttribute("user", user);
//        return "redirect:/myEvents";
        return user;
    }

    @RequestMapping(path = "/createEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> newEvent(HttpSession session, String name, String location, String dateAndTime, String details) throws Exception{
        User user = (User) session.getAttribute("user");

        Event thisEvent = new Event(name, location, dateAndTime, details);
        thisEvent.user = user;


        System.out.println("My runtime repo: " + thisEvent.toString());
        events.save(thisEvent);

        return getMyEvents();
    }

    @RequestMapping(path = "/saveEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> saveEvent(HttpSession session, String name, String location, String dateAndTime, String details) throws Exception{
        Event event = (Event) session.getAttribute("event");

        event.name = name;
        event.location = location;
        event.dateAndTime = dateAndTime;
        event.details = details;

        System.out.println("My runtime repo: " + event.toString());
        events.save(event);

        return getMyEvents();
    }

    @RequestMapping(path = "/deleteEvent.json", method = RequestMethod.POST)
    public ArrayList<Event> deleteEvent(HttpSession session) throws Exception{
        Event event = (Event) session.getAttribute("event");

        System.out.println("My runtime repo to delete event: " + event.toString());
        events.delete(event);

        return getMyEvents();
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
    public String clickedUser(HttpSession session, int userID) throws Exception {
        User findUser = users.findOne(userID);

        return (findUser.displayName);
    }


    ArrayList<Event> getMyEvents() {
        ArrayList<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findByUser(user);

        if (user != null){
            for (Event currentEvent : allEvents) {
                eventList.add(currentEvent);
            }
        }
        return eventList;
    }

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
//    @RequestMapping(path = "/myContacts.json", method = RequestMethod.POST)
//    public ArrayList<Contact> allFriends(HttpSession session) throws Exception{
//    User user = (User) session.getAttribute("user");
//        return getAllMyContacts();
//    }
//
//
//    ArrayList<Event> getAllMyContacts() {
//        ArrayList<Contact> friendList = new ArrayList<Contact>();
//        Iterable<Contact> allFriends = events.findAll(); //change this to a whatever we name our find friend by user
//
//        if (user != null){
//            for (Contact currentFriend : allFriends) {
//                friendList.add(currentFriend);
//            }
//        }
//        return friendList;
//    }

}
