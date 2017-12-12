package net.tylubz.chat.multidialog;

import net.tylubz.chat.shared.model.JidContact;
import net.tylubz.chat.shared.model.Message;
import net.tylubz.chat.multidialog.services.XmppServiceTask;

import java.util.List;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class MultiDialogPresenter implements MultiDialogContract.Presenter {

    private final MultiDialogContract.View dialogView;

    private final XmppServiceTask xmppServiceTask;

    public MultiDialogPresenter(final MultiDialogContract.View dialogView) {

        xmppServiceTask = new XmppServiceTask(msg -> dialogView.onMessageReceive(msg),
                result -> dialogView.onContactListReceive(result));
        xmppServiceTask.establishConnection();

        this.dialogView = dialogView;
        this.dialogView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        xmppServiceTask.closeConnection();
    }

    @Override
    public void sendMessage(Message message) {
        xmppServiceTask.sendMessage(message.getMessage());
    }

    @Override
    public void createGroupChat(List<JidContact> participants) {
        xmppServiceTask.createGroupChat(participants);
    }
}
