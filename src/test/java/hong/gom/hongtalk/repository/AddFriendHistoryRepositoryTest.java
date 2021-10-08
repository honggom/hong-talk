package hong.gom.hongtalk.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.SpUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AddFriendHistoryRepositoryTest {
	
	@Autowired
	AddFriendHistoryRepository addFriendHistoryRepository;
	
	@Autowired
	SpUserRepository spUserRepository;

	@Test
	void 친구초대_기록을_저장하고_해당_기록을_초대한자_초대당한자를_파라미터로_가져올_수_있다() {
		// given
		SpUser from = SpUser.builder().email("from").build();
		SpUser to = SpUser.builder().email("to").build();
		
		spUserRepository.saveAll(List.of(from, to));
		
		addFriendHistoryRepository.save(
										AddFriendHistory.builder()
														.user(from)
														.friend(to)
														.keyMessage("test")
														.build()
				                       );
		
		// when
		AddFriendHistory history = addFriendHistoryRepository.findByUserAndFriend(from, to);
		
		// then
		assertEquals("test", history.getKeyMessage());
	}

}
