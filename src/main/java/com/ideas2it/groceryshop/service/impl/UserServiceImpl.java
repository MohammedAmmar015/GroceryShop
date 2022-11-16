/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.AddressHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.mapper.UserMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.repository.UserRepo;
import com.ideas2it.groceryshop.service.UserService;

/**
 *
 * It is used to have User business logics and
 * it is can contact to user repository
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private AddressHelper addressHelper;
    private Logger logger;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo,
                           AddressHelper addressHelper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.addressHelper = addressHelper;
        this.logger = LogManager.getLogger(UserServiceImpl.class);
    }

    /**
     * it is used to create user
     *
     * @param userRequestDto it contains user details
     * @return SuccessResponseDto it contains success message
     * @throws Existed if username already exist
     */
    @Override
    public SuccessResponseDto addUser(UserRequestDto userRequestDto) throws Existed {
        logger.debug("Entered addUser method");
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        if (userRepo.existsByUserName(userRequestDto.getUserName())) {
            logger.debug("Username already exist");
            throw new Existed("Username already exist");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userRequestDto.getPassword()));
        Optional<Role> role = roleRepo.findByIsActiveAndName
                        (true, user.getRole().getName());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        userRepo.save(user);
        logger.debug("User created successfully");
        return new SuccessResponseDto(201,"User created successfully");
    }

    /**
     * It is used to get user by id
     *
     * @param id it is used to get user by id
     * @return userResponseDto it contains user detail
     * @throws NotFound if user does not exist or inactive
     */
    @Override
    public UserResponseDto getUserById(Integer id) throws NotFound {
        logger.debug("Entered getUserById method");
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            logger.debug("Uses not found");
            throw new NotFound("User not found");
        }
        UserResponseDto userResponseDto =
                UserMapper.userToUserResponseDto(user.get());
        logger.debug("Got users");
        return userResponseDto;
    }

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     * @throws NotFound users not found
     */
    @Override
    public List<UserResponseDto> getAllUser() throws NotFound {
        logger.debug("Entered getAllUser method");
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList(userRepo.findByIsActive(true));
        if(userResponseDtoList.isEmpty()) {
            logger.debug("Users not found");
            throw new NotFound("Users not found");
        }
        logger.debug("Got List of user");
        return userResponseDtoList;
    }

    /**
     * It is used to find users by role
     *
     * @param name used to search users by role name
     * @return userResponseDtoList list of user
     * @throws NotFound users not found
     */
    @Override
    public List<UserResponseDto> getUserByRole(String name) throws NotFound {
        logger.debug("Entered getUserByRole method");
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList
                (userRepo.findByIsActiveAndRoleName(true,
                        RoleMapper.roleDtoToRole(name).getName()));
        if(userResponseDtoList.isEmpty()) {
            logger.debug("Users not found");
            throw new NotFound("Users not found");
        }
        logger.debug("Got List of user");
        return userResponseDtoList;
    }

    /**
     *  It is used to delete user by id
     *
     * @param id to be deleted
     * @return SuccessResponseDto it contains success message
     * @throws NotFound user does not exist
     */
    @Override
    public SuccessResponseDto deleteUserById(Integer id) throws NotFound {
        logger.debug("Entered deleteUserById method");
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            logger.debug("User not found");
            throw new NotFound("User not found");
        }
        userRepo.deactivateUser(id);
        addressHelper.deleteAllAddressByUserId(id);
        logger.debug("User Deleted successfully");
        return new SuccessResponseDto(200,"User Deleted successfully");
    }

    /**
     * It is used to find user by username and active
     *
     * @param username it is name of user
     * @return CustomUserDetails(user.get()) it contains user details
     * @throws UsernameNotFoundException it contains user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        logger.debug("Entered loadUserByUsername method");
        Optional<User> user = userRepo.findByUserNameAndIsActive(username, true);
        if (user.isEmpty()) {
            logger.debug("Username Not Found");
            throw new UsernameNotFoundException("Username Not Found");
        }
        logger.debug("Got user");
        return new CustomUserDetails(user.get());
    }

    /**
     * This method is used to get user by mobile number
     *
     * @param userNameOrMobileNumber it contains username or mobileNumber
     * @return userName it is contains username
     */
    @Override
    public String getUserByMobileNumber(String userNameOrMobileNumber){
        logger.debug("Entered getUserByMobileNumber method");
        Long number = null;
        String userName = null;
        Boolean isTrue = Pattern.matches("^[7-9][0-9]{9}", userNameOrMobileNumber);
        if(isTrue == true){
            logger.debug("mobile number");
            number = Long.parseLong(userNameOrMobileNumber);
            Optional<User> user = userRepo.findUserByMobileNumberAndIsActive(number,
                    true);
            userName = user.get().getUserName();
        } else {
            logger.debug("username");
            userName = userNameOrMobileNumber;
        }
        logger.debug("Got user name");
        return userName;
    }

    /**
     * This method is used to get user object by name,
     * update user object and store in database
     *
     * @param userUpdateDto it contains details to be updated
     * @return SuccessResponseDto it contains success message
     * @throws NotFound user does not exist
     */
    public SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto)
            throws NotFound {
        logger.debug("Entered updateUserByUserName method");
        Optional<User> user = userRepo.findUserByUserNameAndIsActive
                (userUpdateDto.getUserName(), Boolean.TRUE);
        if(user.isEmpty()) {
            logger.debug("User not found");
            throw new NotFound("User not found");
        }
        User updatedUser = UserMapper.userUpdateDtoToUser(userUpdateDto, user.get());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updatedUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        userRepo.save(updatedUser);
        logger.debug("User Updated successfully");
        return new SuccessResponseDto(200,"User Updated successfully");
    }
}