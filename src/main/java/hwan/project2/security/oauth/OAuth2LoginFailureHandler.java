package hwan.project2.security.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${app.frontend-url:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AuthenticationException oae) {
            log.error("[OAUTH2] 인증 실패 - errorCode={}, description={}, uri={}",
                    oae.getError().getErrorCode(),
                    oae.getError().getDescription(),
                    oae.getError().getUri(),
                    oae);
        } else {
            log.error("[OAUTH2] 인증 실패 - {}: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        }
        response.sendRedirect(frontendUrl + "/login?error=oauth_failed");
    }
}
