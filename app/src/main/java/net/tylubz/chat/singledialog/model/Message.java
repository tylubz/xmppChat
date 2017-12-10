package net.tylubz.chat.singledialog.model;

/**
 * @author Sergei Lebedev
 */

public class Message {

    private String message;

    private String userName;

    private String time;

    public Message(String message) {
        this.message = message;

    }

    public Message(String message, String userName, String time) {
        this.message = message;
        this.userName = userName;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!message.equals(message1.message)) return false;
        if (!userName.equals(message1.userName)) return false;
        return time != null ? time.equals(message1.time) : message1.time == null;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
