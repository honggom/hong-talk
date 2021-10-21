function postAsyncRequest(url, data, callback) {
	const xhr = new XMLHttpRequest();

	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-type", "application/json");

	xhr.onload = () => {
		if (xhr.status == 200) {
			callback(xhr.response);
		} else {
			location.reload();
		}
	}
	xhr.send(JSON.stringify(data));
}

function getAsyncRequest(url, callback) {
	const xhr = new XMLHttpRequest();

	xhr.open("GET", url, true);

	xhr.onload = () => {
		if (xhr.status == 200) {
			callback(xhr.response);
		} else {
			location.reload();
		}
	}
	xhr.send();
}

export default {postAsyncRequest, getAsyncRequest}