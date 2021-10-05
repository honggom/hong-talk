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
	void 친구관게_1대N이_형성되는지_테스트() {
		// given
		SpUser host = SpUser.builder()
				            .email("hong")
				            .build();

		SpUser friend = SpUser.builder()
				               .email("ryeong")
				               .build();


		FriendRelation fr = FriendRelation.builder()
				                           .user(host)
				                           .friend(friend)
				                           .build();

		spUserRepository.saveAll(List.of(host, friend));
		friendRelationRepository.saveAll(List.of(fr));
		
		// when
		List<FriendRelation> fs = friendRelationRepository.findByUser(host);
		
		// then
		assertEquals("ryeong", fs.get(0).getFriend().getEmail());
	}
}