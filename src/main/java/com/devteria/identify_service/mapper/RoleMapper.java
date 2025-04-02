package com.devteria.identify_service.mapper;


import com.devteria.identify_service.Entity.Role;
import com.devteria.identify_service.dto.request.RoleRequest;
import com.devteria.identify_service.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions" , ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);


}
