/**
 * 
 */
package photo_renamer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author MichaelWang
 *
 */
public class FileRecordTest {
	private FileRecord record;
	private List<LogEntry> logs;
	private List<String> tag;
	private LogEntry log;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		record = new FileRecord();
		logs = new ArrayList<>();
		tag = new ArrayList<>();
		log = new LogEntry("FirstName", "SecondName");
		logs.add(log);
		tag.add("Tag");


		
	}

	@Test
	public void testAddChange() {
		record.addChange("Tag", "FirstName", "SecondName");
		assertEquals("addChange failed", tag.size(), record.getTags().size());
		}
	@Test
	public void testAddLog() {
		String from = "FirstName";
		String to = "SecondName";
		record.addLog(from, to);
		assertEquals("addLog failed", logs.size(), record.getLogs().size());
		}
	@Test
	public void testGetTags() {
		record.addChange("Tag", "FirstName", "SecondName");
		assertEquals("addChange failed", tag, record.getTags());
		}
	@Test
	public void testGetLogs() {
		String from = "FirstName";
		String to = "SecondName";
		record.addLog(from, to);
		assertNotNull("addLog failed", record.getLogs());
		}
	@Test
	public void testToString() {
		String from = "FirstName";
		String to = "SecondName";
		record.addLog(from, to);	
		assertNotNull("toString failed", record.toString());
	}

}
