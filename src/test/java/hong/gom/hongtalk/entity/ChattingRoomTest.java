package hong.gom.hongtalk.entity;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hong.gom.hongtalk.repository.ChattingRoomRepository;
import hong.gom.hongtalk.repository.SpUserRepository;

@SpringBootTest
class ChattingRoomTest {

	@Autowired
	private ChattingRoomRepository chattingRoomRepository;
	
	@Autowired
	private SpUserRepository spUserRepository;
	
	@Test
	@Transactional
	void SpUser와_ChattingRoom이_1대N_관계가_형성되는지_테스트() {
		//given
		ChattingRoom cttr = new ChattingRoom();
		
		SpUser user1 = SpUser.builder()
							.chattingRoom(cttr)
							.email("user1")
							.build();
		
		SpUser user2 = SpUser.builder()
							.chattingRoom(cttr)
							.email("user2")
							.build();
		
		SpUser user3 = SpUser.builder()
							.chattingRoom(cttr)
							.email("user3")
							.build();
		
		List<SpUser> users = new ArrayList<>();

		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		//when
		chattingRoomRepository.save(cttr);
		spUserRepository.saveAll(users);
		
		//then
		ChattingRoom savedCttr = chattingRoomRepository.findAll().get(0);
		List<SpUser> savedUsers = spUserRepository.findAll();
		
		for(SpUser user : savedUsers) {
			assertSame(user.getChattingRoom(), savedCttr);
		}
	}
}