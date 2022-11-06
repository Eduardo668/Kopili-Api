package br.com.kopili.utility;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public final class AlgorithmUtil {

    private final Environment env;

    public Algorithm defineAlgorithm(){

        String secret = env.getProperty("secret.key");

        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

        return algorithm;



    }


}
