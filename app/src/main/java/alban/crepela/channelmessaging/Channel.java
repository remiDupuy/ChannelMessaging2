package alban.crepela.channelmessaging;

/**
 * Created by crepela on 20/01/2017.
 */
public class Channel {
    private int channelID;
    private String name;
    private int connectedusers;

    @Override
    public String toString() {
        return "Channel{" +
                "channelID=" + channelID +
                ", name='" + name + '\'' +
                ", connectedusers=" + connectedusers +
                '}';
    }

    public Channel(int connectedusers, String name, int channelID) {
        this.connectedusers = connectedusers;
        this.name = name;
        this.channelID = channelID;
    }

    public int getConnectedusers() {

        return connectedusers;
    }

    public void setConnectedusers(int connectedusers) {
        this.connectedusers = connectedusers;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
