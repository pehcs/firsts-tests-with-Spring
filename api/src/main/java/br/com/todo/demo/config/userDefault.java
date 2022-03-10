package br.com.todo.demo.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class userDefault {
    
    public static void main(String[] args){
        System.out.println("sua senha: "+new BCryptPasswordEncoder().encode("admin"));
    }
}
