package hong.gom.hongtalk.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hong.gom.hongtalk.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MailService mailService;
	
	private final SpUserRepository spUserRepository;
	
	// TODO 테스트 케이스 작성
	public void addFriends(List<String> friends) {		
		// TODO
		// 1. 트랜잭션 
		// 2. 이미 초대된 사람인지 파악
		// mailService.sendTo(email);
		friends.stream()
		     .forEach(email -> System.out.println(email + " 에게 메일을 보낸다."));
	}
	
	// TODO 테스트 케이스 작성
	public Map<String, List<String>> separeteFriends(List<String> friends) {
		ArrayList<String> existUsers = new ArrayList<>();
		ArrayList<String> notExistUsers = new ArrayList<>();
		Map<String, List<String>> existUsersAndNotExistUsers = new HashMap<>();
		
		friends.stream().forEach(email -> {
			if(spUserRepository.existsByEmail(email)) {
				existUsers.add(email);
			} else {
				notExistUsers.add(email);
			}
		});
		
		existUsersAndNotExistUsers.put("existUsers", existUsers);
		existUsersAndNotExistUsers.put("notExistUsers", notExistUsers);
		
		return existUsersAndNotExistUsers;
	}
	
}