package net.tylubz.chat.multidialog;

import net.tylubz.chat.shared.model.JidContact;

import java.util.List;

/**
 * @author Sergei Lebedev
 */

public interface ResultListener {
    void pushResult(List<JidContact> result);
}
