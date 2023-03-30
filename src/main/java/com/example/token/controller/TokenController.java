package com.example.token.controller;


import com.example.token.dto.RSAEncryptedResponseDTO;
import com.example.token.services.RSAImpl;
import com.example.token.dto.TokenResponseDTO;
import com.example.token.dto.tokenRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;


@Controller
public class TokenController {
    @Autowired
    RSAImpl rsa;

    @GetMapping(value = "/token" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> getToken() throws NoSuchAlgorithmException {
        String publicKey = rsa.generateKeys();
        return new ResponseEntity<>(new TokenResponseDTO(publicKey), HttpStatus.OK);
    }

    @PostMapping(value = "/encrypt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <?> encrypt(@RequestBody TokenRequestDTO tokenRequestDTO) {
        try {
            String publicKey = tokenRequestDTO.getPublic_key();
            String secret = tokenRequestDTO.getSecret();
            return new ResponseEntity<> (new RSAEncryptedResponseDTO(rsa.encryptRSA(publicKey, secret)),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }


}
