package com.masters.waterways.controller;

import com.masters.waterways.daos.*;
import com.masters.waterways.models.FoodBooking;
import com.masters.waterways.models.Users;
import com.masters.waterways.models.Voyage;
import com.masters.waterways.models.VoyageUserView;
import com.masters.waterways.services.AuthenticationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller

public class UserController {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private VoyageDao voyageDao;

    @Autowired
    private RoomBookingDao roomBookingDao;

    @Autowired
    private FoodItemDao foodItemDao;
    
    @Autowired
    private FoodBookingDao foodBookingDao;

    @Autowired
    private VoyageUserViewDao voyageUserViewDao;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TransactionDao transactionDao;
    
    @GetMapping("/user")
    public String userhome(Model model, HttpSession session){
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        int id = authenticationService.getCurrentUser(session);
        Users user = usersDao.getById(id);
        model.addAttribute("user",user);
        return "UserHome";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        model.addAttribute("user", usersDao.getById(authenticationService.getCurrentUser(session)));
        return "Profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, HttpSession session) {
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        model.addAttribute("user", usersDao.getById(authenticationService.getCurrentUser(session)));
        return "EditProfile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("user") Users user, Model model, HttpSession session) {
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        user.setUserId(authenticationService.getCurrentUser(session));
        usersDao.update(user);
        return "redirect:/profile";
    }


    @GetMapping("/user/booking/{id}")
    public String booking(@PathVariable("id") int voyageId, HttpSession session){
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        roomBookingDao.bookRoomByVoyageIdAndUserId(voyageId, authenticationService.getCurrentUser(session));
        return "redirect:/voyages/{id}";
    }
    
    @GetMapping("/user/mybookings")
    public String mybookings(Model model, HttpSession session){

        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        model.addAttribute("my_completed_voyages", voyageUserViewDao.getAllCompletedByUserId(authenticationService.getCurrentUser(session)));
        model.addAttribute("my_upcoming_voyages", voyageUserViewDao.getAllFutureByUserId(authenticationService.getCurrentUser(session)));
        return "MyVoyageList";
    }

    @GetMapping("/user/voyage/{voyageId}/room/{roomId}")
    public String foodBookingForm(@PathVariable("voyageId") int voyageId, @PathVariable("roomId") int roomId, Model model, HttpSession session){


        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        FoodBooking foodBooking = new FoodBooking();
        foodBooking.setRoomId(roomId);
        foodBooking.setVoyageId(voyageId);



        model.addAttribute("foodBooking", foodBooking);
        model.addAttribute("foodItemList", foodItemDao.getAllByVoyageId(voyageId));
        return "FoodBookingForm";
    }

    @PostMapping("/user/voyage/{voyageId}/room/{roomId}/bookfood")
    public String foodBookingForRoom (@PathVariable("voyageId") int voyageId, @PathVariable("roomId") int roomId, @ModelAttribute("foodBooking")FoodBooking foodBooking,
                                      HttpSession session
    ) {
        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }

        foodBooking.setRoomId(roomId);
        foodBooking.setVoyageId(voyageId);

        foodBookingDao.bookFood(authenticationService.getCurrentUser(session), foodBooking);
        return "redirect:/voyages/{voyageId}";
    }

    @GetMapping("/user/transactions")
    public String myTransactions(Model model, HttpSession session){

        if (!authenticationService.isAuthenticated(session)) {
            return"redirect:/login";
        }
//        int userId=session_key;

        model.addAttribute("transactions", transactionDao.getAllByUserId(authenticationService.getCurrentUser(session)));
        return "UserTransactionList"; // will direct to VoyageDetailsUser
    }
}
