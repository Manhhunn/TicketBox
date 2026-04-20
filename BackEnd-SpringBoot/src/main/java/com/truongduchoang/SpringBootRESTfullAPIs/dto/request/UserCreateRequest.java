package com.truongduchoang.SpringBootRESTfullAPIs.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.truongduchoang.SpringBootRESTfullAPIs.models.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {
    private Long roleId;

    @JsonAlias("name")
    @NotBlank(message = "Tên không được bỏ trống!")
    @Size(max = 150, message = "Tên không được vượt quá 150 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được bỏ trống!")
    @Email(message = "Email không hợp lệ!")
    @Size(max = 150, message = "Email không được vượt quá 150 ký tự")
    private String email;

    @Size(max = 20, message = "Số điện thoại không được vượt quá 20 ký tự")
    private String phone;

    @Size(max = 255, message = "Password hash không được vượt quá 255 ký tự")
    private String passwordHash;

    private UserStatus status;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
