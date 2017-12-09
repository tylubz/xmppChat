package net.tylubz.chat.multidialog;

import net.tylubz.chat.BasePresenter;
import net.tylubz.chat.BaseView;
import net.tylubz.chat.shared.model.JidContact;
import net.tylubz.chat.shared.model.Message;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 *
 * @author Sergei Lebedev
 */

public class MultiDialogContract {

    interface View extends BaseView<Presenter> {
        void onButtonClick();
        void onMessageReceive(Message message);
        void onContactListReceive(List<JidContact> jidContactList);

    }

    interface Presenter extends BasePresenter {
        void sendMessage(Message message);
        void createGroupChat(List<JidContact> participants);
    }
}
