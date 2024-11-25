package com.chat.app.config.security.userdetails;

import com.chat.app.config.security.jwt.JwtUtils;
import com.chat.app.model.UserDocument;
import com.chat.app.model.dto.UserInfoDTO;
import com.chat.app.model.dto.UserLoginRequest;
import com.chat.app.model.dto.UserRegistrationDTO;
import com.chat.app.model.enums.UserRole;
import com.chat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final JwtUtils jwtUtils;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository clientRepository, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = clientRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found "));

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(("ROLE_".concat(user.getUserRole().name()))));

        return new User(
                user.getUserName(),
                user.getPassword(),
                true, // isEnabled
                true, // isAccountNonExpired
                true, // isCredentialsNonExpired
                true, // isAccountNonLocked
                simpleGrantedAuthorities
        );
    }


    public UserInfoDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        userNameInUse(userRegistrationDTO.userName());

        UserDocument user = UserDocument.builder()
                .userName(userRegistrationDTO.userName())
                .password(passwordEncoder.encode(userRegistrationDTO.password()))
                .userRole(UserRole.valueOf(userRegistrationDTO.rol()))
                .build();

        user = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return UserInfoDTO.builder()
                .id(user.getUserId())
                .userName(user.getUserName())
                .token(accessToken)
                .rol(user.getUserRole().toString())
                .build();
    }


    public UserInfoDTO loginUser(UserLoginRequest loginRequest) {

        String userName = loginRequest.userName();
        String password = loginRequest.password();

        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get authority
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst() // Get the first (and only) authority
                .map(GrantedAuthority::getAuthority)
                .orElse(""); // Return an empty string if no authority is found


        int downLineIdx = authority.indexOf("_");
        authority = authority.substring(downLineIdx + 1);

        String accessToken = jwtUtils.createToken(authentication);

        // Get ID from user
        UserDocument userDocument = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userName + " not found "));

        String userId = userDocument.getUserId();

        return UserInfoDTO.builder()
                .id(userId)
                .userName(userName)
                .token(accessToken)
                .rol(authority)
                .build();
    }


    private Authentication authenticate(String userName, String password) {

        UserDetails userDetails = loadUserByUsername(userName);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userName, userDetails.getPassword(), userDetails.getAuthorities());

    }


    private void userNameInUse (String userName) {
        if (userRepository.findByUserName(userName).isPresent())
            throw new UsernameNotFoundException("Email en uso");
    }
}
