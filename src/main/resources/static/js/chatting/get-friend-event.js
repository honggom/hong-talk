import Request from "/static/js/common/ajax-request.js";

window.onload = function() {

	Request.postAsyncRequest("/add-friend", selectedFriends, (validatedUsers) => {
		loadingRing.remove();
		isAbleToClick = true;

		let msg = "";

		JSON.parse(validatedUsers).forEach(user => {
			msg += formatMessage(user) + "\n";
		});

		alert(msg);
	});

}