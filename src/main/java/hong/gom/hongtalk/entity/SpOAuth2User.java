package hong.gom.hongtalk.entity;

import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import hong.gom.hongtalk.entity.listener.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static java.lang.String.format;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sp_oauth2_user")
public class SpOAuth2User extends BaseEntity{

    @Id
    private String oauth2UserId; // google-{id}, naver-{id} 각 회사가 제공하는 id (primary key)

    private Long userId;

    private String name;

    private String email;

    private Provider provider;

    public static enum Provider {
        google {
            public SpOAuth2User convert(OAuth2User user){
                return SpOAuth2User.builder()
                        .oauth2UserId(format("%s_%s", name(), user.getAttribute("sub")))
                        .provider(google)
                        .email(user.getAttribute("email"))
                        .name(user.getAttribute("name"))
                        .build();
            }
        },
        naver {
            public SpOAuth2User convert(OAuth2User userInfo){
                return null;
            }
        };
        public abstract SpOAuth2User convert(OAuth2User userInfo);
    }
}
