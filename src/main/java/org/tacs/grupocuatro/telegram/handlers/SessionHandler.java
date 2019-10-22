package org.tacs.grupocuatro.telegram.handlers;

import java.util.Optional;

import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class SessionHandler extends TelegramHandler {
	
	public SessionHandler() throws TelegramTokenNotFoundException {
		super();
	}

	@Override
	public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		switch(command) {
			
			case "/login":
				this.login(update);
				break;
			
			case "/logout":
				this.logout(update);
				break;
				
			default:
				this.handleCommandNext(command, update, 2);
		
		}
	
	}
	
	
	private void login(Update update) {
		
		String[] message = update.message().text().split(" ");
		long chatId = update.message().chat().id();
		
		if(message.length != 3) {
			
			SendMessage req = new SendMessage(chatId, "/login email password");
	    	bot.getTGBot().execute(req);
	    	
		} else {
			
			Optional<TelegramUserSession> userSession = sessions.getSessionByChatId(chatId);
			
			if(userSession.isPresent()) {
				SendMessage req = new SendMessage(chatId, "Usted ya se encuentra con una sesion iniciada");
		    	bot.getTGBot().execute(req);
			} else {
				
				String email = message[1];
				String pass = message[2];
				
				Optional<User> user = UserDAO.getInstance().findUser(email, pass);
				
				if(user.isEmpty()) {
					SendMessage req = new SendMessage(chatId, "Credenciales incorrectas");
			    	bot.getTGBot().execute(req);
				} else {
					
					sessions.createSession(user.get(), update.message().chat().id());
					SendMessage req = new SendMessage(chatId, "Te has logueado correctamente");
			    	bot.getTGBot().execute(req);

				}
				
			}
			
		}
		
	}
	
	private void logout(Update update) {
		
		String[] message = update.message().text().split(" ");
		long chatId = update.message().chat().id();
		
		if(message.length != 1) {
			
			SendMessage req = new SendMessage(chatId, "/logout");
	    	bot.getTGBot().execute(req);
	    	
		} else {
			
			Optional<TelegramUserSession> userSession = sessions.getSessionByChatId(chatId);
			
			if(userSession.isEmpty()) {
				
				SendMessage req = new SendMessage(chatId, "Usted no ha iniciado sesion");
		    	bot.getTGBot().execute(req);
		    	
			} else {
				
				sessions.removeSession(chatId);
				SendMessage req = new SendMessage(chatId, "Te has deslogueado");
		    	bot.getTGBot().execute(req);
								
			}
			
		}
		
		
	}
	
	
}


