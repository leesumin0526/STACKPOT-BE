package stackpot.stackpot.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import stackpot.stackpot.domain.User;
import stackpot.stackpot.repository.RefreshTokenRepository;
import stackpot.stackpot.repository.UserRepository.UserRepository;
import stackpot.stackpot.web.dto.TokenServiceResponse;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; 	//1시간
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 3; // 3분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 1; // 1분
    private final UserDetailsService  userDetailsService;

    // JWT 생성 (이메일 포함)
    public TokenServiceResponse createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .claim("random", UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();


        long expiration = getExpiration(refreshToken);
        refreshTokenRepository.saveToken(user.getId(), refreshToken, expiration);

        return TokenServiceResponse.of(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT Token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date()); // 현재 시간보다 이전이면 만료됨
        } catch (ExpiredJwtException e) {
            return true; // 이미 만료된 토큰
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String email = getEmailFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email); // 이메일로 사용자 로드
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public long getExpiration(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().getTime() - System.currentTimeMillis();
        } catch (ExpiredJwtException e) {
            return 0; // 이미 만료된 경우 0 반환
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT Token");
        }
    }

    public String refreshAccessToken(String refreshToken) {
        //  리프레시 토큰이 유효한지 확인
        if (!refreshTokenRepository.validateRefreshToken(refreshToken)) {
            return null; //  만료된 경우 null 반환
        }

        //  Redis에서 userId 조회
        Long userId = refreshTokenRepository.getUserIdByToken(refreshToken);
        if (userId == null) {
            return null;
        }

        //  userId 기반으로 유저 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        //  새 액세스 토큰 생성
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date now = new Date();

        String newAccessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return newAccessToken;
    }
}