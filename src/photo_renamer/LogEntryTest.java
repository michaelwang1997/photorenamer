/**
 * 
 */
package photo_renamer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author MichaelWang
 *
 */
public class LogEntryTest {
	 private String string1, string2;
	 private LogEntry log;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		string1 = new String("FirstString");
		string2 = new String("SecondString");
		log = new LogEntry(string1, string2);
	}

	@Test
	public void testGetTime() {
		assertNotNull("getTime failed", log.getTime());
	}
	
	@Test
	public void testGetFrom() {
		assertEquals("getFrom failed", "FirstString", log.getFrom());
		
	}
	@Test
	public void testGetTo() {
		assertEquals("getTo failed", "SecondString", log.getTo());
	}
	@Test
	public void testToString() {
		assertEquals("toString failed", string1 + " ==> " + string2 + ": " + log.getTime(), log.toString());
	}
}


