package org.tacs.grupocuatro.telegram.handlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.ContributorGitHub;
import org.tacs.grupocuatro.github.entity.ContributorsGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

public class ContributorHandler extends TelegramHandler{

	public ContributorHandler() throws TelegramTokenNotFoundException {
		super();
	}
	
	@Override
	public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
		
		long chatId = update.message().chat().id();
		if(isUserLogged(chatId)) {

			TelegramUserSession session = sessions.getSessionByChatId(chatId).get();

			if(command.equals("/contributors") && session.state == TelegramUserSession.SessionState.PRINCIPAL) {
				this.menuRepoId(update);
			} else {

				if(session.state == TelegramUserSession.SessionState.REPOSITORIO_ID_CONTRIBUTORS) {
					this.buscarRepoIdContributors(update);
				} else {
					this.handleCommandNext(command, update, 1);
				}
			}
		} else {
			this.handleCommandNext(command, update, 2);
		}
	}

	private void menuRepoId(Update update) {

		long chatId = update.message().chat().id();

		sessions.modStateSession(chatId, TelegramUserSession.SessionState.REPOSITORIO_ID_CONTRIBUTORS);
		SendMessage req = new SendMessage(update.message().chat().id(), "Por favor ingrese el id del repositorio");
		bot.getTGBot().execute(req);

	}

	private void buscarRepoIdContributors(Update update) {

		long chatId = update.message().chat().id();

		try {
			String id = update.message().text();
			ContributorsGitHub contributors = GitHubConnect.getInstance().getRepositoryContributorsById(Long.parseLong(id));

			StringBuilder message = new StringBuilder("Repositorio encontrado. \n" + "Contributors: \n");
			for(ContributorGitHub con : contributors.getContributors()) {
				message.append("\n")
					.append("User: ").append(con.getLogin()).append("\n")
					.append("Contributions: ").append(con.getContributions()).append("\n");
			}

			sessions.modStateSession(chatId, TelegramUserSession.SessionState.PRINCIPAL);
			SendMessage req = new SendMessage(chatId, message.toString());
			bot.getTGBot().execute(req);

		} catch (NumberFormatException e) {
			sessions.modStateSession(chatId, TelegramUserSession.SessionState.PRINCIPAL);
			SendMessage req = new SendMessage(chatId, "Eso no es un numero, intentelo nuevamente.");
			bot.getTGBot().execute(req);
		} catch (GitHubRequestLimitExceededException e) {
			sessions.modStateSession(chatId, TelegramUserSession.SessionState.MENU_REPOSITORIO);
			SendMessage req = new SendMessage(chatId, "Me quede sin requests. Vuelve a intentarlo proximamente");
			bot.getTGBot().execute(req);
		} catch (GitHubRepositoryNotFoundException e) {
			sessions.modStateSession(chatId, TelegramUserSession.SessionState.MENU_REPOSITORIO);
			SendMessage req = new SendMessage(chatId, "No encontre un repositorio con ese id");
			bot.getTGBot().execute(req);
		}

	}
	
}
