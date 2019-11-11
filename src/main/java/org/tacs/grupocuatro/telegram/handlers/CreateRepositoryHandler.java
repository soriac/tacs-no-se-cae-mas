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
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class CreateRepositoryHandler extends TelegramHandler {
    public CreateRepositoryHandler() throws TelegramTokenNotFoundException {
        super();
    }

    @Override
    public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
        var chatId = update.message().chat().id();
        if (isUserLogged(chatId)) {
            var session = sessions.getSessionByChatId(chatId).get();

            if (command.equals("/createRepo") && session.state == TelegramUserSession.SessionState.PRINCIPAL) {
                sessions.modStateSession(chatId, SessionState.CREATE_REPO);
                var mensaje = "Ingrese nombre del repositorio";
                var req = new SendMessage(update.message().chat().id(), mensaje);
                bot.getTGBot().execute(req);
            } else if (session.state == TelegramUserSession.SessionState.CREATE_REPO) {
                sessions.modStateSession(chatId, TelegramUserSession.SessionState.PRINCIPAL);

                var rawRepo = update.message().text();
                var connect = GitHubConnect.getInstance();

                try {
                    int status = GitHubConnect.getInstance().createRepo(rawRepo);
                    sessions.modStateSession(chatId, SessionState.PRINCIPAL);
                    SendMessage req;
                    if (status >= 300)
                        req = new SendMessage(chatId, "El repositorio no fue creado");
                    else
                        req = new SendMessage(chatId, "Repositorio creado correctamente");
                    bot.getTGBot().execute(req);
                } catch (Exception e) {
                    var mensaje = "Error. " + e.getMessage();
                    var req = new SendMessage(update.message().chat().id(), mensaje);
                    bot.getTGBot().execute(req);
                }
            } else
                this.handleCommandNext(command, update, 1);
        } else {
            this.handleCommandNext(command, update, 2);
        }
    }
}