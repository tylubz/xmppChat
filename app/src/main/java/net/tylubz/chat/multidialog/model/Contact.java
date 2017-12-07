package net.tylubz.chat.multidialog.model;

/**
 * Created by slebedev on 05.12.2017.
 */

public class Contact {

    private String userName;

    private String presence;

    public Contact(String userName, String presence) {
        this.userName = userName;
        this.presence = presence;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }
}
