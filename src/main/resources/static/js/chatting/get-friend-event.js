import Request from "/static/js/common/ajax-request.js";
import ElementFactory from "/static/js/common/element-factory.js";

const friendListWrapper = document.getElementById("friend-list-wrapper");

Request.getAsyncRequest("/friend", (friends) => {
	const friendList = JSON.parse(friends);

	if (friendList.length > 0) {
		friendList.forEach((friend) => {
			friendListWrapper.appendChild(ElementFactory.makeFriendIcon(friend));
		})
	}
});
