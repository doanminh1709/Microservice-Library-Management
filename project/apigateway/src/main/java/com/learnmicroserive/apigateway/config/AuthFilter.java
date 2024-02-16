package com.learnmicroserive.apigateway.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final WebClient.Builder webClientBuilder;


    private AuthFilter(WebClient.Builder webClientBuiler){
        super(Config.class);
        this.webClientBuilder =webClientBuiler;
    }

    public static class Config{
        //This is empty class as I don't need any particular configuration
    }

    @Override
    public GatewayFilter apply(Config config) {
        //return to anonymous function
        return ((exchange, chain) -> {
            //Check request contains authorization
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing authorization information !");
            };
            String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            System.out.println(authHeader);

            String[] paths = authHeader.split(" ");
            if(paths.length != 2 || !paths[0].equals("Bearer")){
                throw new RuntimeException("Incorrect authorization structure !") ;
            }
            String KEY = "DOANMINH1709";
            Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(paths[1]);
            String username = decodedJWT.getSubject();

            if(Objects.equals(username, "") || username == null){
                throw new RuntimeException("Authorization error !");
            }
            //Todo : Create new header to request and forward request through Filter Chain
            //Return a copy of ServerHttpRequest through mutate method
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-auth-username" ,username).build();
            //Change ServerHttpRequest in ServerWeb copy
            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
