package net.tylubz.chat.multidialog;

import net.tylubz.chat.multidialog.model.Contact;

import java.util.List;

/**
 * @author Sergei Lebedev
 */

public interface ResultListener {
    void pushResult(List<Contact> result);
}
