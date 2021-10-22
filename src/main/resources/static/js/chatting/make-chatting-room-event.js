const modal = document.getElementById("make-chatting-room-modal-wrapper");
const anotherModal = document.getElementById("add-friend-modal-wrapper");
const makeChattingRoomButton = document.getElementById("make-chatting-room-button");

// 모달 창 열기 이벤트
makeChattingRoomButton.addEventListener("click", () => {
	anotherModal.style.display = "none"; 
	modal.style.display = modal.style.display === "block" ? "none" : "block";
});