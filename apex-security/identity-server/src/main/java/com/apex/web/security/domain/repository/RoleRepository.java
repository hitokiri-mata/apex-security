package com.apex.web.security.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Page<Role> findByAccountsCredentialUsername(String username,
	    Pageable pageable);

}
