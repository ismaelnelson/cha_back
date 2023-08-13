package com.semillero.crakruk.auth.service;


import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.auth.repository.UserRepository;
import com.semillero.crakruk.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource message;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmailEquals(email);
        if (user == null)
            throw new UsernameNotFoundException(message.getMessage("error.not_found_email", null, Locale.US));
        return new User(user.getEmail(), user.getPassword(), mappingRoles(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mappingRoles(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

}