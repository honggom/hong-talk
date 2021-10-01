function makeNewSelectedFriend(email) {
	const li = document.createElement("li");
	const div = document.createElement("div");
	
	const span = document.createElement("span");
	span.innerText = email;
	
	const button = document.createElement("button");
	button.innerText = "X";
	button.className = "delete-button";
	
	div.appendChild(span)
	div.appendChild(button);
	li.appendChild(div);
		
	return li;
}

export default {makeNewSelectedFriend}