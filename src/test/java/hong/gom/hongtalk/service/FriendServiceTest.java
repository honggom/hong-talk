package hong.gom.hongtalk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

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
public class FriendServiceTest {
	
	@Autowired
	FriendService friendService;
	
	@Autowired
	SpUserRepository spUserRepository;
	
	// TODO 테스트 케이스 수정
	/*
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
		ArrayList<String> notExistUsers = friendService.addFriend(friends);
		
		// then
		assertEquals(2, notExistUsers.size());
	}
	*/
	
	@Test
	void 존재하는_유저와_존재하지_않는_유저를_구분한다() {
		// given
		SpUser user1 = SpUser.builder()
				.email("a@naver.com")
				.build();
		
		SpUser user2 = SpUser.builder()
				.email("b@naver.com")
				.build();
		
		spUserRepository.saveAll(List.of(user1, user2));
		List<String> friends = List.of("a@naver.com", "b@naver.com", "notExist@naver.com");
		
		// when
		Map<String, List<String>> existUsersAndNotExistUsers = friendService.separeteFriends(friends);
		
		// then
		assertEquals(2, existUsersAndNotExistUsers.get("existUsers").stream().count());
		assertEquals(1, existUsersAndNotExistUsers.get("notExistUsers").stream().count());
	}
}





