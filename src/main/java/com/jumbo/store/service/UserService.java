package com.jumbo.store.service;

import com.jumbo.store.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsernameOrEmail(String userNameOrEmail);

    Optional<User> findById(String userNameOrEmail);

    User save(User user);

}
