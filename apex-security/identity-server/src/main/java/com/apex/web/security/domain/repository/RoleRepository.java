package com.apex.web.security.domain.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.apex.web.security.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    List<Role> findByAccountCredentialUsername(String username);

}
