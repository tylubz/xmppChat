package net.tylubz.chat.singledialog;

import net.tylubz.chat.BasePresenter;
import net.tylubz.chat.BaseView;
import net.tylubz.chat.singledialog.model.Message;

import java.io.File;
import java.io.InputStream;

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
        void sendFile(File file, InputStream inputStream, int size);
    }
}
