package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.amazons3.service.S3Service;
import org.example.wms_be.converter.UserConverter;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.UserInfoDto;
import org.example.wms_be.data.dto.UserPasswordDto;
import org.example.wms_be.data.request.LockAccountReq;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.FileStorageException;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.service.UserService;
import org.example.wms_be.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Override
    public List<UserDto> getAllUser() {
        return userMapper.findAll().stream()
                .map(user -> {
                    user.setHinhAnh(s3Service.generatePresignedUrl(user.getHinhAnh()));
                    return userConverter.toUserDto(user);
                }).toList();
    }

    @Override
    public UserInfoDto updateUserInfo(UserInfoDto userDto) {
        User user = userConverter.toUserInfo(userDto);
        try {
            if (userMapper.checkUserExitsByUserName(userDto.getUsername())) {
                String imagePath = null;
                if (userDto.getHinhAnh() != null && !userDto.getHinhAnh().isEmpty()) {
                    try {
                        imagePath = uploadImage(userDto.getHinhAnh());
                        user.setHinhAnh(imagePath);
                    } catch (FileStorageException e) {
                        throw new FileStorageException("Error occurred while uploading the file: " + e.getMessage());
                    }
                }
                // Gọi mapper với dữ liệu đã xử lý
                userMapper.updateInfo(user);
                user = userMapper.findUserByUsername(userDto.getUsername());
            }
            return userConverter.toUserInfoDto(user);
        } catch (Exception e) {
            logger.error("Update info failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Update info failed");
        }
    }


    @Override
    public UserPasswordDto updatePassword(UserPasswordDto userDto) {
        try {
            User user = userMapper.findUserByUsername(userDto.getUsername());
            if (user == null) {
                throw new IllegalArgumentException("User not find with username" + userDto.getUsername());

            }
            if (!passwordEncoder.matches(userDto.getPasswordOld(), user.getPassword())) {
                throw new IllegalArgumentException("Old password is incorrect");
            }
            // Encrypt password
            String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encryptedPassword);
            userMapper.updateMatKhau(user);
            return userConverter.toUserPasswordDto(user);

        } catch (Exception e) {
            logger.error("Failed to update password: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDto findUserById(Integer sysIdUser) {
        return null;
    }

    @Override
    public UserDto findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        user.setHinhAnh(s3Service.generatePresignedUrl(user.getHinhAnh()));
        if (user == null) {
            throw new IllegalArgumentException("User not found with username " + username);
        }
        return userConverter.toUserDto(user);
    }

    @Override
    public LockAccountReq lockAccount(LockAccountReq lockAccountReq) {
        try {
            userMapper.lockAccount(lockAccountReq);
            return lockAccountReq;
        } catch (Exception e) {
            throw new BadSqlGrammarException("Lock account failed: " + e.getMessage());
        }
    }

    private String uploadImage(MultipartFile multipartFile) {
        try {
            File fileConvert = FileUtil.convertMultipartFileToFile(multipartFile);
            String imagePath = s3Service.uploadFileToS3(fileConvert);
            Files.deleteIfExists(fileConvert.toPath());
            return imagePath;
        } catch (IOException e) {
            throw new FileStorageException("Error when uploading file: " + e.getMessage());
        }
    }

    private String generateImageUrl(String imagePath) {
        return s3Service.generatePresignedUrl(imagePath);
    }
}
