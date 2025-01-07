package com.chat.app.config.security.userdetails;

import com.chat.app.config.exceptions.UserNameDuplicatedException;
import com.chat.app.model.UserDocument;
import com.chat.app.model.dto.user.UserRegistrationDTO;
import com.chat.app.model.enums.UserRole;
import com.chat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found "));

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(("ROLE_".concat(user.getUserRole().name()))));

        return new User(
                user.getUsername(),
                user.getPassword(),
                true, // isEnabled
                true, // isAccountNonExpired
                true, // isCredentialsNonExpired
                true, // isAccountNonLocked
                simpleGrantedAuthorities
        );
    }


    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

        userNameInUse(userRegistrationDTO.getUsername());

        UserDocument user = UserDocument.builder()
                .username(userRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .userRole(UserRole.valueOf(userRegistrationDTO.getRole()))
                .build();

        userRepository.save(user);
    }


    private void userNameInUse (String userName) {
        if (userRepository.findByUsername(userName).isPresent())
            throw new UserNameDuplicatedException("Username already in use");
    }
}
