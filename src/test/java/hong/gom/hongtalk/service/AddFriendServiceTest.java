package hong.gom.hongtalk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.entity.SpUser;
import hong.gom.hongtalk.repository.SpUserRepository;

@SpringBootTest
@Transactional
@Rollback(true)
public class AddFriendServiceTest {
	
	@Autowired
	AddFriendService addFriendService;
	
	@Autowired
	SpUserRepository spUserRepository;
	
	@Test
	void 친구추가시_가입여부가_파악되는지_테스트() {
		// given
		SpUser user1 = SpUser.builder()
				.email("user1@naver.com")
				.build();
		
		SpUser user2 = SpUser.builder()
				.email("user2@naver.com")
				.build();
		
		SpUser user3 = SpUser.builder()
				.email("user3@naver.com")
				.build();
		
		spUserRepository.saveAll(List.of(user1, user2, user3));
		
		ArrayList<String> friends = new ArrayList<>();
		friends.add("user1@naver.com");
		friends.add("user2@naver.com");
		friends.add("user3@naver.com");
		friends.add("user4@naver.com");
		friends.add("user5@naver.com");
				
		// when
		ArrayList<String> notExistUsers = addFriendService.addFriend(friends);
		
		// then
		assertEquals(2, notExistUsers.size());
	}
}





