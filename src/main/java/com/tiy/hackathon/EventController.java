package com.tiy.hackathon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Brett on 9/29/16.
 */

@Controller
public class EventController {

	@Autowired
	UserRepository users;

	@Autowired
	EventRepository events;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(path = "/newUser", method = RequestMethod.POST)
	public String newUser(HttpSession session, String email, String displayName, String password) {
	User user = users.findFirstByEmail(email);
		if (user == null) {
			user = new User(email, displayName, password);
			users.save(user);
		}
		return "redirect:/";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, String email, String password) throws Exception {
		User user = users.findFirstByEmail(email);
		if (user == null) {
			throw new Exception("User does not exist!");
		} else if (!password.equals(user.getPassword())) {
			throw new Exception("Password is incorrect");
		}
		session.setAttribute("user", user);
		return "redirect:/events";
	}

	@RequestMapping(path = "/events", method = RequestMethod.GET)
	public String events() {
		return "/events";
	}
}
