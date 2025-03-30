package com.devteria.identify_service.Service;


import com.devteria.identify_service.Exception.AppException;
import com.devteria.identify_service.Exception.ErrorCode;
import com.devteria.identify_service.Repository.UserRepository;
import com.devteria.identify_service.dto.request.AuthenticationRequest;
import com.devteria.identify_service.dto.request.IntrospectRequest;
import com.devteria.identify_service.dto.response.AuthenticationResponse;
import com.devteria.identify_service.dto.response.IntrospectResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;

    @NonFinal //đánh dấu để nó không inject vào constructor
    @Value("${jwt.signerKey}") // đọc 1 biến từ file yaml
    protected String SIGNER_KEY;

    public IntrospectResponse introspectResponse(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        // để verify token này thì
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        // kiểm tra token hết hạn hay chưa
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier); // trả về true false

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow( () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }


        var token = generateToken(authenticationRequest.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // token trả về là 1 string
    private String generateToken(String username) {
        // tạo 1 token bằng thư viện numbus, yêu cầu 2 params : header, và payload

        // header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // payload : cần 1 cái claim : ~ 1 cai body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) // đại diện cho user đăng nhập
                .issuer("devteria.com") // xác định cái token này được issue từ ai: thường là domain từ service
                .issueTime(new Date()) // lấy thời điểm hiện tại
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())) // xác định thời hạn của nó
                .claim("customClaim", "Custom")
                .build();

        // convert
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        //sau đó phải kí token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));// Thuật toán kí Singular Matrix, nó cần secret chuỗi 32 bite, lên generate-random.org
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create Token", e);
            throw new RuntimeException(e);
        }
    }


}
