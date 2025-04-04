package com.devteria.identify_service.mapper;

import org.mapstruct.Mapper;

import com.devteria.identify_service.Entity.Permission;
import com.devteria.identify_service.dto.request.PermissionRequest;
import com.devteria.identify_service.dto.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
