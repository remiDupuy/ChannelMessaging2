package alban.crepela.channelmessaging;

import java.util.StringJoiner;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class PrivateMsg {
    private String userID;
    private int sendbyme;
    private String username;
    private String message;
    private String date;
    private String imageUrl;
    private String everRead;

    public PrivateMsg(String userID, int sendbyme, String username, String message, String date, String imageUrl, String everRead) {
        this.userID = userID;
        this.sendbyme = sendbyme;
        this.username = username;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
        this.everRead = everRead;
    }

    public String getUserid() {
        return userID;
    }

    public void setUserid(String userid) {
        this.userID = userid;
    }

    public int getSendbyme() {
        return sendbyme;
    }

    public void setSendbyme(int sendbyme) {
        this.sendbyme = sendbyme;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEverRead() {
        return everRead;
    }

    public void setEverRead(String everRead) {
        this.everRead = everRead;
    }
}
