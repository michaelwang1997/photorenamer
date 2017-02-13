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
import static org.junit.Assert.assertTrue;

/**
 * @author MichaelWang
 *
 */
public class FileNameManagerTest {
	private FileNameManager manager;
    private FileChangedListener listener;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = FileNameManager.getInstance();
        listener = new FileChangedListener() {
            @Override
            public void onFilesChanged() {

            }
        };
	}

    @Test
	public void testGetInstance(){
		assertEquals("getInstance Failed", manager, FileNameManager.getInstance());
	}

    @Test
	public void testSetPath(){
		manager.setPath("This/is/a/samplePath");
		assertEquals("getInstance Failed", manager.getSelectedFilePath(), "This/is/a/samplePath");
	}

    @Test
	public void testModify(){
        List<String> tags = new ArrayList<>();
        tags.add("animal");
        manager.modify("oldName", tags, true);
        assertNotNull("Create new record and add tag failed", manager.getRecord("@animaloldName"));

        manager.modify("@animaloldName", tags, false);
        assertEquals("Remove tag failed", manager.getRecord("oldName").getLogs().size(), 2);
	}

    @Test
	public void testToString(){
        assertNotNull("toString failed", manager.toString());
	}

    @Test
	public void testAttach(){
        manager.attachListener(listener);
        assertTrue("Adding listener failed", manager.getFileChangedListeners().size() == 1);
        manager.attachListener(listener);
        assertTrue("Listener repeat check failed", manager.getFileChangedListeners().size() == 1);
	}
}
