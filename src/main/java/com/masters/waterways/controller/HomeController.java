package com.masters.waterways.controller;

import com.masters.waterways.daos.*;
import com.masters.waterways.models.*;
import com.masters.waterways.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import com.masters.waterways.models.VoyageStatusProvider;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private VoyageDao voyageDao;

	@Autowired
	private HarborDao harborDao;

	@Autowired
	private RoomBookingDao roomBookingDao;

	@Autowired
	FoodBookingDao foodBookingDao;

	@Autowired
	RoomBookingDetailsDao roomBookingDetailsDao;

	@Autowired
	FoodItemDao foodItemDao;

	@Autowired
	VoyageVerboseTransform voyageVerboseTransform;

	@Autowired
	VoyageUserViewDao voyageUserViewDao;

	@GetMapping("/")
	public String home(){
		return "Home";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("newUser", new Users());
		return "Signup";
	}

	@PostMapping("/signup")
	public String signedup(@ModelAttribute("newUser") Users newUser) {
		// Authorisation and validation should implement here in future.
		usersDao.insert(newUser);
		return "redirect:/login";
	}

	@GetMapping("/voyages")
	public String voyagesList (Model model,
			@RequestParam(name="fromid", required = false) Integer from_harbour_id,
			@RequestParam(name="toid", required = false) Integer to_harbour_id,
			@RequestParam(name="fromdate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime depart_after_datetime,
			@RequestParam(name="todate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrive_before_datetime
	) {

		List<VoyageUserView> voyages = voyageUserViewDao.getAllFutureOperational();

		for (VoyageUserView v: voyages)
			System.out.println(v.getArrivalHarborId());

		if (from_harbour_id != null && from_harbour_id != -1) {
			List<VoyageUserView> new_voyages = new ArrayList<>();
			for (VoyageUserView v: voyages)
				if (from_harbour_id.equals(v.getDepartureHarborId()))
					new_voyages.add(v);
			voyages = new_voyages;
		}

		if (to_harbour_id != null && to_harbour_id != -1) {
			List<VoyageUserView> new_voyages = new ArrayList<>();
			for (VoyageUserView v: voyages)
				if (to_harbour_id.equals(v.getArrivalHarborId()))
					new_voyages.add(v);
			voyages = new_voyages;
		}

		if (depart_after_datetime != null) {
			List<VoyageUserView> new_voyages = new ArrayList<>();
			for (VoyageUserView v: voyages)
				if (v.getDepartureTime().isAfter(depart_after_datetime))
					new_voyages.add(v);
			voyages = new_voyages;
		}

		if (arrive_before_datetime != null) {
			List<VoyageUserView> new_voyages = new ArrayList<>();
			for (VoyageUserView v: voyages)
				if (v.getArrivalTime().isBefore(arrive_before_datetime))
					new_voyages.add(v);
			voyages = new_voyages;
		}


		model.addAttribute("upcoming_voyages", voyages);
		model.addAttribute("harbors", harborDao.getAll());

		return "VoyageListHomeAndUser";
	}


	@GetMapping("/voyages/{id}")
	public String voyageDetails (@PathVariable("id") int voyageId, Model model, HttpSession session) {

//		List<Voyage> voyage = new ArrayList<>();
//		voyage.add(voyageDao.getById(voyageId));
//		List<VoyageVerbose> voyageVerbose = voyageVerboseTransform.transform(voyage);

		VoyageUserView voyage = voyageUserViewDao.getById(voyageId);
		model.addAttribute("voyage", voyage);
		model.addAttribute("voyageStatuses", VoyageStatusProvider.getVoyageStatusDesc);

		boolean isCompleted=voyageDao.isVoyageCompletedByVoyageId(voyageId);

		boolean signedIn = false;

		if (authenticationService.isAuthenticated(session)) {
			signedIn = true;
			model.addAttribute("room_booking_details_with_foods", roomBookingDetailsDao.getAllByUserIdAndVoyageId(authenticationService.getCurrentUser(session), voyageId));
			model.addAttribute("fooditems", foodItemDao.getAllByVoyageId(voyageId));
		}

		model.addAttribute("signedIn", signedIn );
		model.addAttribute("isCompleted", isCompleted);

		return "VoyageDetailsUserAndHome";
	}




}
