package com.devteria.identify_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devteria.identify_service.Entity.Role;
import com.devteria.identify_service.dto.request.RoleRequest;
import com.devteria.identify_service.dto.response.RoleResponse;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
