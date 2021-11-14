package com.ramos.f.jefferson.controller;

import com.ramos.f.jefferson.dto.LoginDTO;
import com.ramos.f.jefferson.dto.TokenDTO;
import com.ramos.f.jefferson.dto.UserDTO;
import com.ramos.f.jefferson.exception.InvalidTokenException;
import com.ramos.f.jefferson.jwt.JwtTokenUtil;
import com.ramos.f.jefferson.security.CustomUserDetails;
import com.ramos.f.jefferson.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.ramos.f.jefferson.util.Constants.HEADER_STRING;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    
    public AuthorizationController(UserService userService,
            JwtTokenUtil jwtTokenUtil,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) userService.loadUserByUsername(loginDTO.getUsername());
        String token = jwtTokenUtil.generateToken(customUserDetails);
        return ResponseEntity.ok(new TokenDTO(token, new UserDTO(customUserDetails.getUser(), customUserDetails.getRoles())));
    }
    
    @PutMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizaToken(@RequestBody TokenDTO tokenDTO) throws InvalidTokenException {
        if(tokenDTO != null && jwtTokenUtil.canTokenBeRefreshed(tokenDTO.getToken())) {
            String refreshedToken = jwtTokenUtil.refreshToken(tokenDTO.getToken());
            return ResponseEntity.ok(new TokenDTO(refreshedToken, tokenDTO.getUserDTO()));
        }
        throw new InvalidTokenException();
    }
    
    @GetMapping("/verify")
    public ResponseEntity<?> verificaToken(HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader(HEADER_STRING);
        if(token != null && jwtTokenUtil.canTokenBeRefreshed(token)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new InvalidTokenException();
    }
}
