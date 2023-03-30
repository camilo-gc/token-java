package com.example.token.services;


import com.example.token.services.interfaces.RSA;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Service
public class RSAImpl implements RSA {

    @Override
    public String generateKeys() throws NoSuchAlgorithmException {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            return Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

    }

    @Override
    public String encryptRSA(String base64PublicKey, String secretMessage) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        // we use X509EncodedKeySpec to get the key from []byte format
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(base64PublicKey));
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        System.out.println(encodedMessage);
        return encodedMessage;
    }


    @Override
    public PublicKey getPublicKey(String key) {
        try {
            //if base64 is invalid, you will see an error here
            byte[] byteKey = Base64.getDecoder().decode(key);
            //if it is not in RSA public key format, you will see error here as java.security.spec.InvalidKeySpecException
            X509EncodedKeySpec X509Key = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(X509Key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
