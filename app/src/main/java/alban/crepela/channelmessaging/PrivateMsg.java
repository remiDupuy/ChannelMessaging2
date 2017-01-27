package alban.crepela.channelmessaging;

import java.util.StringJoiner;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class PrivateMsg {
    private String userid;
    private String sendbyme;
    private String username;
    private String message;
    private String date;
    private String imageUrl;
    private String everRead;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSendbyme() {
        return sendbyme;
    }

    public void setSendbyme(String sendbyme) {
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
