package com.truongduchoang.SpringBootRESTfullAPIs.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.UserCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.UserUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.MediaUploadResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.UserResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.BadRequestException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.DuplicateResourceException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.ResourceNotFoundException;
import com.truongduchoang.SpringBootRESTfullAPIs.mapper.UserMapper;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Role;
import com.truongduchoang.SpringBootRESTfullAPIs.models.User;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.RoleRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.UserRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.services.CloudinaryService;
import com.truongduchoang.SpringBootRESTfullAPIs.services.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public UserResponse createUser(UserCreateRequest request, MultipartFile avatar) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists", "EMAIL_ALREADY_EXISTS");
        }

        Role role = resolveRole(request.getRoleId());
        User user = userMapper.toEntity(request, role);

        if (hasFile(avatar)) {
            MediaUploadResponse uploadResponse = cloudinaryService.uploadImage(avatar, "event-management/users");
            user.setAvatarUrl(uploadResponse.getSecureUrl());
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUserResponses() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        return userMapper.toResponse(findUserById(id));
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request, MultipartFile avatar) {
        User user = findUserById(id);

        if (request.getEmail() != null && !StringUtils.hasText(request.getEmail())) {
            throw new BadRequestException("Email cannot be blank", "EMAIL_BLANK");
        }
        if (request.getFullName() != null && !StringUtils.hasText(request.getFullName())) {
            throw new BadRequestException("Full name cannot be blank", "FULL_NAME_BLANK");
        }
        if (StringUtils.hasText(request.getEmail())
                && !request.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmailAndUserIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists", "EMAIL_ALREADY_EXISTS");
        }

        Role role = request.getRoleId() != null ? resolveRole(request.getRoleId()) : null;
        userMapper.updateEntity(user, request, role);

        if (hasFile(avatar)) {
            MediaUploadResponse uploadResponse = cloudinaryService.uploadImage(avatar, "event-management/users");
            user.setAvatarUrl(uploadResponse.getSecureUrl());
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setStatus(updatedUser.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new NoSuchElementException("USER_NOT_FOUND"));
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found", "USER_NOT_FOUND"));
    }

    private Role resolveRole(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + roleId + " not found", "ROLE_NOT_FOUND"));
    }

    private boolean hasFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }
}
