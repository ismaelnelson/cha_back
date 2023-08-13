package com.semillero.crakruk.auth.service.Impl;


import com.semillero.crakruk.auth.dto.*;
import com.semillero.crakruk.auth.mapper.UserMapper;
import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.auth.repository.UserRepository;
import com.semillero.crakruk.auth.service.IUserService;
import com.semillero.crakruk.auth.service.JwtUtils;
import com.semillero.crakruk.exeption.NullListException;
import com.semillero.crakruk.exeption.PasswordException;
import com.semillero.crakruk.exeption.UserAlreadyExistsException;
import com.semillero.crakruk.exeption.UserNotFoundException;
import com.semillero.crakruk.model.Role;
import com.semillero.crakruk.repository.RoleRepository;
import com.semillero.crakruk.service.IEmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageSource message;
    private IEmailService emailService;

    private final  JwtUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse generateToken(AuthenticationRequest authRequest) throws Exception {
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
            AuthenticationResponse jwt = JwtUtils.createToken(userDetails);

            return jwt;
        }
        catch (BadCredentialsException e) {
            throw new Exception(message.getMessage("error.bad_credentials",null, Locale.US),e);
        }
    }

    @Override
    @Transactional
    public UserBasicDto signup(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(message.getMessage("error.account_exists", null, Locale.US));
        }
        if (userExists(userDto.getUser())){
            throw new UserAlreadyExistsException(message.getMessage("error.user_exist", null, Locale.US));
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserModel entity = userMapper.userDTO2Entity(userDto);
        entity.setRole(roleRepository.findByName("USER").get());
        entity = userRepository.save(entity);
        emailService.sendWelcomeEmailTo(userDto.getEmail(),userDto.getName());

        return userMapper.userEntity2UserBasicDto(entity);
    }

    public UserProfileDto getUserProfile(HttpServletRequest request) {

        String email = null;
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");
        jwt = authorizationHeader.substring(7);
        email = JwtUtils.decodeToken(jwt);

        UserModel userModel = userRepository.findByEmailEquals(email);
        UserProfileDto dto = userMapper.userModel2UserProfileDto(userModel);

        return dto;
    }

    public List<UserBasicDto> returnList() {

       // List<UserBasicDto> entityList = userRepository.getAllUsers();
        List<UserModel> entityList = userRepository.findAll();

        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US)); //new Locale("es","ES")
        }

        return null;
    }

    @Transactional
    @Override
    public UserProfileDto updateUser(HttpServletRequest request, UserPatchDto updates) {
        if (userExists(updates.getUser()) && (!getUserName(request).equals(updates.getUser()))){
            throw new UserAlreadyExistsException(message.getMessage("error.user_exist", null, Locale.US));
        }
        UserModel userModel = this.getUser(request);
        userMapper.update(userModel,updates);
        userRepository.save(userModel);
        return userMapper.userModel2UserProfileDto(userModel);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException(
                message.getMessage(
                        "error.user_not_found",
                        null,
                        Locale.US
                )
        );
        userRepository.deleteById(id);
    }

    @Override
    public String getUserName(HttpServletRequest request){
        String email = null;
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");
        jwt = authorizationHeader.substring(7);
        email = JwtUtils.decodeToken(jwt);

        UserModel userModel = userRepository.findByEmailEquals(email);

        return userModel.getUser();
    }


    @Override
    public UserModel getUser(HttpServletRequest request){
        String email = null;
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");
        jwt = authorizationHeader.substring(7);
        email = JwtUtils.decodeToken(jwt);

        UserModel userModel = userRepository.findByEmailEquals(email);

        return userModel;
    }

    private boolean emailExists(String email) {

        return userRepository.findByEmailEquals(email) != null;

    }

    private boolean userExists(String user) {

        return userRepository.findByUserEquals(user)!= null;

    }

    @Override
    public void resetPassword(HttpServletRequest request,ResetPasswordDto resetPass ){

        UserModel user = getUser(request);
        if (!(passwordEncoder.matches(resetPass.getOldPass(),user.getPassword()))){
            throw new PasswordException(message.getMessage("error.wrong_password", null, Locale.US));
        }
        if (!(resetPass.getNewPass().equals(resetPass.getConfirmNewPass()))){
            throw new PasswordException(message.getMessage("error.match_password", null, Locale.US));
        }
        user.setPassword(passwordEncoder.encode(resetPass.getNewPass()));
        userRepository.save(user);


    }

    @Override
    public UserModel getUserByUserName(String userName){
        return userRepository.findByUserEquals(userName);
    }

    @Override
    public UserProfileDto updateRole(Long userId, Long roleId) {
        Optional<UserModel> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            UserModel userModel = optionalUser.get();
            Role roleModel = optionalRole.get();

            userModel.setRole(roleModel);
            UserModel savedUser = userRepository.save(userModel);

            return userMapper.userModel2UserProfileDto(savedUser);
        }

        return null;
    }

    public UserDto deleteUserBoolean (Long id) throws Exception {
        Optional<UserModel> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            userModel.setDeleted(true);
            UserModel savedUser = userRepository.save(userModel);
            return userMapper.userEntity2DTO(savedUser);
        } else {
            throw new Exception("Id not found");
        }
    }
    public List<UserDto> getAllUser() throws Exception {
        List<UserModel> users = userRepository.findAll();
        if(users.isEmpty()){
           throw new Exception("Empty list");
        }
        List<UserDto> dtos = userMapper.listEntity2DTO(users);
        return dtos;
    }
    public UserDto enableUser (Long id) throws Exception {
        Optional<UserModel> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            userModel.setDeleted(false);
            UserModel savedUser = userRepository.save(userModel);
            return userMapper.userEntity2DTO(savedUser);
        } else {
            throw new Exception("Id not found");
        }
    }
    @Transactional
    public UserDto updateRole(Long id) throws Exception {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()){
            UserModel userModel = user.get();
            Optional<Role> roleAdmin = roleRepository.findById(1L);
            if(roleAdmin.isPresent()){
                userModel.setRole(roleAdmin.get());
                return userMapper.userEntity2DTO(userRepository.save(userModel));
            }else{
                throw new Exception("Admin role not found");
            }
        }else{
            throw new Exception("User not found for the given id");
        }

    }
}