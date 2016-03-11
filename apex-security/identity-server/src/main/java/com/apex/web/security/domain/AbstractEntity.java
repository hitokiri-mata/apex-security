package com.apex.web.security.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @author hitokiri
 *
 */

@Getter
@ToString
@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity implements Identifiable<Long> {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final @JsonIgnore Long id;

    @Version
    private @JsonIgnore Long version;

    /**
     * 
     */
    protected AbstractEntity() {
	this.id = null;
    }

}
