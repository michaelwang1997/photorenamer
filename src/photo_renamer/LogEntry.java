package photo_renamer;
import java.io.Serializable;

/**
 * Created by MichaelWang on 2016-11-13.
 */
public class LogEntry implements Serializable{
    private long time;
    private String from;
    private String to;

    /**
     * A new LogEntry that sets the current time
     * 
     * @param from
     * 			  the previous name of the photo
     * @param to
     * 			  the new name of the photo
     */
    public LogEntry(String from, String to) {
        this.time = System.currentTimeMillis();
        this.from = from;
        this.to = to;
        System.out.println("Changed " + from + " to " + to + "at: " + time);
    }


    
    @Override
    /**
     * Return a String representation of a LogEntry
     * 
     * @return a String representation of this LogEntry
     */
    public String toString(){
        return (from + " ==> " + to + ": " + time);
    }

    /**
     * Return the current Time
     * 
     * @return the time of the LogEntry
     */
    public long getTime() {
        return time;
    }

    /**
     * Return the previous name
     * 
     * @return the previous name
     */
    public String getFrom() {
        return from;
    }

    /**
     * Return the new name
     * 
     * @return the new name
     */
    public String getTo() {
        return to;
    }

}
