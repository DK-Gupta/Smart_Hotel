package com.smart.hotel.service;

import com.smart.hotel.model.User;
import com.smart.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.singleton(() -> user.getRole()));
    }
}
