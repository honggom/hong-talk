package hong.gom.hongtalk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SpOAuth2SuccessHandler successHandler;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.authorizeRequests(
	                request -> request
	                            .antMatchers("/")
	                            .permitAll()
	                )
	                .logout().logoutSuccessUrl("/")
                .and()
	                .oauth2Login().successHandler(successHandler)
	                .and()
	                .exceptionHandling(e -> e.accessDeniedPage("/access-denied"))
	                ;
        
        http.sessionManagement()
                .maximumSessions(1)               // 세션 최대 허용 수 
                .maxSessionsPreventsLogin(false)  // false ==> 중복 로그인하면 이전 로그인이 풀린다.
                ; 
    }
}
