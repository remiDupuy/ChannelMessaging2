package alban.crepela.channelmessaging;

/**
 * Created by crepela on 20/01/2017.
 */
public class Channel {
    private String channelID;
    private String name;
    private String connectedusers;

    @Override
    public String toString() {
        return "Channel{" +
                "channelID=" + channelID +
                ", name='" + name + '\'' +
                ", connectedusers=" + connectedusers +
                '}';
    }

    public Channel(String connectedusers, String name, String channelID) {
        this.connectedusers = connectedusers;
        this.name = name;
        this.channelID = channelID;
    }

    public String getConnectedusers() {

        return connectedusers;
    }

    public void setConnectedusers(String connectedusers) {
        this.connectedusers = connectedusers;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
