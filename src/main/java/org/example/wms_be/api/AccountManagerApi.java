package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.AccountDto;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.AccountManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/account")
public class AccountManagerApi {
    private final AccountManagerService accountManagerService;

    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse<Void>> removeAccount(@PathVariable String username,
                                                                HttpServletRequest request) {
        accountManagerService.deleteAccount(username);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Account removed successfully with username: " + username,
                null
        ), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> postAccount(@RequestBody AccountDto accountDto,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Account post successfully",
                accountManagerService.insertAccount(accountDto)
        ), HttpStatus.CREATED);
    }
}
