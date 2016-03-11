package com.apex.web.security.domain;

import static com.apex.web.security.Constants.DATE_FORMAT;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author hitokiri
 *
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Person extends AbstractEntity {

    /**
     * 
     */
    private String firstname;
    private String lastname;
    /**
     * 
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private Date birthday;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PHONE", joinColumns = @JoinColumn(name = "OWNER_ID") )
    @Column(name = "PHONE_NUMBER")
    private List<Phone> phones;
    /**
     * 
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;
    /**
     * containt the current user
     */
    @OneToOne
    @JsonIgnore
    private Account account;

    /**
     * 
     * @author hitokiri
     *
     */
    @Data
    @Embeddable
    public static class Address {
	private String street;
	private String city;
	private String state;
	@Column(name = "ZIP_CODE")
	private String zipCode;

    }

    /**
     * 
     * @author hitokiri
     *
     */
    @Data
    @Embeddable
    public static class Phone {
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;

	private String areaCode;
	private String number;

    }

    /**
     * 
     * @author hitokiri
     *
     */
    public enum PhoneType {
	MOBILE("phone.mobile.label"), HOME("phone.home.label"), WORK(
		"phone.work.label");
	private String label;

	PhoneType(String label) {
	    this.label = label;
	}

	public String getLabel() {
	    return this.label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.getLabel();
	}

    }

    public enum Gender {
	MALE, FEMALE

    }

}
