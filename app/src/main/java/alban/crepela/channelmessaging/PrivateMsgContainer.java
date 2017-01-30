package alban.crepela.channelmessaging;

import java.util.List;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class PrivateMsgContainer {
    private List<PrivateMsg> messages;

    public List<PrivateMsg> getMessages() {
        return messages;
    }

    public void setMessages(List<PrivateMsg> messages) {
        this.messages = messages;
    }

    public PrivateMsgContainer(List<PrivateMsg> messages) {
        this.messages = messages;
    }
}
