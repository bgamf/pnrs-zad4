package branislav.gamf.chatapplication;

public class MessageItem {
    public String messageText;
    public boolean backgroundColor;

    public MessageItem(String messageText, boolean backgroundColor) {
        this.messageText = messageText;
        this.backgroundColor = backgroundColor;
    }

    public String getMessage() {
        return messageText;
    }

    public void setMessage(String messageText) {
        this.messageText = messageText;
    }

    public boolean isBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(boolean backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
