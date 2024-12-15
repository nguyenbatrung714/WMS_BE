package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.*;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.LockAccountReq;
import org.example.wms_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserApi {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUser(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of User",
                userService.getAllUser()
        ), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(
            @PathVariable String username,
            HttpServletRequest request) {

        UserDto userDto = userService.findUserByUsername(username);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "User by username",
                userDto
        ), HttpStatus.OK);
    }


    @PostMapping("/update-info")
    public ResponseEntity<ApiResponse<UserInfoDto>> updateInfo(@ModelAttribute UserInfoDto userDto,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "User update Info successfully",
                userService.updateUserInfo(userDto)
        ), HttpStatus.CREATED);
    }

    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<UserPasswordDto>> updatePassword(@RequestBody UserPasswordDto userDto,
                                                                       HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Password updated successfully",
                userService.updatePassword(userDto)
        ), HttpStatus.OK);
    }

    @PostMapping("/lock-account")
    public ResponseEntity<ApiResponse<LockAccountReq>> lockAccount(@RequestBody LockAccountReq lockAccountReq,
                                                                   HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Lock account successfully",
                userService.lockAccount(lockAccountReq)
        ), HttpStatus.OK);
    }

}
