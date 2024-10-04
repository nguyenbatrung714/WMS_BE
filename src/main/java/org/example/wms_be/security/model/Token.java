package org.example.wms_be.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

}
