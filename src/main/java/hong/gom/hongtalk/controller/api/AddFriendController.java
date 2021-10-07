package hong.gom.hongtalk.controller.api;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.hongtalk.dto.UserDTO;
import hong.gom.hongtalk.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/add-friend")
public class AddFriendController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final FriendService friendService;
	
	@PostMapping("")
	public List<UserDTO> addFriend(@RequestBody List<String> emails, Principal principal) {
		return friendService.addFriendsService(emails, principal.getName());
	}
	
	@PostMapping("/accpet")
	public void accpet( ) {
		
	}
	
	// 2. 친구 추가 거절 컨트롤러 작성 PUT(수정) 메서드
	// @PutMapping("/refuse")
	
}