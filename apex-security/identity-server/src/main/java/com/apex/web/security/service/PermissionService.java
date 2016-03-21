package com.apex.web.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apex.web.security.domain.Permission;

/**
 * 
 * @author hitokiri
 *
 */
public interface PermissionService {

    Permission getById(Long id);

    Page<Permission> getByRoleId(Long roleId, Pageable pageable);

}
