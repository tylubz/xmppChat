package net.tylubz.chat.contact_list;

import net.tylubz.chat.BasePresenter;
import net.tylubz.chat.BaseView;
import net.tylubz.chat.contact_list.model.Message;
import net.tylubz.chat.shared.model.JidContact;

import java.io.File;
import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 *
 * @author Sergei Lebedev
 */

public class ContactListContract {

    interface View extends BaseView<Presenter> {
        void onButtonClick();
        void onMessageReceive(Message message);

    }

    interface Presenter extends BasePresenter {
        void sendMessage(Message message);
        void sendFile(File file);
        List<JidContact> getContactList();
    }
}
