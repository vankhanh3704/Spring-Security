package com.devteria.identify_service.Service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devteria.identify_service.Entity.UserEntity;
import com.devteria.identify_service.Enum.Role;
import com.devteria.identify_service.Exception.AppException;
import com.devteria.identify_service.Exception.ErrorCode;
import com.devteria.identify_service.Repository.RoleRepository;
import com.devteria.identify_service.Repository.UserRepository;
import com.devteria.identify_service.dto.request.UserCreationRequest;
import com.devteria.identify_service.dto.request.UserUpdateRequest;
import com.devteria.identify_service.dto.response.UserResponse;
import com.devteria.identify_service.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
// nó sẽ tự động đưa vào constructor các cái field có final kết hợp với bên dưới, dùng cái này không cần phải autowired
@RequiredArgsConstructor
// bất cứ field nào khai báo bên trong nó tự make private, final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserEntity user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        //        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //    @PreAuthorize("hasRole('ADMIN')") // kiểm tra trước khi vào được method
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize(
            "returnObject.username == authentication.name") // kiểm tra sau khi vào được method rồi mới xét đến role rồi
    // mới trả về
    public UserResponse getUser(String id) {
        log.info("In method get User by Id");
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userid, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        // khi 1 request đc xác thực thành công thì thông tin đăng nhập đc lưu trữ trong SecurityContextHolder
        var contest = SecurityContextHolder.getContext(); // get user hien tai
        String name = contest.getAuthentication().getName();
        UserEntity user =
                userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
