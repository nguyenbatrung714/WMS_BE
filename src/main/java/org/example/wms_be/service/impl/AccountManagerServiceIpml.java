package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.AccountConverter;
import org.example.wms_be.converter.UserConverter;
import org.example.wms_be.data.dto.AccountDto;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.entity.account.Account;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.entity.account.UserRole;
import org.example.wms_be.entity.warehouse.Zone;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.account.AccountManagerMapper;
import org.example.wms_be.service.AccountManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagerServiceIpml implements AccountManagerService {
    private static final Logger logger = LoggerFactory.getLogger(AccountManagerServiceIpml.class);
    private final AccountManagerMapper accountManagerMapper;
    private final AccountConverter converter;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AccountDto insertAccount(AccountDto accountDto) {
        // Chuyển đổi từ DTO sang Entity
        Account account = converter.toAccount(accountDto);
        // Kiểm tra mật khẩu
        if (accountDto.getPassword() == null || accountDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        // Mã hóa mật khẩu
        String encryptedPassword = passwordEncoder.encode(accountDto.getPassword());
        account.setPassword(encryptedPassword); // Gán mật khẩu đã mã hóa vào Entity
        try {
            // Chèn tài khoản vào cơ sở dữ liệu và lấy sysIdUser
            int insertedRows = accountManagerMapper.insertAccount(account);
            if (insertedRows == 0) {
                throw new RuntimeException("Insert account failed, no rows affected.");
            }
            // Lấy sysIdUser vừa tạo từ đối tượng account
            if (account.getSysIdUser() == null) {
                throw new RuntimeException("sysIdUser is null after account insertion.");
            }
            // Kiểm tra xem cặp sysIdUser và sysIdRole đã tồn tại chưa
            if (accountManagerMapper.checkUserRoleExists(account.getSysIdUser(), accountDto.getSysIdRole())) {
                throw new IllegalArgumentException("User role already exists for sysIdUser: " + account.getSysIdUser());
            }
            // Nếu chưa tồn tại, tiếp tục chèn vai trò
            UserRole userRole = new UserRole();
            userRole.setSysIdUser(account.getSysIdUser()); // ID người dùng từ account
            userRole.setSysIdRole(accountDto.getSysIdRole()); // Vai trò người dùng

            int roleInsertedRows = accountManagerMapper.insertUserRole(userRole);
            if (roleInsertedRows == 0) {
                throw new RuntimeException("Failed to insert user role for account with sysIdUser: " + account.getSysIdUser());
            }

        } catch (Exception e) {
            logger.error("Insert account failed: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to insert account and user role into the database", e);
        }

        // Trả về DTO của tài khoản đã được lưu (với mật khẩu đã mã hóa)
        return converter.toAccountDto(account);
    }

    @Override
    public Void deleteAccount(String username) {
        if (!accountManagerMapper.checkUserExitsByUserName(username)) {
            throw new ResourceNotFoundException("Account", "username", username);
        }
        try {
           accountManagerMapper.deleteAccount(username);
            return null;
        } catch (Exception e) {
            logger.error("Error when deleting Account: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when deleting Account");
        }
    }
}
