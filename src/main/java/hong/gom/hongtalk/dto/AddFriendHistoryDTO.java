package hong.gom.hongtalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddFriendHistoryDTO {
	
	private String hostEmail;
	private String friendEmail;
	private String keyMessage;

}
