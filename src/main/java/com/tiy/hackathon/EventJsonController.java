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

    User user;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String email, String password) throws Exception {
        User user = users.findFirstByEmail(email);
        if (user == null) {
            throw new Exception("User does not exist or was input incorrectly");
//            return "redirect:/login";

        } else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
        return "redirect:/myEvents";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        session.invalidate();
//        return "redirect:/";
    }

    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public ArrayList<Event> newUser(HttpSession session, String email, String displayName, String password) throws Exception{ //should this be any al
        User user = users.findFirstByEmail(email);
        if (user == null) {
            user = new User(email, displayName, password);
            users.save(user);
        }
        session.setAttribute("user", user);
//        return "redirect:/myEvents";
        return getMyEvents();
    }

    @RequestMapping(path = "/createEvent", method = RequestMethod.POST)
    public ArrayList<Event> newEvent(HttpSession session, String name, String location, String dateAndTime, String details) throws Exception{
        User user = (User) session.getAttribute("user");

        Event thisEvent = new Event(name, location, dateAndTime, details);
        thisEvent.user = user;


        System.out.println("My runtime repo: " + thisEvent.toString());
        events.save(thisEvent);

        return getMyEvents();
    }

    @RequestMapping(path = "/saveEvent", method = RequestMethod.POST)
    public ArrayList<Event> saveEvent(HttpSession session, @RequestBody Event event) throws Exception{
//  add stuff ---------
        return getMyEvents();
    }

    @RequestMapping(path = "/myEvents", method = RequestMethod.POST)
    public ArrayList<Event> myEvents(HttpSession session) throws Exception{

        return getMyEvents();
    }


    @RequestMapping(path = "/allEvents", method = RequestMethod.POST)
    public ArrayList<Event> allEvents(HttpSession session) throws Exception{

        return getAllEvents();
    }

    @RequestMapping(path= "/profile", method = RequestMethod.POST)
    public ArrayList<User> thisUser(HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        
        return (user);
    }

    @RequestMapping(path= "/userInfo", method = RequestMethod.POST)
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

}
