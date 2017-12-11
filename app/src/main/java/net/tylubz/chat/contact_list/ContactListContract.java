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
        void onCreateButtonClick();
        void onMessageReceive(Message message);
        void onInvitationAccept(JidContact jidContact);
        void onDeleteUserButtonClick();
    }

    interface Presenter extends BasePresenter {
        void addUser(String userName);
        void sendMessage(Message message);
        List<JidContact> getContactList();
        void deleteUser(List<String> userList);
    }
}
