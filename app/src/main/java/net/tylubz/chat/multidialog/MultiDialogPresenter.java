package net.tylubz.chat.multidialog;

import net.tylubz.chat.multidialog.model.Message;
import net.tylubz.chat.multidialog.services.XmppServiceTask;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class MultiDialogPresenter implements MultiDialogContract.Presenter {

    private final MultiDialogContract.View dialogView;

    private final XmppServiceTask xmppServiceTask;

    public MultiDialogPresenter(final MultiDialogContract.View dialogView) {

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
}
