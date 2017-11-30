package net.tylubz.chat.dialog;

import net.tylubz.chat.dialog.model.Message;

/**
 * The message listener interface
 *
 * @author Sergei Lebedev
 */

@FunctionalInterface
public interface MessageListener {
    void push(Message message);
}
