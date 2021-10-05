package hong.gom.hongtalk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.hongtalk.entity.FriendRelation;
import hong.gom.hongtalk.entity.SpUser;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
	
	List<FriendRelation> findByUser(SpUser user);
	
}