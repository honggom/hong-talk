package hong.gom.hongtalk.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hong.gom.hongtalk.entity.SpOAuth2User;
import hong.gom.hongtalk.entity.SpUser;
import hong.gom.hongtalk.service.SpUserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpOAuth2SuccessHandler implements AuthenticationSuccessHandler {
	
    private final SpUserService userService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        Object principal = authentication.getPrincipal();

        if(principal instanceof OidcUser){
            SpOAuth2User oauth = SpOAuth2User.Provider.google.convert((OidcUser) principal);
            SpUser user = userService.load(oauth);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            );
        }
        response.sendRedirect("/");
    }
    
    
}