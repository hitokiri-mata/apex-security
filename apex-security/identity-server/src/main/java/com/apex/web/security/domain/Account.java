package com.apex.web.security.domain;

import static com.apex.web.security.Constants.ENCRIPTOR_REGISTER_PROPERTY_NAME;
import static com.apex.web.security.Constants.ID_PROPERTY_NAME;
import static com.apex.web.security.Constants.STRING_ENCRYPTED_STRING_NAME;
import static com.apex.web.security.Constants.STRING_ENCRYPTOR_NAME;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author hitokiri
 *
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = { "roles", "sessions" })
public class Account extends AbstractEntity {

    @OneToOne(cascade = { PERSIST, MERGE })
    @NotNull(message = "user.person.null")
    private Person person;

    /**
     * 
     */
    @Embedded
    private Credential credential;

    /**
     * 
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "role_account", 
    joinColumns = @JoinColumn(name = "account_id", referencedColumnName = ID_PROPERTY_NAME) , 
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = ID_PROPERTY_NAME) )
    @NotNull(message = "user.role.null")
    private List<Role> roles;

    /**
     * 
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Session> sessions = new ArrayList<>();

    /**
     * 
     */
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean active;

    /**
     * 
     * @return
     */
    public Session getActiveSession() {
	if (this.getSessions() != null) {
	    for (Session session : this.getSessions()) {
		if (session.getEndTime() == null) {
		    return session;
		}
	    }
	}
	return null;
    }

    @Data
    @Embeddable
    @TypeDef(name = STRING_ENCRYPTED_STRING_NAME, typeClass = EncryptedStringType.class, parameters = {
	    @Parameter(name = ENCRIPTOR_REGISTER_PROPERTY_NAME, value = STRING_ENCRYPTOR_NAME) })
    public static class Credential {

	@Size(min = 2, max = 14, message = "The license plate '${validatedValue}' must be between {min} and {max} characters long")
	@NotNull(message = "error.title.notnull")
	@Column(unique = true)
	private String username;
	/**
	 * 
	 */
	@JsonIgnore
	@Size(min = 10, max = 20, message = "'${validatedValue}' must be between {min} and {max} characters long")
	@NotNull(message = "user.password.notnull")
	@Type(type = STRING_ENCRYPTED_STRING_NAME)
	private String password;

    }

}
