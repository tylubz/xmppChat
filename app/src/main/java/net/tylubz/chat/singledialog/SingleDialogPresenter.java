package net.tylubz.chat.singledialog;

import net.tylubz.chat.singledialog.model.Message;
import net.tylubz.chat.singledialog.services.XmppFileServiceTask;
import net.tylubz.chat.singledialog.services.XmppServiceTask;

import java.io.File;
import java.io.InputStream;

/**
 * Implementation of dialog presenter interface
 *
 * @author Sergei Lebedev
 */

public class SingleDialogPresenter implements SingleDialogContract.Presenter {

    private final SingleDialogContract.View dialogView;

    private final XmppServiceTask xmppServiceTask;

    private XmppFileServiceTask xmppFileServiceTask;

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

    @Override
    public void sendFile(File file, InputStream inputStream, int size) {
        xmppFileServiceTask = new XmppFileServiceTask(xmppServiceTask.getConnection(), inputStream, size);
        xmppFileServiceTask.execute(file);
    }

//    @Override
//    public void sendFile(File file) {
//        xmppFileServiceTask = new XmppFileServiceTask(xmppServiceTask.getConnection());
//        xmppFileServiceTask.execute(file);
////        xmppServiceTask.sendFile(file);
//    }
}
