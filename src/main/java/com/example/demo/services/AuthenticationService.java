package com.example.demo.services;

import com.example.demo.DTO.request.JwtRequest;
import com.example.demo.DTO.request.SignUpRequest;
import com.example.demo.DTO.response.CreatedUserResponse;
import com.example.demo.exceptions.ConfirmPasswordMismatchException;
import com.example.demo.models.User;
import com.example.demo.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private UserInfoService userInfoService;

    private UserManagementService userManagementService;

    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void UserManagementService(UserManagementServiceImpl userManagementService) {
        this.userManagementService = userManagementService;
    }

    public String createAuthToken(JwtRequest authRequest) {
        String login = authRequest.getUsername();
        String password = authRequest.getPassword();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = userInfoService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return token;
    }

    public CreatedUserResponse createNewUser(@RequestBody SignUpRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ConfirmPasswordMismatchException("Пароли не совпадают");
        }
        User user = userManagementService.create(request);
        return new CreatedUserResponse(user.getId(), user.getEmail());
    }
}