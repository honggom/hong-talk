package hong.gom.hongtalk.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import hong.gom.hongtalk.entity.listener.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(SpAuthority.class)
@Table(name = "sp_user_authority")
public class SpAuthority extends BaseEntity implements GrantedAuthority {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    private String authority;

}
