package com.apex.web.security.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Permission;

public interface PermissionRepository
	extends PagingAndSortingRepository<Permission, Long> {

    Page<Permission> findByRolesId(Long id, Pageable pageable);

}
