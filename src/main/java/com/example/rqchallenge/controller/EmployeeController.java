package com.example.rqchallenge.controller;

import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.exception.EmployeeNotFoundException;
import com.example.rqchallenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1")
public class EmployeeController implements IEmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employees = new ArrayList<>();

    @Override
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        String name = (String) employeeInput.get("name");
        int age = (Integer) employeeInput.get("age");
        int salary = (Integer) employeeInput.get("salary");
        Employee newEmployee = new Employee(name, age, salary);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @Override
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found.");
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
        return ResponseEntity.ok(employee);
    }

    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        List<Employee> employees = employeeRepository.findByName(searchString);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found with name containing: " + searchString);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        Optional<Integer> highestSalary = employees.stream()
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder());
        return highestSalary.map(ResponseEntity::ok)
                .orElseThrow(() -> new EmployeeNotFoundException("No employees found to determine highest salary."));
    }

    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employees = employeeRepository.findAll();

        List<String> topTenHighestEarningEmployeeNames = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());

        if (topTenHighestEarningEmployeeNames.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found with top ten highest Salary.");
        }
        return ResponseEntity.ok(topTenHighestEarningEmployeeNames);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Employee with ID " + id + " was successfully deleted.");
    }
}
