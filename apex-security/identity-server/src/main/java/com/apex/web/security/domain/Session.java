package com.apex.web.security.domain;

import static com.apex.web.security.Constants.DATE_TIMEZONE_FORMAT;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@ToString(callSuper = false, exclude = { "account" })
public class Session extends AbstractEntity {

    public enum Agent {
	SYSTEM, USER
    }

    @Column(unique = true, nullable = false)
    private String ticket;

    @OneToOne
    @NotNull
    @JsonIgnore
    private Account account;

    /**
     * 
     */
    @JsonFormat(shape = STRING, pattern = DATE_TIMEZONE_FORMAT)
    private Date startTime;

    /**
     * 
     */
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(shape = STRING, pattern = DATE_TIMEZONE_FORMAT)
    private Date endTime;

    /**
     * 
     */
    @JsonFormat(shape = STRING, pattern = DATE_TIMEZONE_FORMAT)
    private Date lastRequest;
    /**
     * 
     */
    @NotNull(message = "user.session.ip.not.null")
    private String remoteIPAddress;

    @JsonInclude(Include.NON_NULL)
    @Enumerated(EnumType.STRING)
    private Agent closeBy;

}
