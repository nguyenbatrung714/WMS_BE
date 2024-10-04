package org.example.wms_be.security.service;

import org.example.wms_be.security.model.LoginRequest;
import org.example.wms_be.security.model.Token;

public interface AuthService {
    Token login(LoginRequest loginRequest);

}
