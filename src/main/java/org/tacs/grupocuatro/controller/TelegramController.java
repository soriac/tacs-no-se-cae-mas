package org.tacs.grupocuatro.controller;

import org.tacs.grupocuatro.telegram.TelegramGHBot;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

import io.javalin.http.Context;

public class TelegramController {
	
	public static void update(Context ctx) throws TelegramTokenNotFoundException {
		
		TelegramGHBot bot = TelegramGHBot.getInstance();
		
		Update update = BotUtils.parseUpdate(ctx.body());
		
		bot.handleUpdate(update);
		//Message mess = update.message();		
		
		
		/*SendResponse response = bot.getTGBot().execute(new SendMessage(mess.chat().id(),"Pong!"));*/
		
	}
	
}
