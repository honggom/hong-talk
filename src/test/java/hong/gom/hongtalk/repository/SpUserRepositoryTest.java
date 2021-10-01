package hong.gom.hongtalk.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.entity.SpUser;

@SpringBootTest
@Transactional
class SpUserRepositoryTest {
	
	@Autowired
	SpUserRepository spUserRepository;
	
	@Test
	@Rollback(true)
	void existsByEmail_함수_동작_테스트() {
		// given
		SpUser user1 = SpUser.builder()
						.email("cavok699@naver.com")
						.build();
		
		// when
		spUserRepository.save(user1);
		
		// then
		assertEquals(true, spUserRepository.existsByEmail("cavok699@naver.com"));
		
	}
}
