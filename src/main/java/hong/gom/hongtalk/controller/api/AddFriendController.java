package hong.gom.hongtalk.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.hongtalk.service.InviteService;
import hong.gom.hongtalk.service.MailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
public class InviteController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final InviteService inviteService;
	
	@PostMapping("")
	public ArrayList<String> invite(@RequestBody ArrayList<String> friends) {
		ArrayList<String> notExistUsers = inviteService.invite(friends);
		return notExistUsers;
	}
	
}