package org.tacs.grupocuatro.telegram;


import org.tacs.grupocuatro.telegram.exceptions.*;
import org.tacs.grupocuatro.telegram.handlers.*;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;

public class TelegramGHBot {

    private static TelegramGHBot instance = null;
    
	private TelegramBot bot;
    private String token;
    private String webhook;
    private TelegramHandler handler;
    
    private TelegramGHBot() throws TelegramTokenNotFoundException{

        String token = System.getenv("GITHUB_TACS_TELEGRAM");
        
        if (token == null) {
            throw new TelegramTokenNotFoundException();
        } else {
        	
        	this.bot = new TelegramBot(token);

        }
        
        this.token = token;

    }

    public TelegramBot getTGBot() {
    	return this.bot;
    }
    
    public String getToken() {
    	return this.token;
    }
    
    public static TelegramGHBot getInstance() throws TelegramTokenNotFoundException {
        if (instance == null) {
            instance = new TelegramGHBot();
        }
        return instance;
    }
	
    public void start(String webhook) throws TelegramCannotSetWebhookException, TelegramTokenNotFoundException {
    	this.webhook = webhook;
    	this.setWebHook();
    	this.setHandlers();
    }
	
    
    public void setWebHook() throws TelegramCannotSetWebhookException {
    	    	
    	SetWebhook request = new SetWebhook()
    			.url(this.webhook);
    	
    	BaseResponse resp = this.bot.execute(request);
    	
    	if(!resp.isOk()) {
    		throw new TelegramCannotSetWebhookException(resp.description());
    	}
    	
    }
    
    public void setHandlers() throws TelegramTokenNotFoundException {

        TelegramHandler telegramHandler = new SessionHandler();
        telegramHandler
                .linkNext(new RepositoryHandler())
                .linkNext(new ContributorHandler())
                .linkNext(new PingHandler())
                .linkNext(new DefaultHandler());

    	this.handler = telegramHandler;
    	
    }
    
    public void handleUpdate(Update update) {
    	
    	Message message = update.message();
    	String command = message.text().split(" ")[0];

    	try {
			this.handler.handleCommand(command, update, 2);
		} catch (TelegramHandlerNotExistsException e) {
			System.out.println("No encontre handler para el comando " + command);
		}
    	
    }
    
}
