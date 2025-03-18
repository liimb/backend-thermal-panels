package com.thermal.thermalback.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JoseHeaderNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String DEFAULT_AUTH_MANAGER_KEY = "default-auth-manager";
    private static final String REFRESH_AUTH_MANAGER_KEY = "refresh-auth-manager";

    private final String pathToPublicKey;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(
            @Value("${cert.path.publicKey}") String pathToPublicKey,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver
    ) {
        this.pathToPublicKey = pathToPublicKey;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    public SecurityFilterChain filterChainAll(final HttpSecurity http, Map<String, AuthenticationManager> authManagerStore) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/auth/by-sms",
                                "/auth/refresh",
                                "/auth/ask-sms-code",
                                "/register/create"
                        ).permitAll()
                        
                        .requestMatchers("/account/get-by-id/**").hasAnyAuthority("USER", "ADMIN")

                        .requestMatchers("/materials/get-all").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/materials/get/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/materials/delete/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/materials/edit/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/materials/save").hasAnyAuthority("ADMIN")

                        .requestMatchers("/works/get-all").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/works/get/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/works/delete/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/works/edit/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/works/save").hasAnyAuthority("ADMIN")

                        .requestMatchers("/orders/get-all-by-user/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/orders/create").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/orders/edit/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/orders/delete/**").hasAnyAuthority("ADMIN")

                        .anyRequest().authenticated())

                    .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationManagerResolver(request ->
                                request.getRequestURI().endsWith("/auth/refresh")
                                        ? authManagerStore.get(REFRESH_AUTH_MANAGER_KEY)
                                        : authManagerStore.get(DEFAULT_AUTH_MANAGER_KEY)
                        ));

        return http.build();
    }

    @Bean
    Map<String, AuthenticationManager> authManagerStore(
            JwtDecoder jwtVerifyDecoder, JwtDecoder jwtIgnoreTimeDecoder,
            JwtAuthenticationConverter customAuthConverter
    ) {
        return Map.of(
                DEFAULT_AUTH_MANAGER_KEY, getAuthManager(jwtVerifyDecoder, customAuthConverter),
                REFRESH_AUTH_MANAGER_KEY, getAuthManager(jwtIgnoreTimeDecoder, customAuthConverter)
        );
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("role");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(converter);

        return authConverter;
    }

    @Bean
    JwtDecoder jwtVerifyDecoder(Algorithm algorithm) {
        return token -> {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return parseJwt(token, jwt);
        };
    }

    private Jwt parseJwt(String token, final DecodedJWT jwt) {
        return Jwt.withTokenValue(token)
                .claims(claims -> claims.put("role", jwt.getClaim("role").asString()))
                .headers(headers -> {
                            headers.put(JoseHeaderNames.ALG, jwt.getAlgorithm());
                            headers.put(JoseHeaderNames.TYP, jwt.getType());
                            headers.put(JoseHeaderNames.CTY, jwt.getContentType());
                            headers.put(JoseHeaderNames.KID, jwt.getKeyId());
                        }
                )
                .issuer(jwt.getIssuer())
                .subject(jwt.getSubject())
                .audience(jwt.getAudience())
                .expiresAt(jwt.getExpiresAtAsInstant())
                .notBefore(jwt.getNotBeforeAsInstant())
                .issuedAt(jwt.getIssuedAtAsInstant())
                .jti(jwt.getId())
                .build();
    }

    @Bean
    Algorithm publicKeyAlgorithm() {
        try {
            byte[] publicBytes = getKeyFromString(pathToPublicKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            ECPublicKey publicKey = (ECPublicKey) kf.generatePublic(keySpec);

            return Algorithm.ECDSA256(publicKey);

        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthenticationManager getAuthManager(JwtDecoder decoder, JwtAuthenticationConverter converter) {
        JwtAuthenticationProvider defaultAuthProvider = new JwtAuthenticationProvider(decoder);
        defaultAuthProvider.setJwtAuthenticationConverter(converter);

        return new ProviderManager(defaultAuthProvider);
    }

    private byte[] getKeyFromString(String path) throws IOException {
        List<String> file = Files.readAllLines(Paths.get(path));
        String key = String.join("\n", file.subList(1, file.size() - 1));
        return Base64.decodeBase64(key);
    }

    @Bean
    CorsConfigurationSource configurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);

        configuration.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
        configuration.addAllowedHeader(HttpHeaders.ACCEPT);
        configuration.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        configuration.addAllowedHeader("X-Requested-With");

        configuration.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);

        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }
}
