package com.apex.web.security.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Role> getByPrincipal(String name) {
	return roleRepository.findByAccountCredentialUsername(name);
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

}
