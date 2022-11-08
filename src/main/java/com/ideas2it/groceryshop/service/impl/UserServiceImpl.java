package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.mapper.UserMapper;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.repository.UserRepo;
import com.ideas2it.groceryshop.service.UserService;

/**
 *
 * It is used to have User business logics
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserHelper userHelper;

    /**
     * it is used to create user
     *
     * @param userRequestDto
     */
    public void addUser(UserRequestDto userRequestDto) {
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        if (userRequestDto.getRoleRequestDto().getName().equals("customer")) {
            Cart cart = new Cart();
            user.setCart(cart);
            cart.setUser(user);
        }
        Optional<Role> role = roleRepo.findByIsActiveAndName
                        (true, userRequestDto.getRoleRequestDto().getName());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        userRepo.save(user);
    }

    /**
     * It is used to get user by id
     *
     * @param id it is used to get user by id
     * @return userResponseDto it contains user detail
     */
    public UserResponseDto getUserById(Integer id) {
        User user = userRepo.findByIsActiveAndId(true, id);
        UserResponseDto userResponseDto = UserMapper.userToUserResponseDto(user);
        return userResponseDto;
    }

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     */
    public List<UserResponseDto> getAllUser() {
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList(userRepo.findByIsActive(true));
        return userResponseDtoList;
    }

    /**
     * It is used to find users by role
     *
     * @param name used to search users by role name
     * @return userResponseDtoList list of user
     */
    public List<UserResponseDto> getUserByRole(String name) {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, name);
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList
                (userRepo.findByIsActiveAndRole(true, role.get()));
        return userResponseDtoList;
    }

    /**
     *  It is used to delete user by id
     *
     * @param id to be deleted
     */
    public void deleteUserById(Integer id){
        userRepo.deactivateUser(id);
    }
}