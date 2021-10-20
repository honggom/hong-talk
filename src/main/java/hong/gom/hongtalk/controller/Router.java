package hong.gom.hongtalk.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Router {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String home(){
        return "home";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
    	return "error/access-denied";
    }
    
    @GetMapping("/chatting")
    public Object check(Principal principal, HttpSession session){
        logger.info(principal.getName());
        return "chatting/chatting";
    }
    
}