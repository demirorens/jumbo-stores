package com.jumbo.store.service.impl;

import com.jumbo.store.model.Role;
import com.jumbo.store.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final MongoTemplate mongoTemplate;

    /**
     * Method that finds role by name from database
     * @param name
     * @return
     */
    @Override
    public Role findByName(String name) {
        Query query =
                new BasicQuery("{name: '" + name + "'}");
        Role role = mongoTemplate.findOne(query, Role.class);
        return role;
    }

    /**
     * Method saves the role to the database
     * @param role
     * @return
     */
    @Override
    public Role save(Role role) {
        return mongoTemplate.save(role);
    }
}
