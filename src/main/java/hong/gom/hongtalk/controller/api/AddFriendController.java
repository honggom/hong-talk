package hong.gom.hongtalk.controller.api;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.hongtalk.dto.UserDTO;
import hong.gom.hongtalk.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AddFriendController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final FriendService friendService;
	
	@PostMapping("/add-friend")
	public List<UserDTO> addFriend(@RequestBody List<String> emails, Principal principal) {
		return friendService.addFriendsService(emails, principal.getName());
	}
	
}