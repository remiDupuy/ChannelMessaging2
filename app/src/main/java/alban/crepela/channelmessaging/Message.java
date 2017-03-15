package alban.crepela.channelmessaging;

import java.util.Date;

/**
 * Created by dupuyr on 23/01/2017.
 */
public class Message {
    private int userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;
    private String messageImageUrl;

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    private String soundUrl;



    public String getMessageImageUrl() {
        return messageImageUrl;
    }

    public void setMessageImageUrl(String messageImageUrl) {
        this.messageImageUrl = messageImageUrl;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }



    public Message() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
