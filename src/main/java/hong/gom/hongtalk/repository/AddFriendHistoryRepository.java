package hong.gom.hongtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.hongtalk.entity.AddFriendHistory;
import hong.gom.hongtalk.entity.SpUser;

public interface AddFriendHistoryRepository extends JpaRepository<AddFriendHistory, Long>{
	
	AddFriendHistory findByUserAndFriend(SpUser user, SpUser friend);
	
	AddFriendHistory findByUserAndFriendAndKeyMessage(SpUser user, SpUser friend, String keyMessage);
	
	void deleteByUserAndFriend(SpUser user, SpUser friend);

}