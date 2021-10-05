package hong.gom.hongtalk.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hong.gom.hongtalk.entity.FriendRelation;
import hong.gom.hongtalk.entity.SpUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FriendRelationRepositoryTest {
	
	@Autowired
	SpUserRepository spUserRepository;
	
	@Autowired
	FriendRelationRepository friendRelationRepository;
	
	@Test
	void 친구관계_형성_후_해당_친구관계가_카운트_된다() {
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
		int count = friendRelationRepository.countByUserAndFriend(host, friend);
		
		// then
		assertEquals(1, count);
	}

}
