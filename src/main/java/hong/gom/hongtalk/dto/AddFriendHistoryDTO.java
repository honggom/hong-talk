package hong.gom.hongtalk.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddFriendHistoryDTO {
	
	private String hostEmail;
	private String friendEmail;
	private String keyMessage;

}
