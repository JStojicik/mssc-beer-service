package com.springframework.msscbeerservice.web.controller;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFounException extends RuntimeException {
    public NotFounException(String message) {
        super(message);
    }
}
