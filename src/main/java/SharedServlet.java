
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

import com.google.gson.Gson;

@ServerEndpoint("/SharedServlet")
public class SharedServlet {
	private static Map<String, Set<Session>> sessionsMap = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
    	sessionsMap.computeIfAbsent("0000", k -> new CopyOnWriteArraySet<>()).add(session);
        System.out.println("WebSocket connection opened: " + session.getId());
    }
    @OnMessage
    public void onMessage(String message, Session session) {
    	Gson gson = new Gson();
    	SentData data = gson.fromJson(message, SentData.class);
    	UpdateKey(session, data.getCode());
    	//UPDATE THE PEOPLE YOU ARE SENDING IT TO
    	broadcast(data.getCode(),message, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    	System.out.println("WebSocket connection closed: " + session.getId());
    	String keyToRemove = null;
    	for (Map.Entry<String, Set<Session>> entry : sessionsMap.entrySet()) {
            if (entry.getValue().remove(session)) {
                if (entry.getValue().isEmpty()) {
                    keyToRemove = entry.getKey();
                }
                break;
            }
        }
        
        if (keyToRemove != null) {
            sessionsMap.remove(keyToRemove);
        }
    }

    private void broadcast(String key, String message, Session sender) {
        Set<Session> sessions = sessionsMap.get(key);
        if (sessions != null) {
            for (Session session : sessions) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                
            }
        } else {
            System.out.println("No sessions found for key: " + key);
        }
    }

    public static void UpdateKey(Session session, String newKey) {
        String currentKey = null;
        for (Map.Entry<String, Set<Session>> entry : sessionsMap.entrySet()) {
            if (entry.getValue().remove(session)) {
                currentKey = entry.getKey();
                if (entry.getValue().isEmpty()) {
                    sessionsMap.remove(currentKey);
                }
                break; 
            }
        }

        // Step 2: Add the session under the new key
        sessionsMap.computeIfAbsent(newKey, k -> new CopyOnWriteArraySet<>()).add(session);
    }

}