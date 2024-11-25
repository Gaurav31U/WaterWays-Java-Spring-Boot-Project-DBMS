package com.masters.waterways.controller;

import com.masters.waterways.daos.UsersDao;
import com.masters.waterways.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHomeContoller {

    @Autowired
    UsersDao usersDao;

    @Autowired
    AuthenticationService authenticationService;
    // TO BE IMPLEMENTED PROPERLY IF
    @GetMapping("/admin")
    public String adminHome(HttpSession session) {

        if (!authenticationService.isAdmin(session))
            return "redirect:/login";

        return "AdminHome";
    }

}
