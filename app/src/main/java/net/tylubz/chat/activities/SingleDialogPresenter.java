package net.tylubz.chat.activities;

import net.tylubz.chat.singledialog.model.Message;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class SingleDialogPresenter implements SingleDialogContract.Presenter {

    private final SingleDialogContract.View dialogView;

    private final XmppServiceTask xmppServiceTask;

    public SingleDialogPresenter(final SingleDialogContract.View dialogView) {

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
    public void sendMessage(String jid, Message message) {
        xmppServiceTask.sendMessage(jid, message.getMessage());
    }
}
