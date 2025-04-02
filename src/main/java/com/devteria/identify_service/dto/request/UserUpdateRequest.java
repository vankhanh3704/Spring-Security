package com.devteria.identify_service.dto.request;

import com.devteria.identify_service.validator.DobConstaints;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // giúp tạo ra 1 builder class cho 1 dto
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String password;
     String firstName;
     String lastName;
     @DobConstaints(min = 2, message = "INVALID_DOB")
     LocalDate dateOfBirth;

     List<String> roles;

}
