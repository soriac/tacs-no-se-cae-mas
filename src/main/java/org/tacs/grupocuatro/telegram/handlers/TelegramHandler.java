package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.TelegramGHBot;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;

public abstract class TelegramHandler {
	
	public TelegramHandler next;
	public TelegramGHBot bot;
	
	public TelegramHandler() throws TelegramTokenNotFoundException {
		this.bot = TelegramGHBot.getInstance();
	}
	
	public TelegramHandler linkNext(TelegramHandler next) {
		this.next = next;
		return next;
	}
	
	public abstract void handleCommand(String command, Update update) throws TelegramHandlerNotExistsException;
	
	protected void handleCommandNext(String command, Update update) throws TelegramHandlerNotExistsException {
		
		if (next == null) {
			throw new TelegramHandlerNotExistsException();
		} else {
			next.handleCommand(command, update);
		}
	
	}
	
}
