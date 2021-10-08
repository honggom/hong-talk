package hong.gom.hongtalk.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.dto.AddFriendHistoryDTO;
import hong.gom.hongtalk.dto.UserDTO;
import hong.gom.hongtalk.dto.enums.UserStatus;
import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.SpUser;
import hong.gom.hongtalk.repository.AddFriendHistoryRepository;
import hong.gom.hongtalk.repository.FriendRelationRepository;
import hong.gom.hongtalk.repository.SpUserRepository;
import hong.gom.hongtalk.util.RandomWordGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MailService mailService;
	
	private final SpUserRepository spUserRepository;
	
	private final FriendRelationRepository friendRelationRepository;
	
	private final AddFriendHistoryRepository addFriendHistoryRepository; 
	
	@Transactional
	public List<UserDTO> addFriendsService(List<String> emails, String hostEmail) {
		List<UserDTO> validatedUsers = validate(emails, hostEmail);
		addFriends(validatedUsers, hostEmail);
		return validatedUsers;
	}
		
	private List<UserDTO> validate(List<String> emails, String hostEmail) {
		List<UserDTO> validatedUsers = new ArrayList<>();
		SpUser hostUser = spUserRepository.findByEmail(hostEmail);
		
		emails.stream().forEach(email -> {
			UserDTO validatedUser = new UserDTO();
			validatedUser.setEmail(email);
			
			if(!spUserRepository.existsByEmail(email)) {                                   // 해당 친구가 DB에 존재하지 않는 경우
				validatedUser.setStatus(UserStatus.NOT_EXIST);                             
			} else {
				SpUser user = spUserRepository.findByEmail(email);                    
				
				if(friendRelationRepository.countByUserAndFriend(hostUser, user) == 0 &&   // 친구 관계가 형성돼 있지 않은 경우 
				   friendRelationRepository.countByUserAndFriend(user, hostUser) == 0) { 
					validatedUser.setStatus(UserStatus.CAN_ADD);
				} else {                                                                   
					validatedUser.setStatus(UserStatus.ALREADY_FRIEND);                     // 이미 친구인 경우
				}
			}
			validatedUsers.add(validatedUser);
		});
		return validatedUsers;
	}
	
	private void addFriends(List<UserDTO> validatedUsers, String hostEmail) {				
		validatedUsers.stream().forEach(user -> {
			if(user.getStatus() == UserStatus.CAN_ADD) {
				AddFriendHistoryDTO addFriendHistoryDTO = AddFriendHistoryDTO.builder()
						                                .hostEmail(hostEmail)
						                                .friendEmail(user.getEmail())
						                                .keyMessage(RandomWordGenerator.generateRandomWord())
						                                .build();
				
				mailService.sendTo(addFriendHistoryDTO);
				setHistory(addFriendHistoryDTO);
			}
		});
	}
	
	private void setHistory(AddFriendHistoryDTO dto) {
		SpUser from = spUserRepository.findByEmail(dto.getHostEmail());
		SpUser to = spUserRepository.findByEmail(dto.getFriendEmail());
		AddFriendHistory history = addFriendHistoryRepository.findByUserAndFriend(from, to);
		
		if(history == null) { // 이전에 초대한 기록이 없으면 
			addFriendHistoryRepository.save(AddFriendHistory.builder()
								                    .user(from)
								                    .friend(to)
								                    .keyMessage(dto.getKeyMessage())
								                    .build()
                                            );
		} else {
			history.setKeyMessage(dto.getKeyMessage());
			addFriendHistoryRepository.save(history);
		}
	}
}









