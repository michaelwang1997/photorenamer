package photo_renamer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichaelWang on 2016-11-13.
 */
public class FileRecord implements Serializable{
    private List<String> tags;
    private List<LogEntry> logs;

    /**
     * Creates a new FileRecord 
     * 
     */
    public FileRecord() {
        this.tags = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    /**
     * Adds the new tag to the list of tags, and adds a new LogEntry to the list of logs
     * 
     * @param tag
     * 			  the name of the tag
     * @param from
     * 			  the previous name of the file
     * @param to
     *   		  the new name of the file
     */
    public void addChange(String tag, String from, String to){
        tags.add(tag);
        addLog(from, to);
    }
    
    /**
     * Adds a new log to the list of logs
     * 
     * @param from
     * 			  the previous name of the file
     * @param to
     *   		  the new name of the file
     */
    public void addLog(String from, String to){
        logs.add(new LogEntry(from, to));
    }

    /**
     * Return a the list of tags
     * 
     * @return the list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Return the list of LogEntries
     * 
     * @return the list of LogEntry
     */
    public List<LogEntry> getLogs() {
        return logs;
    }

   
    @Override
    /**
     * Return a string representation of the items in LogEntry
     * 
     * @return the string of LogEntry
     */
    public String toString(){
        String toReturn = "";
        for(LogEntry log : logs){
            toReturn += (log + "\n");
        }
        return toReturn;
    }

}
