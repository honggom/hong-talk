package hong.gom.hongtalk.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.dto.User;
import hong.gom.hongtalk.dto.enums.UserStatus;
import hong.gom.hongtalk.entity.SpUser;
import hong.gom.hongtalk.repository.FriendRelationRepository;
import hong.gom.hongtalk.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MailService mailService;
	
	private final SpUserRepository spUserRepository;
	
	private final FriendRelationRepository friendRelationRepository;
	
	@Transactional
	public void addFriends(List<String> emails, String hostEmail) {		
		
		List<User> validatedUsers = validate(emails, hostEmail);
		
		// TODO 추가 기능 작성
		validatedUsers.stream().forEach(user -> {
			System.out.println(user.getEmail() + " : " + user.getStatus());
		});
	}
	
	public List<User> validate(List<String> emails, String hostEmail) {
		List<User> validatedUsers = new ArrayList<>();
		SpUser hostUser = spUserRepository.findByEmail(hostEmail);
		
		emails.stream().forEach(email -> {
			User validateUser = new User();
			validateUser.setEmail(email);
			
			if(!spUserRepository.existsByEmail(email)) {                                   // 해당 친구가 DB에 존재하지 않는 경우
				validateUser.setStatus(UserStatus.NOT_EXIST);                             
			} else {
				SpUser user = spUserRepository.findByEmail(email);                    
				
				if(friendRelationRepository.countByUserAndFriend(hostUser, user) == 0 &&   // 친구 관계가 형성돼 있지 않은 경우 
				   friendRelationRepository.countByUserAndFriend(user, hostUser) == 0) { 
					validateUser.setStatus(UserStatus.CAN_ADD);
				} else {                                                                   
					validateUser.setStatus(UserStatus.ALREADY_FRIEND);                     // 이미 친구인 경우
				}
			}
			validatedUsers.add(validateUser);
		});
		return validatedUsers;
	}
}