import Request from "/static/js/common/ajax-request.js";

window.onload = function() {

	Request.getAsyncRequest("/friend", (friends) => {
		console.log("친구 목록");
		console.log(friends);
		console.log("친구 목록");
	});

}