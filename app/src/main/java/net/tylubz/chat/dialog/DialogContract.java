package net.tylubz.chat.dialog;

import net.tylubz.chat.BasePresenter;
import net.tylubz.chat.BaseView;
import net.tylubz.chat.dialog.model.Message;

/**
 * Specifies the contract between the view and the presenter.
 *
 * @author Sergei Lebedev
 */

public class DialogContract {

    interface View extends BaseView<Presenter> {
        void onButtonClick();
        void onMessageReceive(Message message);

    }

    interface Presenter extends BasePresenter {
        void sendMessage(Message message);
    }
}
