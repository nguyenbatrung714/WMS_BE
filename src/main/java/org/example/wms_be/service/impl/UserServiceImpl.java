package org.example.wms_be.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.UserConverter;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.UserInfoDto;
import org.example.wms_be.data.dto.UserPasswordDto;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageInfo<UserDto> getAllUser(int page, int size) {
        try {
            PageMethod.startPage(page + 1, size);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when starting page for User");
        }

        return new PageInfo<>(
                userMapper.findAll()
                        .stream()
                        .map(userConverter::toUserDto)
                        .toList());
    }

    @Override
    public UserInfoDto updateUserInfo(UserInfoDto userDto) {
        User user = userConverter.toUserInfo(userDto);
        try{
            if   (userMapper.checkUserExitsByUserName(userDto.getUsername())){
                    userMapper.updateInfo(user);
                    user = userMapper.findUserByUsername(userDto.getUsername());
                }
        }catch (Exception e) {
            logger.error("Update info failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Update info failed");
        }

        return userConverter.toUserInfoDto(user);
    }

    @Override
    public UserPasswordDto updatePassword(UserPasswordDto userDto) {
        try {
            // Kiểm tra mật khẩu mới và mật khẩu xác nhận
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                throw new IllegalArgumentException("Password and confirm password do not match");
            }
            User user = userMapper.findUserByUsername(userDto.getUsername());
            if (user != null) {
                // Encrypt password
                String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
                user.setPassword(encryptedPassword);
                userMapper.updateMatKhau(user);
                return userConverter.toUserPasswordDto(user);
            } else {
                throw new IllegalArgumentException("User not found with id: " + userDto);
            }
        } catch (Exception e) {
            logger.error("Failed to update password: {}", e.getMessage());
            throw new IllegalArgumentException("Error updating password");
        }
    }

    @Override
    public UserDto findUserById(Integer sysIdUser) {
        return null;
    }
}
