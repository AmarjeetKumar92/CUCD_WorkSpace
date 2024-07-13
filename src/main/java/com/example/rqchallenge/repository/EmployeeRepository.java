package com.example.rqchallenge.repository;

import com.example.rqchallenge.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:searchString%")
    List<Employee> findByName(@Param("searchString") String searchString);
}
