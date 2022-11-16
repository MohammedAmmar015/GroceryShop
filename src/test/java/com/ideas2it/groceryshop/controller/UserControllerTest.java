package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *
 * UserControllerTest is used to test the flow of
 * create, delete and view users
 *
 * @version 1.0 14-11-2022
 *
 * @author Rohit A P
 */
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    /**
     *  it is used to create user
     *
     * @throws Existed username already exist
     */
    @Test
    public void createUser() throws Existed {
        UserRequestDto userRequestDto = new UserRequestDto("Rohit",
                "Rohit", "AP", 9876543211L,
                "rohit@gmail.com", "123456", "ADMIN");
         SuccessResponseDto SuccessResponseDto = new  SuccessResponseDto();
         SuccessResponseDto.setStatusCode(200);
         SuccessResponseDto.setMessage("User created successfully");
         when(userService.addUser(userRequestDto)).thenReturn(SuccessResponseDto);
    }

    /**
     * It is used to get user by id
     *
     * @throws NotFound user does not exist
     */
    @Test
    public void getUserById() throws NotFound, Existed {
        Role role = new Role(1,"admin", true);
        UserResponseDto userResponseDto = new UserResponseDto(1,
                "Rohit", "Rohit", "AP",
                9876543211L, "rohit@gmail.com",
                null, null, null,
                null, true, "admin");
        when(userService.getUserById(1)).thenReturn(userResponseDto);
        Assertions.assertEquals(1, userResponseDto.getId());
    }

    /**
     * It is used get all users
     *
     * @throws NotFound user does not found
     */
    @Test
    public void viewAllUser() throws NotFound {
        List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
        UserResponseDto userResponseDto = new UserResponseDto(1,
                "Rohit", "Rohit", "AP",
                9876543211L, "rohit@gmail.com",
                null, null, null,
                null, true, "admin");
        userResponseDtoList.add(userResponseDto);
        when(userService.getAllUser()).thenReturn(userResponseDtoList);
        Assertions.assertEquals(1, userResponseDtoList.size());
    }

    /**
     * It is used to find user by role
     *
     * @return userResponseDtoList it returns list of user
     */
    @Test
    public void viewUsersByRole() throws NotFound {
        List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
        UserResponseDto userResponseDto = new UserResponseDto(1,
                "Rohit", "Rohit", "AP",
                9876543211L, "rohit@gmail.com",
                null, null, null,
                null, true, "admin");
        userResponseDtoList.add(userResponseDto);
        when(userService.getUserByRole("admin")).thenReturn(userResponseDtoList);
        Assertions.assertEquals(1, userResponseDtoList.size());
    }

    /**
     *  It is used to delete user by id
     *
     * @throws NotFound it contains user not found exception
     */
    @Test
    public void deleteUserById() throws NotFound {
        SuccessResponseDto SuccessResponseDto = new  SuccessResponseDto();
        SuccessResponseDto.setStatusCode(200);
        SuccessResponseDto.setMessage("User deleted successfully");
        when(userService.deleteUserById(1)).thenReturn(SuccessResponseDto);
    }
}
