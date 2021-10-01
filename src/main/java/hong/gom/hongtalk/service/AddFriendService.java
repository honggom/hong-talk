package hong.gom.hongtalk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddFriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MailService mailService;
	
	private final SpUserRepository spUserRepository;
	
	public ArrayList<String> addFriend(ArrayList<String> friends) {
		
		ArrayList<String> notExistUsers = new ArrayList<>();
		
		friends.stream().forEach(email -> {
			if(spUserRepository.existsByEmail(email)) {
				// TODO 메일 전송 성공 후 로직
				// 1. 트랜잭션 
				// 2. 초대 승락 / 거절에 따른 대응
				// 3. 초대 가능 기한??
				// mailService.sendTo(email);
			} else {
				notExistUsers.add(email);
			}
		});
		return notExistUsers;
	}
	
}