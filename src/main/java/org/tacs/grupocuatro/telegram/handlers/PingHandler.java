package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class PingHandler extends TelegramHandler{

	public PingHandler() throws TelegramTokenNotFoundException {
		super();
	}
	
	@Override
	public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		if(command.equals("/ping")) {
			this.ping(update);
		} else {
			this.handleCommandNext(command, update, 1);
		}

	}
	
	private void ping(Update update) {
		
		long chatId = update.message().chat().id();
		SendMessage req = new SendMessage(chatId, "Pong!");
    	bot.getTGBot().execute(req);
		
	}
	
}
