package stc.test.socialmedia.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import stc.test.socialmedia.auth.model.AuthUser;
import stc.test.socialmedia.util.JwtUtil;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    @Value("${jwt.expiry}")
    long expiry;

    private final JwtEncoder encoder;

    @PostMapping("/token")
    public String token(@AuthenticationPrincipal AuthUser authUser) {
        log.debug("create JWT for '{}'", authUser);
        Instant now = Instant.now();
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry));
        JwtUtil.addUserDetails(claimsBuilder, authUser);
        return encoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }
}
