package net.tylubz.chat.multidialog;

import net.tylubz.chat.multidialog.model.Message;

/**
 * The message listener interface
 *
 * @author Sergei Lebedev
 */

@FunctionalInterface
public interface MessageListener {
    void push(Message message);
}
