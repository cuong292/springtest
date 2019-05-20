package com.demo.springtest.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "11111111111111111111111111111111";
    private static final long EXPIRE_TIME = 86400000;
    private static String USER_ID = "user_id";

    public String generateToken(int userId) {
        String token = "";
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("USER_ID", userId);
            builder.expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME));
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private byte[] generateShareSecret() {
        return SECRET_KEY.getBytes();
    }

    private JWTClaimsSet getDataFromToken(String token) {
        JWTClaimsSet claimsSet = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claimsSet = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claimsSet;
    }

    public int getUserIDFromToken(String token) {
        JWTClaimsSet claimsSet = getDataFromToken(token);
        if (claimsSet != null) {
            try {
                return claimsSet.getIntegerClaim(USER_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public boolean isTokenExpire(String token) {
        JWTClaimsSet claimsSet = getDataFromToken(token);
        if (claimsSet != null) {
            try {
                Date date = claimsSet.getExpirationTime();
                //return isExpire(date.)
            }
        }
    }
}
