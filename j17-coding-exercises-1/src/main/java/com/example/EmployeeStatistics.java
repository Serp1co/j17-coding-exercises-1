package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private String department;
    private double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isJunior() {
        return this.salary < 5900;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}

public class EmployeeStatistics {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeStatistics.class);

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 5000),
                new Employee("Roberto", "HR", 4000),
                new Employee("Carlo", "IT", 6000),
                new Employee("Davide", "Finance", 7000),
                new Employee("Enrico", "Finance", 8000),
                new Employee("Luisa", "IT", 5500),
                new Employee("Gino", "Finance", 5200),
                new Employee("Sandro", "IT", 6500),
                new Employee("Leila", "IT", 5600),
                new Employee("Giorgia", "HR", 5800)
        );

        // grouped by department
        Map<String, Long> departmentCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        logger.info("Counts of departments: {}", departmentCounts);

        // grouped by avarage salary
        Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        logger.info("Avarage salary: {}", averageSalaries);

        // partition based on seniority
        Map<Boolean, List<Employee>> partitionBySeniority = employees.stream()
                .collect(Collectors.partitioningBy(Employee::isJunior));
        logger.info("Junior Employees: {}", partitionBySeniority.get(true));
        logger.info("Senior Employees: {}", partitionBySeniority.get(false));


    }
}
