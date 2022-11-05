package com.masters.waterways.controller;

import com.masters.waterways.daos.EmployeeDao;
import com.masters.waterways.daos.UsersDao;
import com.masters.waterways.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminEmployeeController {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private EmployeeDao employeeDao;


    @GetMapping("/admin/employee")
    public String listEmployee(Model model) {
        model.addAttribute("empployeestatuses", EmployeeStatusProvider.getEmployeeStatusDesc);
        model.addAttribute("employees", employeeDao.getAll());
        return "EmployeeList";
    }

    @GetMapping("/admin/employee/add")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employeestatuses", EmployeeStatusProvider.getEmployeeStatusDesc);
        model.addAttribute("newemployee", new Employee());
        model.addAttribute("users", usersDao.getAll());
        return "AddShipForm";
    }

    @PostMapping("/admin/employee/add")
    public String saveEmployee(@ModelAttribute("newemployee") Employee newemployee) {
        employeeDao.makeEmployee(newemployee);
        return "redirect:/admin/employee";
    }

    @GetMapping("/admin/employee/update/{id}")
    public String editemployeeform(@PathVariable int id, Model model) {
        model.addAttribute("employeestatuses", EmployeeStatusProvider.getEmployeeStatusDesc);
        model.addAttribute("employee", employeeDao.getById(id));
        return "UpdateEmployeeForm";
    }

    @PostMapping("/admin/employee/update/{id}")
    public String updateemployee(@PathVariable int id,
                             @ModelAttribute("employee") Employee employee,
                             Model model) {
        employeeDao.update(employee);
        return "redirect:/admin/employee";
    }





}