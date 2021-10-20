package hong.gom.hongtalk.controller.api;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.hongtalk.dto.AddFriendHistoryDTO;
import hong.gom.hongtalk.dto.UserDTO;
import hong.gom.hongtalk.dto.enums.AcceptRequestResult;
import hong.gom.hongtalk.dto.enums.AddFriendHistoryStatus;
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
	
	@GetMapping("/accept")
	public ResponseEntity<String> accept(AddFriendHistoryDTO acceptDto) {
		AcceptRequestResult status = friendService.acceptService(acceptDto);
		
		if(status == AcceptRequestResult.OK) {
			return new ResponseEntity<String>(acceptDto.getHostEmail() + "님과 친구가 되었습니다.", HttpStatus.OK);
		} else if(status == AcceptRequestResult.ALREADY_FRIEND) {
			return new ResponseEntity<String>(acceptDto.getHostEmail() + "님과 이미 친구입니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("유효하지 않은 초대 이메일입니다.", HttpStatus.OK);
		}		
	}
	
}