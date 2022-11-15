package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
import com.ideas2it.groceryshop.model.Cart;
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
    private UserHelper userHelper;
    private AddressHelper addressHelper;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo,
                           UserHelper userHelper, AddressHelper addressHelper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userHelper = userHelper;
        this.addressHelper = addressHelper;
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
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        if (userRepo.existsByUserName(userRequestDto.getUserName())) {
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
        return new SuccessResponseDto(200,"User created successfully");
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
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            throw new NotFound("User does not exist");
        }
        UserResponseDto userResponseDto =
                UserMapper.userToUserResponseDto(user.get());
        return userResponseDto;
    }

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     * @throws NotFound user not found
     */
    @Override
    public List<UserResponseDto> getAllUser() throws NotFound {
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList(userRepo.findByIsActive(true));
        if(userResponseDtoList.isEmpty()) {
            throw new NotFound("User not found");
        }
        return userResponseDtoList;
    }

    /**
     * It is used to find users by role
     *
     * @param name used to search users by role name
     * @return userResponseDtoList list of user
     */
    @Override
    public List<UserResponseDto> getUserByRole(String name) {
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList
                (userRepo.findByIsActiveAndRoleName(true,
                        RoleMapper.roleDtoToRole(name).getName()));
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
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            throw new NotFound("User not found");
        }
        userRepo.deactivateUser(id);
        addressHelper.deleteAllAddressByUserId(id);
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
        Optional<User> user = userRepo.findByUserNameAndIsActive(username, true);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        return new CustomUserDetails(user.get());
    }

    /**
     * This method is used to get user by mobile number
     *
     * @param mobileNumber it contains username or mobileNumber
     * @return userName it is contains username
     */
    @Override
    public String getUserByMobileNumber(String userNameOrMobileNumber){
        Long number = null;
        String userName = null;
        Boolean isTrue = Pattern.matches("^[7-9][0-9]{9}", userNameOrMobileNumber);
        if(isTrue == true){
            number = Long.parseLong(userNameOrMobileNumber);
            Optional<User> user = userRepo.findUserByMobileNumberAndIsActive(number,
                    true);
            userName = user.get().getUserName();
        } else {
            userName = userNameOrMobileNumber;
        }
        return userName;
    }

    /**
     * This method is used to get user object by name,
     * update user object and store in database
     *
     * @param userUpdateDto it contains details to be updated
     * @return SuccessResponseDto it contains success message
     */
    public SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto) {
        Optional<User> user = userRepo.findUserByUserNameAndIsActive
                (userUpdateDto.getUserName(), Boolean.TRUE);
        User updatedUser = UserMapper.userUpdateDtoToUser(userUpdateDto, user.get());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updatedUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        userRepo.save(updatedUser);
        return new SuccessResponseDto(200,"User Updated successfully");
    }
}