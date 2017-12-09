package net.tylubz.chat.shared.model;

/**
 * Created by slebedev on 05.12.2017.
 */

public class JidContact {

    private String jid;

    private String presence;

    public JidContact(String jid, String presence) {
        this.jid = jid;
        this.presence = presence;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
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

        JidContact jidContact = (JidContact) o;

        if (!jid.equals(jidContact.jid)) return false;
        return presence != null ? presence.equals(jidContact.presence) : jidContact.presence == null;
    }

    @Override
    public int hashCode() {
        int result = jid.hashCode();
        result = 31 * result + (presence != null ? presence.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JidContact{" +
                "jid='" + jid + '\'' +
                ", presence='" + presence + '\'' +
                '}';
    }
}
