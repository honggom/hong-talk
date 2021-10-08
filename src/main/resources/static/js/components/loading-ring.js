export default class LoadingRing extends HTMLElement {
	constructor() {
		super();
	}

	// 커스텀 엘리먼트가 생성될때 실행된다.
	connectedCallback() {
		this.render(this.title);
	}

	// 해당요소가 새로운 문서로 이동 될 때마다 호출 된다. 
	adoptCallback() { }

	// 요소의 속성이 추가, 제거, 업데이트, 교체되는 부분을 관찰하고 호출된다. 
	// 이때 observedAttributes 속성에 나열된 특성에서만 호출된다.
	attributeChangedCallback(attrName, oldVal, newVal) { }

	//attributeChangedCallback 에서 관찰하는 항목을 리턴한다.
	static get observedAttributes() {
		return ['title'];
	}

	get title() {
		return this.getAttribute('title');
	}

	// custom element 가 제거될때 호출된다. 
	disconnectedCallback() { }

	render(title) {
		this.innerHTML = `<div class="hong-talk-loading">
				            <div class="hong-talk-ring"></div>
			              </div>
			              <h3 class="hong-talk-ring-title">${title}</h3>
			              
			              <style>
			                .hong-talk-loading .hong-talk-ring {
							    animation: loadingD 1.5s 0.3s cubic-bezier(0.17, 0.37, 0.43, 0.67) infinite;
							}
							
							.hong-talk-ring {
							    width: 10px;
							    height: 10px;
							    margin: 0 auto;
							    padding: 10px;
							    border: 7px dashed #4b9cdb;
							    border-radius: 100%;
							}
							
							.hong-talk-ring-title {
								text-align: center;
							}
							
							@keyframes loadingB {
							    0% {
							        width: 15px;
							    }
							
							    50% {
							        width: 35px;
							    }
							
							    100% {
							        width: 15px;
							    }
							}
							
							@keyframes loadingC {
							    0% {
							        transform: translate(0, 0);
							    }
							
							    50% {
							        transform: translate(0, 15px);
							    }
							
							    100% {
							        transform: translate(0, 0);
							    }
							}
							
							@keyframes loadingD {
							    0% {
							        transform: rotate(0deg);
							    }
							
							    50% {
							        transform: rotate(180deg);
							    }
							
							    100% {
							        transform: rotate(360deg);
							    }
							}
							
							@keyframes loadingE {
							    0% {
							        transform: rotate(0deg);
							    }
							
							    100% {
							        transform: rotate(360deg);
							    }
							}
							
							@keyframes loadingF {
							    0% {
							        opacity: 0;
							    }
							
							    100% {
							        opacity: 1;
							    }
							}
							
							@keyframes loadingG {
							    0% {
							        transform: translate(0, 0) rotate(0deg);
							    }
							
							    50% {
							        transform: translate(70px, 0) rotate(360deg);
							    }
							
							    100% {
							        transform: translate(0, 0) rotate(0deg);
							    }
							}
							
							@keyframes loadingH {
							    0% {
							        width: 15px;
							    }
							
							    50% {
							        width: 35px;
							        padding: 4px;
							    }
							
							    100% {
							        width: 15px;
							    }
							}
							
							@keyframes loadingI {
							    100% {
							        transform: rotate(360deg);
							    }
							}
							
							@keyframes bounce {
							
							    0%,
							    100% {
							        transform: scale(0);
							    }
							
							    50% {
							        transform: scale(1);
							    }
							}
							
							@keyframes loadingJ {
							    0%,
							    100% {
							        transform: translate(0, 0);
							    }
							
							    50% {
							        transform: translate(80px, 0);
							        background-color: #f5634a;
							        width: 25px;
							    }
							}
			              </style>`;
	}
}