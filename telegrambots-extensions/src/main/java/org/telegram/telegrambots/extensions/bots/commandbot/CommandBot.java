package org.telegram.telegrambots.extensions.bots.commandbot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This interface represents common functions for command bots
 *
 * @author Andrey Korsakov (loolzaaa)
 */
public interface CommandBot {
    /**
     * Process all updates, that are not commands.
     *
     * @param update the update
     * @warning Commands that have valid syntax but are not registered on this bot,
     * won't be forwarded to this method <b>if a default action is present</b>.
     */
    void processNonCommandUpdate(Update update);

    /**
     * This method is called when user sends a not registered command. By default it will just call processNonCommandUpdate(),
     * override it in your implementation if you want your bot to do other things, such as sending an error message
     *
     * @param update Received update from Telegram
     */
    default void processInvalidCommandUpdate(Update update) {
        processNonCommandUpdate(update);
    }

    /**
     * Override this function in your bot implementation to filter messages with commands
     * <p>
     * For example, if you want to prevent commands execution incoming from group chat:
     * #
     * # return !message.getChat().isGroupChat();
     * #
     *
     * @param message Received message
     * @return true if the message must be ignored by the command bot and treated as a non command message,
     * false otherwise
     * @note Default implementation doesn't filter anything
     */
    default boolean filter(Message message) {
        return false;
    }

    BotApiMethod<?> onWebhookUpdateReceived(Update update);
    void onUpdateReceived(Update update);
    boolean register(IBotCommand botCommand);
    Map<IBotCommand, Boolean> registerAll(IBotCommand... botCommands);
    boolean deregister(IBotCommand botCommand);
    Map<IBotCommand, Boolean> deregisterAll(IBotCommand... botCommands);
    Collection<IBotCommand> getRegisteredCommands();
    void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer);
    IBotCommand getRegisteredCommand(String commandIdentifier);
}
