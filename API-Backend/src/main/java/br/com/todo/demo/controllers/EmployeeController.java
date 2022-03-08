package br.com.todo.demo.controllers;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todo.demo.models.Employee;
import br.com.todo.demo.repositories.EmployeeRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository er;

    @GetMapping("/employees")
    public List<Employee> listRegisteredEmployee(){
        return er.findAll();
    }

    @PostMapping("/employees")
    public ResponseEntity<String> formRegister(@RequestBody Employee employee){
        try{
            er.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created resource successfully");
        }catch(Error e){
            System.out.println("Trigger to send email with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API not available");
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable UUID id, @RequestBody Employee employeeData){

        try{
            Optional<Employee> optionalEmployee = er.findById(id);
            Employee updatedEmployee = optionalEmployee.get();
    
            if(!optionalEmployee.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
            }
    
            updatedEmployee.setEmail(employeeData.getEmail());
            updatedEmployee.setFirstName(employeeData.getFirstName());
            updatedEmployee.setLastName(employeeData.getLastName());
    
            Employee updatedEmployeeResponse = updatedEmployee;
    
            return ResponseEntity.ok(updatedEmployeeResponse);
        }catch(Error e){
            System.out.println("Trigger to send email with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API not available");
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable UUID id){
        try{
            Optional<Employee> optionalEmployee = er.findById(id);
            Employee updatedEmployee = optionalEmployee.get();
    
            if(!optionalEmployee.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
            }
            er.delete(updatedEmployee);
    
            return ResponseEntity.status(HttpStatus.OK).body("The resource deleted successfully");
        }catch(Error e){
            System.out.println("Trigger to send email with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API not available");
        }
    }

}
