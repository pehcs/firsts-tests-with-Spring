package br.com.todo.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.todo.demo.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    
}
