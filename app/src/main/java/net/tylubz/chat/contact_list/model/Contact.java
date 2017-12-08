package net.tylubz.chat.contact_list.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!userName.equals(contact.userName)) return false;
        return presence != null ? presence.equals(contact.presence) : contact.presence == null;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + (presence != null ? presence.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "userName='" + userName + '\'' +
                ", presence='" + presence + '\'' +
                '}';
    }
}
