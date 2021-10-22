import Regex from "/static/js/common/regex.js";
import ElementFactory from "/static/js/common/element-factory.js";
import Request from "/static/js/common/ajax-request.js";

import LoadingRing from "/static/js/components/loading-ring.js";
window.customElements.define("loading-ring", LoadingRing);

window.onload = function() {
	const modal = document.getElementById("add-friend-modal-wrapper");
	const anotherModal = document.getElementById("make-chatting-room-modal-wrapper");
	const addFriendModalOpenButton = document.getElementById("add-friend-modal-open-button");
	const addFriendButton = document.getElementById("add-friend-button");
	const addFriendInput = document.getElementById("add-friend-input");
	const addFriendList = document.getElementById("add-friend-list");
	const sendButton = document.getElementById("send-button");
	const addFriendModalCloseButton = document.getElementById("add-friend-modal-close-button");
	const loggedUserEmail = document.getElementById("logged-user").innerText;
	const friendListWrapper = document.getElementById("friend-list-wrapper");

	// 친구 추가 모달 창 닫기 이벤트
	addFriendModalCloseButton.addEventListener("click", () => {
		modal.style.display = modal.style.display === "block" ? "none" : "block";
	});

	// 친구 추가 모달 창 열기 이벤트
	addFriendModalOpenButton.addEventListener("click", () => {
		anotherModal.style.display = "none";
		modal.style.display = modal.style.display === "block" ? "none" : "block";
	});

	const selectedFriends = new Array();

	// 친구 추가 모달내에서 '추가' 버튼 이벤트
	addFriendButton.addEventListener("click", () => {
		const email = addFriendInput.value;

		if (email === loggedUserEmail) {
			alert("본인은 초대할 수 없습니다.");
		} else {
			if (Regex.isEmail(email)) {
				if (!selectedFriends.includes(email)) {
					const newFriend = ElementFactory.makeNewSelectedFriend(email);

					const deleteButton = newFriend.querySelector(".delete-button");
					deleteButton.addEventListener("click", deleteSelectedFriend);

					addFriendList.appendChild(newFriend);
					selectedFriends.push(email);
				} else {
					alert("이미 추가된 친구입니다.");
				}
			} else {
				alert("이메일 형식으로 작성해주세요.");
			}
		}
	});

	function deleteSelectedFriend() {
		const selectedFriend = this.parentElement.parentElement;
		const email = this.previousSibling.innerText;
		const index = selectedFriends.indexOf(email);

		selectedFriends.splice(index, 1);
		selectedFriend.remove();
	}

	let isAbleToClick = true;

	// 친구 초대 모달내에서 '전송' 버튼 이벤트
	sendButton.addEventListener("click", () => {
		if (selectedFriends.length == 0) {
			alert("최소 1명 이상 친구를 초대해주세요.");
		} else if (!isAbleToClick) {
			alert("메일 전송 중입니다.");
		} else {
			const loadingRing = makeLoadingRingElement("초대 메일 전송 중");
			friendListWrapper.appendChild(loadingRing);

			modal.style.display = "none";
			isAbleToClick = false;

			Request.postAsyncRequest("/friend/add", selectedFriends, (validatedUsers) => {
				loadingRing.remove();
				isAbleToClick = true;

				let msg = "";

				JSON.parse(validatedUsers).forEach(user => {
					msg += formatMessage(user) + "\n";
				});

				alert(msg);
			});
		}
	});

	function makeLoadingRingElement(title) {
		let loadingRing = document.createElement("loading-ring");
		loadingRing.setAttribute("title", title);
		return loadingRing;
	}

	function formatMessage(user) {
		let msg = "";

		if (user.status === "CAN_ADD") {
			msg += `${user.email} 님에게 초대 메일을 보냈습니다.`;
		} else if (user.status === "ALREADY_FRIEND") {
			msg += `${user.email} 님과는 이미 친구입니다.`;
		} else {
			msg += `${user.email} 는 존재하지 않는 회원입니다.`;
		}
		return msg;
	}

	/*	
	let ws = new WebSocket("ws://" + location.host + "/web-socket");
	
	ws.onopen = function(data) {
		console.log(data);
		console.log("소켓 열림");
		ws.send("들리시나요?..");
	}

	ws.onmessage = function(data) {
		console.log("데이터 도착함");
		console.log(data);
	}

	ws.onclose = function(e){
		console.log("소켓 닫힘");
		console.log(e);
	}
	*/
}