package alban.crepela.channelmessaging;

import java.util.List;

/**
 * Created by dupuyr on 23/01/2017.
 */
public class MessagesContainer {

    private List<Message> messages;

    @Override
    public String toString() {
        return "MessagesContainer{" +
                "messages=" + messages +
                '}';
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public MessagesContainer(List<Message> messages) {
        this.messages = messages;
    }
}
