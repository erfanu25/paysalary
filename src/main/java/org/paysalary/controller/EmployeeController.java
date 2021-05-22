package org.paysalary.controller;

import org.paysalary.domain.Employee;
import org.paysalary.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employee")
    public List<Employee> employees() {
        return employeeService.getEmployeeList();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteUser(@PathVariable long id) {
        employeeService.delete(id);
    }

    @PostMapping("/employee")
    public ResponseEntity<Object> createUser(@Valid @RequestBody Employee employee) {
        if(!employeeService.employeeIdExist(employee.getEmployeeId())) {
            return ResponseEntity.ok(employeeService.saveEmployee(employee));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("This Employee ID Already Used");
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody Employee employee, @PathVariable long id) {
        return ResponseEntity.ok(employeeService.updateEmployee(employee,id));
    }
}
