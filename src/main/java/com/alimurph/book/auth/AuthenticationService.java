package com.alimurph.book.auth;

import com.alimurph.book.email.EmailService;
import com.alimurph.book.email.EmailTemplateName;
import com.alimurph.book.exception.OperationNotPermittedException;
import com.alimurph.book.role.RoleRepository;
import com.alimurph.book.security.JwtService;
import com.alimurph.book.user.Token;
import com.alimurph.book.user.TokenRepository;
import com.alimurph.book.user.User;
import com.alimurph.book.user.UserRepository;

import io.jsonwebtoken.lang.Objects;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    @Value("${application.mailing.activation-url}")
    private String activationUrl;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(RoleRepository roleRepository, UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegistrationRequest request) throws MessagingException, OperationNotPermittedException {

        // Step 1 - assign a default role to the user
        var userRole = roleRepository.findByName("USER").orElseThrow(
                // TODO - to add custom exception
                () -> new IllegalStateException("Role USER is not initialized")
        );

        // Step 1.1 - check if account already exists
        var accountExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if(accountExists)
            throw new OperationNotPermittedException("Email address already exists. Please try Login.");

        // Step 2 - create and save the user
        var newUser = new User.Builder()
                .setFirstname(request.getFirstname())
                .setLastname(request.getLastname())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setEmail(request.getEmail())
                .setAccountLocked(false)
                .setEnabled(false)
                .setRoles(List.of(userRole))
                .createUser();
        userRepository.save(newUser);

        // Step 3 - send validation mail with the activation token
        sendValidationMail(newUser);
    }

    private void sendValidationMail(User user) throws MessagingException {

        // Step 1- Generate activation token
        String activationToken = generateAndSaveActivationToken(user);
        emailService.sendMail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                activationToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String newToken = generateActivationToken(6);
        var token = new Token.Builder()
                .setToken(newToken)
                .setCreatedAt(LocalDateTime.now())
                .setExpiresAt(LocalDateTime.now().plusMinutes(15))
                .setUser(user)
                .createToken()
                ;
        tokenRepository.save(token);
        return newToken;
    }

    /*
        generate a random set of numbers between 0 and 9
     */
    private String generateActivationToken(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();  // use SecureRandom instead of Random

        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());        // this will generate numbers between 0 .... 9
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // check if account exists
        userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Account does not exists. Please click on resgister to create your account."));

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) auth.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", user.fullName());

        String jwtToken = jwtService.generateToken(claims, user);

        return new AuthenticationResponse.Builder().setJwtToken(jwtToken).createAuthenticationResponse();
    }


//    @Transactional    TODO add back again
    public void activateAccount(String activationToken) throws MessagingException {

        Token savedToken = tokenRepository.findByToken(activationToken).orElseThrow(() -> new RuntimeException("Invalid token provided. Please try again.")); //TODO add custom exception

         // check if activationToken has expired
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){ 
            sendValidationMail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address.");
        }

        // check if the token has already been validated previously
        if(!Objects.isEmpty(savedToken.getValidatedAt())){
            throw new OperationNotPermittedException("You have already validated your account. Please try to login.");
        }

        // check if the user mentioned in the activation token exists in user table
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));  // TODO add custom exception
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

    }
}
