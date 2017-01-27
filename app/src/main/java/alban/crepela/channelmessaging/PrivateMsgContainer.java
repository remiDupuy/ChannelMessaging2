package alban.crepela.channelmessaging;

import java.util.List;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class PrivateMsgContainer {
    private List<PrivateMsg> listPrivateMsgs;

    public PrivateMsgContainer(List<PrivateMsg> listPrivateMsgs) {
        this.listPrivateMsgs = listPrivateMsgs;
    }

    @Override
    public String toString() {
        return "PrivateMsgContainer{" +
                "listPrivateMsgs=" + listPrivateMsgs +
                '}';
    }

    public List<PrivateMsg> getListPrivateMsgs() {
        return listPrivateMsgs;
    }

    public void setListPrivateMsgs(List<PrivateMsg> listPrivateMsgs) {
        this.listPrivateMsgs = listPrivateMsgs;
    }
}
