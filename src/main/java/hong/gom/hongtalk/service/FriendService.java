package hong.gom.hongtalk.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.dto.User;
import hong.gom.hongtalk.dto.enums.UserStatus;
import hong.gom.hongtalk.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MailService mailService;
	
	private final SpUserRepository spUserRepository;
	
	public void addFriends(List<String> emails) {		
		// TODO
		// 1. 트랜잭션 
		// 2. 이미 초대된 사람인지 파악
		// mailService.sendTo(email);
		emails.stream()
		     .forEach(email -> System.out.println(email + " 에게 메일을 보낸다."));
	}
	
	public List<User> validate(List<String> emails) {
		List<User> validatedUsers = new ArrayList<>();
		
		emails.stream().forEach(email -> {
			User validateUser = new User();
			validateUser.setEmail(email);
			
			if(!spUserRepository.existsByEmail(email)) {
				validateUser.setStatus(UserStatus.NOT_EXIST);
			} else {
				if(/* TODO 이미 친구인지 파악 */true) {
					validateUser.setStatus(UserStatus.ALREADY_FRIEND);
				} else {
					validateUser.setStatus(UserStatus.CAN_ADD);
				}
			}
			validatedUsers.add(validateUser);
		});
		
		return validatedUsers;
	}
	
	public boolean isAlreadyFriend(String from, String to) {
		return false;
	}
	
	
}