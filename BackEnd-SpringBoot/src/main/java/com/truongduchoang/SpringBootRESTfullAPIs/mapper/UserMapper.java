package com.truongduchoang.SpringBootRESTfullAPIs.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.UserCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.UserUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.UserResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Role;
import com.truongduchoang.SpringBootRESTfullAPIs.models.User;
import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.UserStatus;

@Component
public class UserMapper {
    public User toEntity(UserCreateRequest request, Role role) {
        User user = new User();
        user.setRole(role);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(request.getPasswordHash());
        user.setStatus(request.getStatus() != null ? request.getStatus() : UserStatus.ACTIVE);
        return user;
    }

    public void updateEntity(User user, UserUpdateRequest request, Role role) {
        if (role != null || request.getRoleId() != null) {
            user.setRole(role);
        }
        if (StringUtils.hasText(request.getFullName())) {
            user.setFullName(request.getFullName());
        }
        if (StringUtils.hasText(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getPasswordHash() != null) {
            user.setPasswordHash(request.getPasswordHash());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        if (user.getRole() != null) {
            response.setRoleId(user.getRole().getRoleId());
            response.setRoleName(user.getRole().getRoleName());
        }
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setStatus(user.getStatus());
        response.setEmailVerifiedAt(user.getEmailVerifiedAt());
        response.setLastLoginAt(user.getLastLoginAt());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
