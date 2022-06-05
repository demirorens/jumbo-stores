package com.jumbo.store.service.impl;

import com.jumbo.store.model.User;
import com.jumbo.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method finds user by the given parameter which is email or username
     * @param userNameOrEmail
     * @return
     */
    @Override
    public Optional<User> findByUsernameOrEmail(String userNameOrEmail) {
        Query query =
                new BasicQuery("{$or:[ {username: '" + userNameOrEmail + "'}," +
                        " {email:'" + userNameOrEmail + "'} ] }");
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    /**
     * Method finds user by id
     * @param id
     * @return
     */
    @Override
    public Optional<User> findById(String id) {
        Query query =
                new BasicQuery("{id: '" + id + "'}");
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    /**
     * Method saves user
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mongoTemplate.save(user);
    }


}
