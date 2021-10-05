package hong.gom.hongtalk.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.entity.SpUser;

@DataJpaTest
class SpUserRepositoryTest {
	
	@Autowired
	SpUserRepository spUserRepository;
	
	@Test
	void DB상에_해당_이메일의_유저가_존재하면_true를_반환한다() {
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
