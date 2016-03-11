package com.apex.web.security.message;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultErrorMessage {
    private Date timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;
}
