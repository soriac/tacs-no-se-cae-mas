package org.tacs.grupocuatro.telegram.handlers;

import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession.SessionState;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class RepositoryHandler extends TelegramHandler{

	public RepositoryHandler() throws TelegramTokenNotFoundException {
		super();
	}

	@Override
	public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		long chatId = update.message().chat().id();
		if(isUserLogged(chatId)) {
			
			TelegramUserSession session = sessions.getSessionByChatId(chatId).get();
			
			if(command.equals("/repositorio") && session.state == SessionState.PRINCIPAL) {
				this.monstrarMenu(update);
			} else if (command.equals("/1") && session.state == SessionState.MENU_REPOSITORIO) {
				this.menuRepoId(update);
			} else if(command.equals("/2") && session.state == SessionState.MENU_REPOSITORIO){
				this.menuRepoNombre(update);
			} else if(command.equals("/3") && session.state == SessionState.MENU_REPOSITORIO){
				this.volver(update);
			} else {
				
				if(session.state == SessionState.REPOSITORIO_ID) {
					this.buscarRepoId(update);
				} else if (session.state == SessionState.REPOSITORIO_NOMBRE) {
					this.buscarRepoNombre(update);
				} else {
					this.handleCommandNext(command, update, 1);
				}
				
			}
			
		} else {
			this.handleCommandNext(command, update, 2);
		}

	}

	
	private void monstrarMenu(Update update) {
		
		long chatId = update.message().chat().id();
		SendMessage req;
		
		Keyboard keyboard = new ReplyKeyboardMarkup(
				new String[] {"/1 Buscar por ID"},
				new String[] {"/2 Buscar por nombre"},
				new String[] {"/3 Volver atras"})
				.oneTimeKeyboard(true)
				.resizeKeyboard(true)
				.selective(true);
		
		sessions.modStateSession(chatId, SessionState.MENU_REPOSITORIO);
		req = new SendMessage(update.message().chat().id(), "Por favor seleccione una opcion del menu").replyMarkup(keyboard);
    	bot.getTGBot().execute(req);
		
	}
	
	private void menuRepoId(Update update) {
		
		long chatId = update.message().chat().id();
		
		sessions.modStateSession(chatId, SessionState.REPOSITORIO_ID);
		SendMessage req = new SendMessage(update.message().chat().id(), "Por favor ingrese el id del repositorio");
		bot.getTGBot().execute(req);

	}
	
	private void menuRepoNombre(Update update) {
		
		long chatId = update.message().chat().id();
		
		sessions.modStateSession(chatId, SessionState.REPOSITORIO_NOMBRE);
		SendMessage req = new SendMessage(update.message().chat().id(), "Por favor ingrese el id del repositorio");
		bot.getTGBot().execute(req);
		
	}
	
	private void volver(Update update) {
		
		long chatId = update.message().chat().id();
		
		sessions.modStateSession(chatId, SessionState.MENU_REPOSITORIO);
		SendMessage req = new SendMessage(update.message().chat().id(), "Por favor ingrese el id del repositorio");
		bot.getTGBot().execute(req);
	
	}
	
	private void buscarRepoId(Update update) {
		
		long chatId = update.message().chat().id();
		
		try {
			
			
			String id = update.message().text();
			RepositoryGitHub repo = GitHubConnect.getInstance().findRepositoryById(Long.parseLong(id));
			
			String message = "Repositorio encontrado \n"
					+ "Nombre: " + repo.getName() + "\n"
					+ "\ud83c\udf1f: " + repo.getNumStars() + "\n"
					+ "\ud83c\udf74: " + repo.getNumForks() + "\n"
					+ "Lenguaje:" + repo.getLanguage();
			
			sessions.modStateSession(chatId, SessionState.PRINCIPAL);
			SendMessage req = new SendMessage(chatId, message);
			bot.getTGBot().execute(req);
			
		} catch (NumberFormatException e) {
			sessions.modStateSession(chatId, SessionState.PRINCIPAL);
			SendMessage req = new SendMessage(chatId, "Eso no es un numero, intentelo nuevamente.");
			bot.getTGBot().execute(req);
		} catch (GitHubRequestLimitExceededException e) {
			sessions.modStateSession(chatId, SessionState.MENU_REPOSITORIO);
			SendMessage req = new SendMessage(chatId, "Me quede sin requests. Vuelve a intentarlo proximamente");
			bot.getTGBot().execute(req);
		} catch (GitHubRepositoryNotFoundException e) {
			sessions.modStateSession(chatId, SessionState.MENU_REPOSITORIO);
			SendMessage req = new SendMessage(chatId, "No encontre un repositorio con ese id");
			bot.getTGBot().execute(req);
		}
		
	}
	
	private void buscarRepoNombre(Update update) {
		
		long chatId = update.message().chat().id();
		
		sessions.modStateSession(chatId, SessionState.MENU_REPOSITORIO);
		SendMessage req = new SendMessage(chatId, "Proximamente");
		bot.getTGBot().execute(req);

	}
	
}
