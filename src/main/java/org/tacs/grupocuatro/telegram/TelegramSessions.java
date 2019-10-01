package org.tacs.grupocuatro.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.telegram.TelegramUserSession.SessionState;

public class TelegramSessions {
	
	private static TelegramSessions instance;
	private List<TelegramUserSession> users = new ArrayList<TelegramUserSession>();
	
	private TelegramSessions() {
		super();	
	}
	
	public static TelegramSessions getInstance() {
        if (instance == null) {
            instance = new TelegramSessions();
        }
        return instance;
    }
	
	
	public TelegramUserSession createSession(User user, long chatId) {
		TelegramUserSession userSession = new TelegramUserSession(user,chatId);
		this.users.add(userSession);
		return userSession;
	}
	
	public void removeSession(TelegramUserSession user) {
		this.users.remove(user);
	}
	
	public Optional<TelegramUserSession> getSessionByChatId(long chatId) {
		return users.stream().filter(u -> u.chatId == chatId).findFirst();
	}
	
	public void modStateSession(long chatId, SessionState state) {
		TelegramUserSession session = users.stream().filter(u -> u.chatId == chatId).findFirst().get();
		session.state = state;
	}
	
}
