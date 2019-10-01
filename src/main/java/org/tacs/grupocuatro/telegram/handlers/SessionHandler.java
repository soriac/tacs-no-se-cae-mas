package org.tacs.grupocuatro.telegram.handlers;

import java.util.Optional;

import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.telegram.TelegramSessions;
import org.tacs.grupocuatro.telegram.TelegramSessions.UserSession;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;

public class SessionHandler extends TelegramHandler {
	
	public SessionHandler() throws TelegramTokenNotFoundException {
		super();
	}

	@Override
	public void handleCommand(String command, Update update) throws TelegramHandlerNotExistsException {
		
		switch(command) {
			
			case "/login":
				this.login(update);
				break;
			
			case "/logout":
				this.logout(update);
				break;
				
			default:
				this.handleCommandNext(command, update);
		
		}
	
	}
	
	
	public void login(Update update) {
		
		String[] message = update.message().text().split(" ");
		long chatId = update.message().chat().id();
		TelegramSessions sessions = TelegramSessions.getInstance();
		if(message.length != 3) {
			bot.replyMessage("/login email password", update);
		} else {
			
			Optional<UserSession> userSession = sessions.getSessionByChatId(chatId);
			
			if(userSession.isPresent()) {
				bot.replyMessage("Usted ya se encuentra con una sesion iniciada", update);
			} else {
				
				String email = message[1];
				String pass = message[2];
				
				Optional<User> user = UserDAO.getInstance().findUser(email, pass);
				
				if(user.isEmpty()) {
					bot.replyMessage("Credenciales incorrectas", update);
				} else {
					
					sessions.createSession(user.get(), update.message().chat().id());
					bot.replyMessage("Te has logueado correctamente", update);

				}
				
			}
			
		}
		
	}
	
	public void logout(Update update) {
		
		String[] message = update.message().text().split(" ");
		long chatId = update.message().chat().id();
		TelegramSessions sessions = TelegramSessions.getInstance();
		
		if(message.length != 1) {
			bot.replyMessage("/logout", update);
		} else {
			
			Optional<UserSession> userSession = sessions.getSessionByChatId(chatId);
			
			if(userSession.isEmpty()) {
				bot.replyMessage("Usted no ha iniciado sesion", update);
			} else {
				
				sessions.removeSession(userSession.get());
				bot.replyMessage("Te has deslogueado", update);
				
			}
			
		}
		
		
	}
	
	
}


