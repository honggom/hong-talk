package hong.gom.hongtalk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.dto.AddFriendHistoryDTO;
import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.FriendRelation;
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
	
	
	// findHistory 함수 private 처리 
	@Test
	@Disabled
	void 친구초대에_대한_기록을_저장하고_조회된다() {
		// given
		SpUser user = SpUser.builder().email("user").build();
		SpUser friend = SpUser.builder().email("friend").build();
		String keyMessage = "keykey";
		
		spUserRepository.saveAll(List.of(user, friend));
		addFriendHistoryRepository.save(AddFriendHistory.builder().user(user).friend(friend).keyMessage(keyMessage).build());
		
		AddFriendHistoryDTO dto = new AddFriendHistoryDTO("user", "friend", keyMessage);
		
		// when 
		// AddFriendHistory history = friendService.findHistory(dto);
		
		// then
		// assertEquals(keyMessage, history.getKeyMessage()); 
	}
	
	@Test
	void 친구관계를_형성하고_친구관계가_조회된다() {
		// given
		SpUser a = SpUser.builder().email("a").build();
		SpUser b = SpUser.builder().email("b").build();
		SpUser c = SpUser.builder().email("c").build();
		SpUser d = SpUser.builder().email("d").build();
		
		spUserRepository.saveAll(List.of(a, b, c, d));
		
		FriendRelation ab = FriendRelation.builder().user(a).friend(b).build();
		FriendRelation ac = FriendRelation.builder().user(a).friend(c).build();
		FriendRelation ad = FriendRelation.builder().user(a).friend(d).build();
		
		FriendRelation ba = FriendRelation.builder().user(b).friend(a).build();
		FriendRelation ca = FriendRelation.builder().user(c).friend(a).build();
		FriendRelation da = FriendRelation.builder().user(d).friend(a).build();
		
		friendRelationRepository.saveAll(List.of(ab, ac, ad, ba, ca, da));
		
		// when
		List<String> emails = friendService.getFriends("a");
		
		// then
		assertEquals(3, emails.size());		
	}
}





