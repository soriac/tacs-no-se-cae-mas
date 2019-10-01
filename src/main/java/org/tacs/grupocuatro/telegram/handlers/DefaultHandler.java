package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class DefaultHandler extends TelegramHandler {

	public DefaultHandler() throws TelegramTokenNotFoundException {
		super();
	}

	@Override
	public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		long chatId = update.message().chat().id();
		
		String msg = "";
		
		if(justification == 1) {
			msg = "Perdona. No te entiendo...";
		} else if(justification == 2) {
			msg = "Debes iniciar sesion para poder hacer eso";
		}
		
		SendMessage req = new SendMessage(chatId, msg);
    	bot.getTGBot().execute(req);
    	
	}

	
	
}
