package br.com.todo.demo.controllers;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/api/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository er;
    
    @GetMapping
    public List<Employee> listRegisteredEmployees(){
        return er.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> listRegisteredEmployee(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        Optional<Employee> employeeOptional = er.findById(id);
        var employeeData = employeeOptional.get();
        // String user = userDetails.getUsername();
        return ResponseEntity.status(HttpStatus.OK).body(employeeData);
    }

    @PostMapping
    public ResponseEntity<Object> formRegister(@RequestBody Employee employee){
        try{

            return ResponseEntity.status(HttpStatus.CREATED).body(er.save(employee));
        }catch(Error e){
            System.out.println("Trigger to send email with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API not available");
        }
    }

    @PutMapping("/{id}")
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
            er.save(updatedEmployee);
            Employee updatedEmployeeResponse = updatedEmployee;
            return ResponseEntity.ok(updatedEmployeeResponse);
        }catch(Error e){
            System.out.println("Trigger to send email with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API not available");
        }
    }

    @DeleteMapping("{id}")
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
