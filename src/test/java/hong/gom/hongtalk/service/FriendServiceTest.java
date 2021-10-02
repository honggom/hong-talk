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





