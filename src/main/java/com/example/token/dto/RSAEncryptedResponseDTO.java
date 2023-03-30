package com.example.token.dto;

public class RSAEncryptedResponseDTO {

    public String encrypted_message;

    public RSAEncryptedResponseDTO(String encrypted_message) {
        this.encrypted_message = encrypted_message;
    }

}
