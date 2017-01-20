package alban.crepela.channelmessaging;

import java.util.List;

/**
 * Created by crepela on 20/01/2017.
 */
public class ChannelsContainer {
    private List<Channel> channels;

    @Override
    public String toString() {
        return "ChannelsContainer{" +
                "channels=" + channels +
                '}';
    }

    public ChannelsContainer(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannels() {

        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
