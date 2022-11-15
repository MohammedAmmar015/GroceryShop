package com.ideas2it.groceryshop.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * Role controller test class is used to test flow of
 * create, update and delete role.
 *
 * @version 1.0 07-11-2022
 *
 * @author Rohit A P
 *
 */
@SpringBootTest
public class RoleControllerTest {

    @InjectMocks
    RoleControllerTest roleController;

    @Mock
    RoleService roleService;

    /**
     * This method is used to test
     * create role method
     *
     * @throws Existed it throws role already exist
     */
    @Test
    public void createRole() throws Existed {
        RoleRequestDto roleRequestDto = new RoleRequestDto("admin");
        when(roleService.addRole(roleRequestDto)).thenReturn(new SuccessResponseDto());
    }

    /**
     * This method is used to update existing role name
     *
     * @throws NotFound it throws role not found
     */
    @Test
    public void updateRole() throws NotFound {
        RoleUpdateRequestDto roleUpdateRequestDto =
                new RoleUpdateRequestDto("ADMINI","ADMIN");
        when(roleService.updateRole(roleUpdateRequestDto)).thenReturn(new SuccessResponseDto());
    }

    /**
     * This method is used to delete role by name
     *
     * @throws NotFound it throws role not found
     */
    @Test
    public void deleteRole() throws NotFound {
        RoleRequestDto roleRequestDto = new RoleRequestDto("manager");
        when(roleService.deleteRole(roleRequestDto)).thenReturn(new SuccessResponseDto());
    }
}
