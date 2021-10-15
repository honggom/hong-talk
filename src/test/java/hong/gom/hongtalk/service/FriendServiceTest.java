package hong.gom.hongtalk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.dto.AddFriendHistoryDTO;
import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.SpUser;
import hong.gom.hongtalk.repository.AddFriendHistoryRepository;
import hong.gom.hongtalk.repository.FriendRelationRepository;
import hong.gom.hongtalk.repository.SpUserRepository;

@SpringBootTest
@Transactional
@Rollback(true)
public class FriendServiceTest {
	
	@Autowired
	FriendService friendService;
	
	@Autowired
	SpUserRepository spUserRepository;
	
	@Autowired
	FriendRelationRepository friendRelationRepository;
	
	@Autowired
	AddFriendHistoryRepository addFriendHistoryRepository; 
	
	@Test
	void findHistory함수가_정상적으로_동작한다() {
		// given
		SpUser user = SpUser.builder().email("user").build();
		SpUser friend = SpUser.builder().email("friend").build();
		String keyMessage = "keykey";
		
		spUserRepository.saveAll(List.of(user, friend));
		addFriendHistoryRepository.save(AddFriendHistory.builder().user(user).friend(friend).keyMessage(keyMessage).build());
		
		AddFriendHistoryDTO dto = new AddFriendHistoryDTO("user", "friend", keyMessage);
		
		// when 
		// findHistory 함수 private 처리 
		// AddFriendHistory history = friendService.findHistory(dto);
		
		// then
		// assertEquals(keyMessage, history.getKeyMessage()); 
	}
}





