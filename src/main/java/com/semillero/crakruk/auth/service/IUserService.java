package com.semillero.crakruk.auth.service;


import com.semillero.crakruk.auth.dto.*;
import com.semillero.crakruk.auth.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {

    UserBasicDto signup(UserDto userDto) ;

    UserProfileDto getUserProfile(HttpServletRequest request);

    AuthenticationResponse generateToken(AuthenticationRequest authRequest) throws Exception;

    List<UserBasicDto> returnList();

    UserProfileDto updateUser(HttpServletRequest request, UserPatchDto updates);

    String getUserName(HttpServletRequest request);

    UserModel getUser(HttpServletRequest request);

    void deleteUser(Long id);

    void resetPassword(HttpServletRequest request,ResetPasswordDto resetPass);

    UserModel getUserByUserName(String userName);

    UserProfileDto updateRole(Long userId,Long roleId);
}
