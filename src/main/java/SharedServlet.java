import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.annotation.WebServlet;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/SharedServlet")
public class SharedServlet {
    private static Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        sessions.add(session);
        System.out.println("WebSocket connection opened: " + session.getId());
    }
    @OnMessage
    public void onMessage(String message, Session session) {
    	//may just be able to send it back no change
    	System.out.println(message);
    	broadcast(message, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    	System.out.println("WebSocket connection closed: " + session.getId());
    	sessions.remove(session);
    }

    private void broadcast(String message, Session sender) {
        for (Session session : sessions) {
            if (!session.equals(sender)) {
                try {
                    System.out.println(session.getId());
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
