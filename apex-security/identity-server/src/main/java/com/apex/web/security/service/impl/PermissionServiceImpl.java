package com.apex.web.security.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apex.web.security.domain.Permission;
import com.apex.web.security.domain.repository.PermissionRepository;
import com.apex.web.security.service.PermissionService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author hitokiri
 *
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class PermissionServiceImpl implements PermissionService {

    private final @NonNull PermissionRepository permissionRepository;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.PermissionService#getById(java.lang.Long)
     */
    @Override
    public Permission getById(Long id) {
	return permissionRepository.findOne(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.PermissionService#getByRoleId(java.lang.
     * Long, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Permission> getByRoleId(Long roleId, Pageable pageable) {
	return permissionRepository.findByRolesId(roleId, pageable);
    }

}
