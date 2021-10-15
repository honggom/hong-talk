package hong.gom.hongtalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendHistoryDTO {
	
	private String hostEmail;
	private String friendEmail;
	private String keyMessage;

}
