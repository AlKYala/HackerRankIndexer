package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        de.yalama.hackerrankindexer.User.Model.User user = this.userService.findByUsername(username);
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPasswordHashed(), Collections.emptyList());
    }
}
