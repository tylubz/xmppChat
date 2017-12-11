package net.tylubz.chat.contact_list.services;

/**
 * The message listener interface
 *
 * @author Sergei Lebedev
 */

@FunctionalInterface
public interface InvitationListener {
    void invite(String userName);
}
