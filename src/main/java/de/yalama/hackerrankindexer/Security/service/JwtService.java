package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Repository.UserRepository;
import de.yalama.hackerrankindexer.User.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static de.yalama.hackerrankindexer.Security.SecurityConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Used to extract claims from JWTokens.
     * Claims are encrypted pieces of information in a string called token, read more about that here:
     * https://auth0.com/docs/tokens/json-web-tokens/json-web-token-claims
     *
     * Claims used here: Username, date, signature Algorithm
     *
     * @param token The JWToken to decrypt
     * @param claimsResolver resolves claims in the token
     * @param <T> class of the token
     * @return depending on the claim returns one of the pieces of information listed above
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        if(token == null || token.isEmpty()) {
            //TODO - not logged in!
        }
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates JWToken from the passed UserDetails instance
     *
     * @param userDetails passed by WebSecurity Config
     * @return JWT for a user as a string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        User user = this.findByEmail(userDetails.getUsername());
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        return createUserToken(claims);
    }

    //To prevent circular dependency with UserService - re-implement
    private User findByEmail(String email) {
        return this.userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with email %s found", email)));
    }

    private String createUserToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setId(claims.get("id").toString())
                .setSubject(claims.get("email").toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY).compact();
    }

    public String createCustomToken(Map<String, Object> claims) {
        JwtBuilder jwtBuilder = Jwts.builder();
        for(String key : claims.keySet()) {
            jwtBuilder.setHeaderParam(key, claims.get(key));
        }
        jwtBuilder
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY);

        return jwtBuilder.compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
