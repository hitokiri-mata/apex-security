package com.apex.web.security.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apex.web.security.domain.Role;
import com.apex.web.security.domain.repository.RoleRepository;
import com.apex.web.security.service.RoleService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class RoleServiceImpl implements RoleService {

    private final @NonNull RoleRepository roleRepository;

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.service.RoleService#getByPrincipal(java.lang.
     * String)
     */
    @Override
    public Page<Role> getByPrincipal(String name, Pageable pageable) {
	return roleRepository.findByAccountsCredentialUsername(name,pageable);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.apex.web.security.service.RoleService#getById(java.lang.Long)
     */
    @Override
    public Role getById(Long id) {
	return roleRepository.findOne(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.apex.web.security.service.RoleService#getAll(org.springframework.data
     * .domain.Pageable)
     */
    public Page<Role> getAll(Pageable pageable) {
	return roleRepository.findAll(pageable);
    }
}
