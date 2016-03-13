package com.apex.web.security.domain;

import static com.apex.web.security.Constants.ID_PROPERTY_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Permission extends AbstractEntity {

    private String value;
    private String description;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = ID_PROPERTY_NAME) , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = ID_PROPERTY_NAME) )
    private List<Role> roles = new ArrayList<>();

    public Permission() {
	super();
    }

    public Permission(String value) {
	this.value = value;
    }

}
