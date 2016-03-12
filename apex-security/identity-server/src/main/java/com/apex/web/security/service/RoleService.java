package com.apex.web.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apex.web.security.domain.Role;

public interface RoleService {

    Page<Role> getAll(Pageable pageable);

    Page<Role> getByPrincipal(String name, Pageable pageable);

    Role getById(Long id);

}
