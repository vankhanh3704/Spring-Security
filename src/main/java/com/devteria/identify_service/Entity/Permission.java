package com.devteria.identify_service.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // giúp tạo ra 1 builder class cho 1 dto
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {

    @Id
    String name;
    String description;


}
