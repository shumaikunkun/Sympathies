package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findById(String id);
    List<User> findByMail(String mail);
    List<User> findByPassward(String passward);
    List<User> findByMailAndPassward(String mail, String passward);
}
