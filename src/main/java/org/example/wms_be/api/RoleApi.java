package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.RoleDto;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/role")
public class RoleApi {
    private final RoleService roleService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAllRoles(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of Role",
                roleService.getAllRoles()
        ), HttpStatus.OK);
    }
}
