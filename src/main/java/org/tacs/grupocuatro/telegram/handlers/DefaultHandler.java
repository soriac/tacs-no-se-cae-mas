package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;

public class DefaultHandler extends TelegramHandler {

	public DefaultHandler() throws TelegramTokenNotFoundException {
		super();
	}

	@Override
	public void handleCommand(String command, Update update) throws TelegramHandlerNotExistsException {
		
		this.bot.replyMessage("Perdona. No te entiendo...", update);
		System.out.println("Comando " + command + " atendido por DefaultHandler");
		
	}

	
	
}
