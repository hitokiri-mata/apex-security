package com.apex.web.security.service;

import java.util.List;

import com.apex.web.security.domain.Role;

public interface RoleService {

    List<Role> getByPrincipal(String name);

    Role getById(Long id);

}
