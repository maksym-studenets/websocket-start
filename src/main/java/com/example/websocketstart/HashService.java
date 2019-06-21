package com.example.websocketstart;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class HashService {

    public List<Greeting> greeting(HelloMessage message) throws Exception {
        String base64Encoded = Base64.getEncoder().encodeToString(message.getName().getBytes());
        String aesEncrypted = encrypted(message);

        return List.of(new Greeting("Hello, " + HtmlUtils.htmlEscape(base64Encoded) + "!"),
                new Greeting("Hello, " + HtmlUtils.htmlEscape(aesEncrypted) + "!"));
    }

    private String encrypted(HelloMessage message) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        //byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getName().getBytes(StandardCharsets.UTF_8)));
    }
}
