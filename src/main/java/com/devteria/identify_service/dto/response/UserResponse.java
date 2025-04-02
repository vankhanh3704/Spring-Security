package com.devteria.identify_service.dto.response;

import com.devteria.identify_service.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // giúp tạo ra 1 builder class cho 1 dto
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     String id;
     String username;
     String firstName;
     String lastName;
     LocalDate dateOfBirth;
     Set<RoleResponse> roles;
}
