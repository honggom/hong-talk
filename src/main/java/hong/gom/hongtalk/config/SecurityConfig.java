package hong.gom.hongtalk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// TODO
	// 1. 채팅구역에 버튼 및 클릭 이벤트 만들기
	// 2. 채팅방 엔티티 설계 
	// 3. 클릭 이벤트 => 채팅방 생성기능
	
    private final SpOAuth2SuccessHandler successHandler;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
              .disable()
            .authorizeRequests(
            		request -> request.antMatchers("/", "/access-denied")
            		                    .permitAll()
            		                  .anyRequest()
            		                    .authenticated()
            		)
            .logout()
              .logoutSuccessUrl("/")
          .and()
            .oauth2Login()
              .successHandler(successHandler)
          .and()
            .exceptionHandling(
            		e -> e.accessDeniedPage("/access-denied")
            		)
//            .sessionManagement()
//              .maximumSessions(1)               
//              .maxSessionsPreventsLogin(false)
	    ;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
}
