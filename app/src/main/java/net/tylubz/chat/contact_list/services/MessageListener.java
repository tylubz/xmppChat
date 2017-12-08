package net.tylubz.chat.contact_list.services;


import net.tylubz.chat.contact_list.model.Message;

/**
 * The message listener interface
 *
 * @author Sergei Lebedev
 */

@FunctionalInterface
public interface MessageListener {
    void push(Message message);
}
