package net.tylubz.chat.singledialog.services;

import android.os.AsyncTask;
import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smackx.bytestreams.socks5.Socks5BytestreamManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.filetransfer.Socks5TransferNegotiator;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.DomainFullJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sergei Lebedev
 */

public class XmppFileServiceTask extends AsyncTask<File, Void, Void> {

    private AbstractXMPPConnection connection;

    private InputStream inputStream;

    int size;

    public XmppFileServiceTask(AbstractXMPPConnection connection) {
        this.connection = connection;
    }

    public XmppFileServiceTask(AbstractXMPPConnection connection, InputStream inputStream, int size) {
        this.connection = connection;
        this.inputStream = inputStream;
        this.size = size;
    }

    public void sendFile(File file) {
        if (!file.exists()) {
            Log.e("EROR", "file doesn't exist");
        }
        final String jid = "golub578@jabber.ru";
//        EntityFullJid entityFullJid = null;
//        try {
////            EntityBareJid bareJid = JidCreate.entityBareFrom(jid);
//            BareJid bareJid = JidCreate.bareFrom(jid);
//            entityFullJid = JidCreate.fullFrom(bareJid.asEntityBareJidIfPossible(), bareJid.getResourceOrEmpty());
////            DomainFullJid domainFullJid = JidCreate.domainFullFrom(jid);
////            entityFullJid = JidCreate.fullFrom(domainFullJid.asEntityBareJidIfPossible(), domainFullJid.getResourceOrThrow());
//        } catch (XmppStringprepException e) {
//            e.printStackTrace();
//        }

        // Create the file transfer manager
        FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
        EntityFullJid entityFullJid = null;
        // Create the outgoing file transfer
        try {
            entityFullJid = JidCreate.entityFullFrom(JidCreate.entityBareFrom(jid), Resourcepart.from("jabber.ru"));
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(entityFullJid);
//
//        // Create the file transfer manager
//        FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
//        // Create the outgoing file transfer
//        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(entityFullJid);

        // Send the file


        try {
            transfer.sendStream(this.inputStream, "test.txt", this.size, "description");
//            transfer.sendFile(file, "You won't believe this!");
//            while (!transfer.isDone()) {
//                long res = transfer.getBytesSent();
//            }
//            transfer.sendStream(new InputStream() {
//                @Override
//                public int read() throws IOException {
//                    return 1;
//                }
//            }, "file", 1, "" );
//            transfer.isDone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(File... files) {
        sendFile(files[0]);
        return null;
    }
}
