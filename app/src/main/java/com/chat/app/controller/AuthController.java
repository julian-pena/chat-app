package com.chat.app.controller;

import com.chat.app.config.security.userdetails.UserDetailsServiceImpl;
import com.chat.app.model.dto.user.UserInfoDTO;
import com.chat.app.model.dto.user.UserLoginRequest;
import com.chat.app.model.dto.user.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operations for login and user registration")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Operation(
            summary = "Register a new client",
            description = """
        This is a public endpoint that allows new users to register on the platform.
        No JWT token is required to access this endpoint.
        """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client registration data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDTO.class),
                            examples = @ExampleObject(
                                    name = "Client registration example",
                                    summary = "Example of a client registration",
                                    value = """
                    {
                        "userName": "john.doe@example.com",
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
                        "password": "SecurePassword123!"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Client successfully registered",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful client registration response",
                                    summary = "Example of a successfully registered client",
                                    value = """
                    {
                        "id": 1,
                        "userName": "john.doe@example.com",
                        "role": "ADMIN"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input, malformed object",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid input response",
                                    summary = "Example of an invalid input error",
                                    value = """
                    {
                        "status": 400,
                        "message": "Invalid email format or required fields missing",
                        "timestamp": "2024-10-23T10:50:00Z"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserInfoDTO> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO, UriComponentsBuilder uriComponentsBuilder){
        UserInfoDTO userDTO = userDetailsService.registerUser(userRegistrationDTO);
        URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userDTO.id()).toUri();
        return ResponseEntity.created(url).body(userDTO);
    }

    @Operation(
            summary = "Authenticate a user",
            description = """
        This endpoint allows a user to authenticate. The generated JWT token is valid for 1 hour.
        The token must be included in the authorization header to access protected endpoints.
        If the token expires, the user will need to log in again.
        This applies to both roles: ADMIN and CLIENT.
        """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User authentication data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserLoginRequest.class),
                            examples = @ExampleObject(
                                    name = "User authentication example",
                                    summary = "User authentication example",
                                    value = """
                    {
                        "userName": "admin@gamexo.com",
                        "password": "SecurePassword123!"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully authenticated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful authentication response",
                                    summary = "Example of a successful authentication response",
                                    value = """
                   {
                        "userName": "admin@gamexo.com",
                        "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
                        "role": "ADMIN"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid credentials response",
                                    summary = "Example response when the credentials are invalid",
                                    value = """
                                        {
                                            "timestamp": "2024-08-19T23:04:40.011+00:00",
                                            "status": 403,
                                            "error": "Not Authorized",
                                            "message": "Invalid password"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Missing email field",
                                    summary = "Example response when the request body is invalid",
                                    value = """
                    {
                      "timestamp": "2024-10-23T14:10:00",
                      "userName": "must not be empty",
                      "status": 400
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<UserInfoDTO> loginUser(@RequestBody @Valid UserLoginRequest loginRequest){
        return new ResponseEntity<>(userDetailsService.loginUser(loginRequest), HttpStatus.OK);
    }


}
