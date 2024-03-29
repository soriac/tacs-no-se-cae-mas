package org.tacs.grupocuatro.telegram.entity;

import org.tacs.grupocuatro.entity.User;

public class TelegramUserSession {
	
	public User user;
	public long chatId;
	public SessionState state;
	
	public enum SessionState {
		PRINCIPAL,
		MENU_REPOSITORIO,
		REPOSITORIO_NOMBRE,
		REPOSITORIO_ID,
		COMMITS_REPO_NOMBRE,
		REPOSITORIO_ID_CONTRIBUTORS,
		CREATE_REPO
	};
	
	public TelegramUserSession(User user, long chatId) {
		this.user = user;
		this.chatId = chatId;
		this.state = SessionState.PRINCIPAL;
	}
	
}
