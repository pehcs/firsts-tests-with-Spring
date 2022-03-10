package br.com.todo.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
// import org.springframework.stereotype.Service;

import br.com.todo.demo.models.User;
import br.com.todo.demo.repositories.UserRepository;

@Repository
public class UserServices implements UserDetailsService{

    @Autowired
    private UserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        User user = ur.findByLogin(login);

        if(user == null){
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return user;
    }
    
    
}
