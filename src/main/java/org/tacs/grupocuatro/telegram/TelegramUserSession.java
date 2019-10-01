package org.tacs.grupocuatro.telegram;

import org.tacs.grupocuatro.entity.User;

public class TelegramUserSession {
	
	public User user;
	public long chatId;
	public SessionState state;
	
	public enum SessionState {
		PRINCIPAL,
		MENU_REPOSITORIO,
		REPOSITORIO_NOMBRE,
		REPOSITORIO_ID
	};
	
	public TelegramUserSession(User user, long chatId) {
		this.user = user;
		this.chatId = chatId;
		this.state = SessionState.PRINCIPAL;
	}
	
}
