package org.tacs.grupocuatro.telegram;

import com.google.gson.Gson;
import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession.SessionState;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelegramSessions {
	
	private static TelegramSessions instance;
	private static Gson gson = new Gson();
	private List<TelegramUserSession> users = new ArrayList<TelegramUserSession>();
	private Jedis redisDB;
	
	private TelegramSessions() {
		super();	
	}
	
	public static TelegramSessions getInstance() {
        if (instance == null) {
            instance = new TelegramSessions();
            instance.connect();
        }
        return instance;
    }
	
	public void connect() {
		String url = System.getenv("GITHUB_TACS_REDIS_URL");
		int port = Integer.parseInt(System.getenv("GITHUB_TACS_REDIS_PORT"));

		Jedis redisDB = new Jedis(url, port);
		redisDB.connect();
		redisDB.flushAll();
		this.redisDB = redisDB;
		
	}
	
	public TelegramUserSession createSession(User user, long chatId) {
		
		TelegramUserSession userSession = new TelegramUserSession(user,chatId);
		redisDB.set(Long.toString(chatId),gson.toJson(userSession));
		return userSession;
	
	}
	
	public void removeSession(long chatId) {
		redisDB.del(Long.toString(chatId));
	}
	
	public Optional<TelegramUserSession> getSessionByChatId(long chatId) {
		
		TelegramUserSession session = gson.fromJson(redisDB.get(Long.toString(chatId)), TelegramUserSession.class);
		return Optional.ofNullable(session);
	
	}
	
	public void modStateSession(long chatId, SessionState state) {
		
		TelegramUserSession session = gson.fromJson(redisDB.get(Long.toString(chatId)), TelegramUserSession.class);
		session.state = state;
		redisDB.set(Long.toString(chatId),gson.toJson(session));

	}
	
}
