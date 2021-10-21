package hong.gom.hongtalk.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.dto.AddFriendHistoryDTO;
import hong.gom.hongtalk.dto.UserDTO;
import hong.gom.hongtalk.dto.enums.AcceptRequestResult;
import hong.gom.hongtalk.dto.enums.AddFriendHistoryStatus;
import hong.gom.hongtalk.dto.enums.UserStatus;
import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.FriendRelation;
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
	
	// 친구 추가 이메일 전송 로직
	@Transactional
	public List<UserDTO> addFriendsService(List<String> emails, String hostEmail) {
		List<UserDTO> validatedUsers = validateUser(emails, hostEmail);
		addFriendRequest(validatedUsers, hostEmail);
		return validatedUsers;
	}
		
	private List<UserDTO> validateUser(List<String> emails, String hostEmail) {
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
	
	private void addFriendRequest(List<UserDTO> validatedUsers, String hostEmail) {
		SpUser host = spUserRepository.findByEmail(hostEmail);
		
		validatedUsers.stream().forEach(user -> {
			if(user.getStatus() == UserStatus.CAN_ADD) {
				SpUser friend = spUserRepository.findByEmail(user.getEmail());
				
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
								                            .build());
		} else {
			history.setKeyMessage(dto.getKeyMessage());
			addFriendHistoryRepository.save(history);
		}
	}
	
	// 친구 추가 이메일에 상대가 수락을 했을 경우 처리 로직
	@Transactional
	public AcceptRequestResult acceptService(AddFriendHistoryDTO dto) {
		AddFriendHistoryStatus historyStatus = validateHistory(dto);
		
		if(historyStatus == AddFriendHistoryStatus.OK) {
			if(isAlreadyFriend(dto)) {
				return AcceptRequestResult.ALREADY_FRIEND;
			} else {
				setRelation(dto);
				return AcceptRequestResult.OK;
			}
		} else {
			return AcceptRequestResult.NOT_OK;
		} 
		
	}
	
	private AddFriendHistoryStatus validateHistory(AddFriendHistoryDTO dto) {
		AddFriendHistory history = findHistory(dto);
		
		if(history == null) {            
			return AddFriendHistoryStatus.NOT_OK;       // 초대 기록이 없거나 재전송해서 기록이 변경됨
		} else {
			LocalDateTime savedDate = history.getUpdatedAt();
			
			if(LocalDateTime.now().isAfter(savedDate.plusDays(3))) {                    
				return AddFriendHistoryStatus.EXPIRED;  // 유효기간이 지났으면
			} else {                     
				return AddFriendHistoryStatus.OK;       // 정상 처리
			}
		}
	}
	
	private AddFriendHistory findHistory(AddFriendHistoryDTO dto) {
		SpUser user = spUserRepository.findByEmail(dto.getHostEmail());
		SpUser friend = spUserRepository.findByEmail(dto.getFriendEmail());
		String keyMessage = dto.getKeyMessage();
		
		return addFriendHistoryRepository.findByUserAndFriendAndKeyMessage(user, friend, keyMessage);
	}
	
	private void setRelation(AddFriendHistoryDTO dto) {
		SpUser user = spUserRepository.findByEmail(dto.getHostEmail());
		SpUser friend = spUserRepository.findByEmail(dto.getFriendEmail());
		
		FriendRelation fr1 = FriendRelation.builder()
				                           .user(user)
		                                   .friend(friend)
				                           .build();
		
		FriendRelation fr2 = FriendRelation.builder()
                                           .user(friend)
                                           .friend(user)
                                           .build();
		
		friendRelationRepository.saveAll(List.of(fr1, fr2));
	}
	
	private boolean isAlreadyFriend(AddFriendHistoryDTO dto) {
		SpUser user = spUserRepository.findByEmail(dto.getHostEmail());
		SpUser friend = spUserRepository.findByEmail(dto.getFriendEmail());
		
		int friendRelationCount1 = friendRelationRepository.countByUserAndFriend(user, friend);
		int friendRelationCount2 = friendRelationRepository.countByUserAndFriend(friend, user);
		
		if(friendRelationCount1 == 1 || friendRelationCount2 == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// 사용자의 친구 목록을 조회
	public List<String> getFriends(String email) {
		List<String> friendEmails = new ArrayList<>();
		
		SpUser user = spUserRepository.findByEmail(email);
		List<FriendRelation> friendRelations =  friendRelationRepository.findByUser(user);
		
		friendRelations.stream()
		               .forEach(
				          fr -> friendEmails.add(fr.getFriend().getEmail())
				       );
		
		return friendEmails;
	}
}
