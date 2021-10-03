package hong.gom.hongtalk.controller.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.hongtalk.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AddFriendController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final FriendService friendService;
	
	@PostMapping("/add-friend")
	public List<String> addFriend(@RequestBody List<String> friends) {
		Map<String, List<String>> existUsersAndNotExistUsers = friendService.separeteFriends(friends);
		friendService.addFriends(existUsersAndNotExistUsers.get("existUsers"));
		return existUsersAndNotExistUsers.get("notExistUsers");
	}
	
}