package rmartin.lti.server.service.impls;

import rmartin.lti.server.service.SecretService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SecretServiceImpl implements SecretService {

    private final SecureRandom sr = new SecureRandom();

    @Override
    public String generateSecret(int size) {
        if(size<=0)
            throw new IllegalArgumentException("Secret size must be greater than 0");

        byte[] arr = new byte[size];
        sr.nextBytes(arr);
        return Base64.encodeBase64URLSafeString(arr);
    }

    @Override
    public String generateSecret() {
        return this.generateSecret(64);
    }
}
