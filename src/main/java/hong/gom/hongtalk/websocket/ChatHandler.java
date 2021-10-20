package hong.gom.hongtalk.websocket;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static List<WebSocketSession> sessions = new ArrayList<>();
	
	// 메시지 수신 시 동작
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		
		System.out.println(msg);
		
		for (WebSocketSession ws : sessions) {
			ws.sendMessage(new TextMessage(msg));
		}
	}
	
	// 소켓 연결 후 동작
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("----소켓 연결 됨----");
		System.out.println(session);
		System.out.println("----소켓 연결 됨----");
		sessions.add(session);
	}
	
	// 소켓 종료 후 동작
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("----소켓 종료 됨----");
		sessions.remove(session);
		System.out.println("----소켓 종료 됨----");
	}
}