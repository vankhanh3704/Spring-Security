package com.devteria.identify_service.mapper;


import com.devteria.identify_service.Entity.Permission;
import com.devteria.identify_service.Entity.UserEntity;
import com.devteria.identify_service.dto.request.PermissionRequest;
import com.devteria.identify_service.dto.request.UserCreationRequest;
import com.devteria.identify_service.dto.request.UserUpdateRequest;
import com.devteria.identify_service.dto.response.PermissionResponse;
import com.devteria.identify_service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);


}
