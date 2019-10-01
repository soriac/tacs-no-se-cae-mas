package org.tacs.grupocuatro.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.tacs.grupocuatro.entity.User;

public class TelegramSessions {
	
	private static TelegramSessions instance;
	private List<UserSession> users = new ArrayList<UserSession>();
	
	private TelegramSessions() {
		super();	
	}
	
	public static TelegramSessions getInstance() {
        if (instance == null) {
            instance = new TelegramSessions();
        }
        return instance;
    }
	
	
	public UserSession createSession(User user, long chatId) {
		UserSession userSession = new UserSession(user,chatId);
		this.users.add(userSession);
		return userSession;
	}
	
	public void removeSession(UserSession user) {
		this.users.remove(user);
	}
	
	public Optional<UserSession> getSessionByChatId(long chatId) {
		return users.stream().filter(u -> u.chatId == chatId).findFirst();
	}

	public class UserSession{
		
		public User user;
		public long chatId;
		
		public UserSession(User user, long chatId) {
			this.user = user;
			this.chatId = chatId;
		}
		
	}
	
}
