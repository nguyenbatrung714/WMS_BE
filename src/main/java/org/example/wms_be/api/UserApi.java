package org.example.wms_be.api;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.*;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserApi {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<ApiResponse<PageInfo<UserDto>>> getAllUser(@RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                     HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of User",
                userService.getAllUser(page, size)
        ), HttpStatus.OK);
    }
    @PostMapping("/update-info")
    public ResponseEntity<ApiResponse<UserInfoDto>> updateInfo(@RequestBody UserInfoDto userDto,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "User update Info successfully",
                userService.updateUserInfo(userDto)
        ), HttpStatus.CREATED);
    }
    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<UserPasswordDto>> updatePassword(
                                                               @RequestBody UserPasswordDto userDto,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Password updated successfully",
                userService.updatePassword( userDto)
        ), HttpStatus.OK);
    }

}
