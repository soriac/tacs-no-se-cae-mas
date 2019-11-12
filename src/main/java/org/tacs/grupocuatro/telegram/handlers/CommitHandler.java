package org.tacs.grupocuatro.telegram.handlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.telegram.entity.TelegramUserSession;
import org.tacs.grupocuatro.telegram.exceptions.TelegramHandlerNotExistsException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

public class CommitHandler extends TelegramHandler {
    public CommitHandler() throws TelegramTokenNotFoundException {
    }

    @Override
    public void handleCommand(String command, Update update, int justification) throws TelegramHandlerNotExistsException {
        var chatId = update.message().chat().id();
        if (isUserLogged(chatId)) {
            var session = sessions.getSessionByChatId(chatId).get();

            if (command.equals("/commits") && session.state == TelegramUserSession.SessionState.PRINCIPAL) {
                sessions.modStateSession(chatId, TelegramUserSession.SessionState.COMMITS_REPO_NOMBRE);
                var mensaje = "Ingrese autor y nombre del repositorio, separados por una /. Por ejemplo: soriac/tacs-no-se-cae-mas";
                var req = new SendMessage(update.message().chat().id(), mensaje);
                bot.getTGBot().execute(req);
            } else if (session.state == TelegramUserSession.SessionState.COMMITS_REPO_NOMBRE) {
                sessions.modStateSession(chatId, TelegramUserSession.SessionState.PRINCIPAL);

                var rawRepo = update.message().text();
                var isValid = rawRepo.matches("[a-zA-Z0-9\\-]+/[a-zA-Z0-9\\-]+");

                if (!isValid) {
                    var mensaje = "Nombre del repo no valido. Volviendo al inicio.";
                    var req = new SendMessage(update.message().chat().id(), mensaje);
                    bot.getTGBot().execute(req);
                } else {
                    var split = rawRepo.split("/");
                    var author = split[0];
                    var name = split[1];

                    var connect = GitHubConnect.getInstance();

                    try {
                        var commits = connect.getRepositoryWithCommits(author, name);
                        commits.forEach(commit -> {
                            var mensaje = commit.getAuthorName() +
                                    "\n" + commit.getDate() +
                                    "\n" + commit.getMessage();

                            var req = new SendMessage(update.message().chat().id(), mensaje);
                            bot.getTGBot().execute(req);
                        });
                    } catch (Exception e) {
                        var mensaje = "Error. " + e.getMessage();
                        var req = new SendMessage(update.message().chat().id(), mensaje);
                        bot.getTGBot().execute(req);
                    }
                }
            } else {
                this.handleCommandNext(command, update, 2);
            }
        } else {
            this.handleCommandNext(command, update, 2);
        }

    }
}
