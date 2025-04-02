package com.devteria.identify_service.mapper;


import com.devteria.identify_service.Entity.UserEntity;
import com.devteria.identify_service.dto.request.UserCreationRequest;
import com.devteria.identify_service.dto.request.UserUpdateRequest;
import com.devteria.identify_service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserCreationRequest request);

//    @Mapping(source = "firstName", target = "lastName", ignore = true)
    UserResponse toUserResponse(UserEntity entity);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget UserEntity userEntity, UserUpdateRequest request);
}
