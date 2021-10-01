import Regex from "/static/js/common/regex.js";
import ElementFactory from "/static/js/common/elementFactory.js";
import Request from "/static/js/common/ajax-request.js";

window.onload = function() {

	const modal = document.getElementById("invite-modal-wrapper");
	const inviteButton = document.getElementById("invite-button");
	const inviteAddButton = document.getElementById("invite-add-button");
	const inviteInput = document.getElementById("invite-input");
	const inviteFriendList = document.getElementById("invite-friend-list");
	const inviteSendButton = document.getElementById("invite-send-button");
	const closeButton = document.getElementById("modal-close-button");
	const loggedUserEmail = document.getElementById("logged-user").innerText;
	
	// 친구 초대하기 모달 창 닫기 이벤트
	closeButton.addEventListener("click", () => {
		modal.style.display = modal.style.display === "block" ? "none" : "block";
	});
	
	// 친구 초대하기 모달 창 열기 이벤트
	inviteButton.addEventListener("click", () => {
		modal.style.display = modal.style.display === "block" ? "none" : "block";
	});

	function deleteSelectedFriend() {
		const selectedFriend = this.parentElement.parentElement;
		const email = this.previousSibling.innerText;
		const index = selectedFriends.indexOf(email);

		selectedFriends.splice(index, 1);
		selectedFriend.remove();
	}

	const selectedFriends = new Array();
	
	// 친구 초대하기 모달내에서 '추가' 버튼 이벤트
	inviteAddButton.addEventListener("click", () => {
		const email = inviteInput.value;

		if (email === loggedUserEmail) {
			alert("본인은 초대할 수 없습니다.");
		} else {
			if (Regex.isEmail(email)) {
				if (!selectedFriends.includes(email)) {
					const newFriend = ElementFactory.makeNewSelectedFriend(email);

					const deleteButton = newFriend.querySelector(".delete-button");
					deleteButton.addEventListener("click", deleteSelectedFriend);

					inviteFriendList.appendChild(newFriend);
					selectedFriends.push(email);
				} else {
					alert("이미 추가된 친구입니다.");
				}
			} else {
				alert("이메일 형식으로 작성해주세요.");
			}
		}
	});
	
	// 친구 초대하기 모달내에서 '전송' 버튼 이벤트
	inviteSendButton.addEventListener("click", () => {
		if (selectedFriends.length == 0) {
			alert("최소 1명 이상 친구를 초대해주세요.");
		} else {
			Request.postAsyncRequest("/invite", selectedFriends, (notExistUsers) => {
				const givenNotExistUsers = JSON.parse(notExistUsers);
				const invitedFriendsCount = selectedFriends.length - givenNotExistUsers.length;
				
				if (selectedFriends.length === givenNotExistUsers.length) {
					alert(`${givenNotExistUsers} : 해당 친구는 존재하지 않습니다.`);	
				} else if (givenNotExistUsers.length === 0) {
					alert("초대가 완료되었습니다."); 
				} else {
					alert(`${invitedFriendsCount}명에게 초대가 완료되었습니다.\n${givenNotExistUsers} : 존재하지 않는 친구입니다.`);
				}
			});
		}
	});

};