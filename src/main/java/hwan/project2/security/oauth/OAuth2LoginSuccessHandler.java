package hwan.project2.security.oauth;

import hwan.project2.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${jwt.refresh-exp-seconds}")
    private long refreshExpSeconds;

    @Value("${app.frontend-url:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2MemberPrincipal memberPrincipal = resolvePrincipal(authentication);
        if (memberPrincipal == null) {
            response.sendRedirect(frontendUrl + "/login?error=oauth_failed");
            return;
        }

        String accessToken = jwtTokenProvider.createAccessToken(memberPrincipal.getId(), memberPrincipal.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(memberPrincipal.getId());
        redis.opsForValue().set("RT:" + memberPrincipal.getId(), refreshToken, Duration.ofSeconds(refreshExpSeconds));

        response.sendRedirect(frontendUrl + "/oauth/callback?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }

    private OAuth2MemberPrincipal resolvePrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2MemberPrincipal memberPrincipal) {
            return memberPrincipal;
        }

        if (authentication instanceof OAuth2AuthenticationToken oauth2Token && principal instanceof OAuth2User oauth2User) {
            String registrationId = oauth2Token.getAuthorizedClientRegistrationId();
            return customOAuth2UserService.loadOrCreateMemberFromAttributes(registrationId, oauth2User.getAttributes());
        }
        return null;
    }
}
