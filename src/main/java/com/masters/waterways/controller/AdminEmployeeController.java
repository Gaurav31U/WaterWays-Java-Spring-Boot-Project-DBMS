package com.masters.waterways.controller;

import com.masters.waterways.daos.EmployeeDao;
import com.masters.waterways.daos.UsersDao;
import com.masters.waterways.models.*;
import com.masters.waterways.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminEmployeeController {
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/admin/employee")
    public String listEmployee(Model model, HttpSession session) {

        if (!authenticationService.isAdmin(session))
            return "redirect:/login";

        model.addAttribute("employeestatuses", EmployeeStatusProvider.getEmployeeStatusDesc);
        model.addAttribute("employees", employeeDao.getAll());
//        model.addAttribute("status", status)
        return "EmployeeList";
    }

    @GetMapping("/admin/employee/add")
    public String createEmployeeForm(Model model, HttpSession session) {

        if (!authenticationService.isAdmin(session))
            return "redirect:/login";
        model.addAttribute("employeestatuses", EmployeeStatusProvider.getEmployeeStatusCode);
        model.addAttribute("newemployee", new Employee());
        model.addAttribute("users", usersDao.getAllNonEmployees());
        return "AddEmployeeForm";
    }

    @PostMapping("/admin/employee/add")
    public String saveEmployee(@ModelAttribute("newemployee") Employee newemployee, HttpSession session) {

        if (!authenticationService.isAdmin(session))
            return "redirect:/login";

        employeeDao.makeEmployee(newemployee);
        return "redirect:/admin/employee";
    }

    @GetMapping("/admin/employee/{id}/update")
    public String updateEmployeeStatus(@PathVariable int id, Model model, HttpSession session) {

        if (!authenticationService.isAdmin(session))
            return "redirect:/login";

        Employee employee=employeeDao.getById(id);
        if (EmployeeStatusProvider.getEmployeeStatusDesc.get(employee.getEmployeeStatusCode()).equals("SUSPENDED")){
            employeeDao.setActive(id);
        }
        else {
            employeeDao.setSuspended(id);
        }
        return "redirect:/admin/employee";
    }

}
