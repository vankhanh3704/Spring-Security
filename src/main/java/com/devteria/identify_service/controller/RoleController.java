package com.devteria.identify_service.controller;


import com.devteria.identify_service.Service.PermissionService;
import com.devteria.identify_service.Service.RoleService;
import com.devteria.identify_service.dto.request.ApiResponse;
import com.devteria.identify_service.dto.request.PermissionRequest;
import com.devteria.identify_service.dto.request.RoleRequest;
import com.devteria.identify_service.dto.response.PermissionResponse;
import com.devteria.identify_service.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;


    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }


    @DeleteMapping("/{roleId}")
    ApiResponse<Void> delete(@PathVariable String roleId) {
        roleService.delete(roleId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
