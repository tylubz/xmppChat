package net.tylubz.chat.singledialog;

import net.tylubz.chat.singledialog.model.Message;

/**
 * The message listener interface
 *
 * @author Sergei Lebedev
 */

@FunctionalInterface
public interface MessageListener {
    void push(Message message);
}
