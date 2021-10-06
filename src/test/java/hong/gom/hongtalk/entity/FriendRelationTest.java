package hong.gom.hongtalk.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hong.gom.hongtalk.repository.FriendRelationRepository;
import hong.gom.hongtalk.repository.SpUserRepository;

@SpringBootTest
public class FriendRelationTest {

	@Autowired
	SpUserRepository spUserRepository;

	@Autowired
	FriendRelationRepository friendRelationRepository;

	@Test
	@Transactional 
	@Rollback(true)
	void 친구관게_1대N이_형성된다() {
		// given
		SpUser host = SpUser.builder()
				            .email("hong")
				            .build();

		SpUser friend1 = SpUser.builder()
				               .email("ryeong")
				               .build();
		
		SpUser friend2 = SpUser.builder()
	                           .email("gom")
              	               .build();

		FriendRelation fr = FriendRelation.builder()
				                           .user(host)
				                           .friend(friend1)
				                           .build();
		
		FriendRelation fr2 = FriendRelation.builder()
                                           .user(host)
                                           .friend(friend2)
                                           .build();

		spUserRepository.saveAll(List.of(host, friend1, friend2));
		friendRelationRepository.saveAll(List.of(fr, fr2));
		
		// when
		List<FriendRelation> fs = friendRelationRepository.findByUser(host);
		
		// then
		assertEquals(2, fs.size());
	}
}