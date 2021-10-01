package hong.gom.hongtalk.repository;

import hong.gom.hongtalk.entity.SpUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpUserRepository extends JpaRepository<SpUser, Long> {

    Optional<SpUser> findSpUserByEmail(String email);
    
    boolean existsByEmail(String email);

}
