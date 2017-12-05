package net.tylubz.chat.dialog;

import net.tylubz.chat.dialog.model.Message;
import net.tylubz.chat.dialog.services.XmppServiceTask;

import java.io.File;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class DialogPresenter implements DialogContract.Presenter {

    private final DialogContract.View dialogView;

    private final XmppServiceTask xmppServiceTask;

    public DialogPresenter(final DialogContract.View dialogView) {

        xmppServiceTask = new XmppServiceTask(msg -> dialogView.onMessageReceive(msg));
        xmppServiceTask.execute();

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
    public void sendFile(File file) {
        xmppServiceTask.sendFile(file);
    }
}
