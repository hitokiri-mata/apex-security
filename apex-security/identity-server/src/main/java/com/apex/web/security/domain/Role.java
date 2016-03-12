package com.apex.web.security.domain;

import static com.apex.web.security.Constants.ID_PROPERTY_NAME;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 
 * @author hitokiri
 *
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = { "accounts", "permissions" })
public class Role extends AbstractEntity implements GrantedAuthority {

    public static enum Permission {
	WRITE, READ, DELETE, EDIT
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permission")
    @Column(name = "permission")
    private List<Permission> permissions;

    /**
     * 
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = ID_PROPERTY_NAME) , inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = ID_PROPERTY_NAME) )
    private List<Account> accounts;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    @JsonIgnore
    public String getAuthority() {
	return name;
    }

}
