package br.com.todo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.todo.demo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findByLogin(String login);
}
