package net.tylubz.chat.activities;

import net.tylubz.chat.BasePresenter;
import net.tylubz.chat.BaseView;
import net.tylubz.chat.shared.model.Message;

/**
 * Specifies the contract between the view and the presenter.
 *
 * @author Sergei Lebedev
 */

public class SingleDialogContract {

    interface View extends BaseView<Presenter> {
        void onButtonClick();
        void onMessageReceive(Message message);

    }

    interface Presenter extends BasePresenter {
        void sendMessage(String jid, Message message);
    }
}
