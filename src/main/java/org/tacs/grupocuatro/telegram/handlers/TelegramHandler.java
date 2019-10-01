package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.TelegramGHBot;
import org.tacs.grupocuatro.telegram.TelegramSessions;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;

public abstract class TelegramHandler {
	
	public TelegramHandler next;
	public TelegramGHBot bot;
	public TelegramSessions sessions;
	
	public TelegramHandler() throws TelegramTokenNotFoundException {
		this.bot = TelegramGHBot.getInstance();
		this.sessions = TelegramSessions.getInstance();
	}
	
	public TelegramHandler linkNext(TelegramHandler next) {
		this.next = next;
		return next;
	}
	
	public abstract void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException;
	
	protected void handleCommandNext(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		if (next == null) {
			throw new TelegramHandlerNotExistsException();
		} else {
			next.handleCommand(command, update, justification);
		}
	
	}
	
	public boolean isUserLogged(long chatId) {
		return sessions.getSessionByChatId(chatId).isPresent();
	}
	
}
