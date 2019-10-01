package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;

public class PingHandler extends TelegramHandler{

	public PingHandler() throws TelegramTokenNotFoundException {
		super();
	}
	
	@Override
	public void handleCommand(String command, Update update) throws TelegramHandlerNotExistsException {
		
		switch(command) {
			
			case "/ping":
				this.ping(update);
				break;
				
			default:
				this.handleCommandNext(command, update);
		
		}
	
	}
	
	private void ping(Update update) {
		this.bot.replyMessage("Pong!", update);
	}
	
}
