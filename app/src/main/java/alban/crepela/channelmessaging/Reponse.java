package alban.crepela.channelmessaging;

/**
 * Created by crepela on 20/01/2017.
 */
public class Reponse {
    private String response;
    private String code;
    private String accesstoken;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public Reponse(String response, String code, String accesstoken) {
        this.response = response;
        this.code = code;
        this.accesstoken = accesstoken;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "response='" + response + '\'' +
                ", code='" + code + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
                '}';
    }
}
