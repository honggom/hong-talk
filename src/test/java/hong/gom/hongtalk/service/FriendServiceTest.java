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

}





