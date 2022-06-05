package com.jumbo.store.service;

import com.jumbo.store.model.Role;

public interface RoleService {

    Role findByName(String name);

    Role save(Role role);
}
