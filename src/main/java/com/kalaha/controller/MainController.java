package com.kalaha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = "http://localhost:8090")
@Controller
public class MainController {
	
	  @RequestMapping(value="/", method = RequestMethod.GET)
	    public String welcome() {
	        return "welcome";
	    }

}
