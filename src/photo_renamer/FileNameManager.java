package photo_renamer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MichaelWang on 2016-11-13.
 */
// This is class uses the Singleton design pattern
// This class make sure that only one object gets created and provides direct access to the object
// without it being instantiated
public class FileNameManager implements Serializable {
	private static final String recordName = "phototagger.record";
	private static FileNameManager instance;
	private static String selectedFilePath;
	private List<FileChangedListener> fileChangedListeners;

	private HashMap<String, FileRecord> fileRecords;
	/**
	 * Return a FileNameManager instance
	 *
	 * @return a FileNameManger
	 */
	public static FileNameManager getInstance() {
		if (instance == null && !readFile()) {// checks if instance is null, if
												// it is, readfile is called,
												// and if that fails,
												// initializes instance
			instance = new FileNameManager();
		}
		System.out.println(instance);
		return instance;

	}

	/**
	 * Set the path
	 *
	 * @param path
	 *            the path of the file
	 */
	public static void setPath(String path) {
		selectedFilePath = path;
	}

	/**
	 * Return a FileRecord if String fileName is in the fileRecord, otherwise,
	 * return the fileRecord with the fileName placed inside
	 *
	 * @param fileName
	 *            the name of the file
	 * @return a FileRecord containing the string, fileName
	 */
	public FileRecord getRecord(String fileName) {
		if (fileRecords.containsKey(fileName)) {
			return fileRecords.get(fileName);
		} else {
			FileRecord record = new FileRecord();
			fileRecords.put(fileName, record);
			return record;
		}
	}

	/**
	 * Changes a file from its old name to the new name
	 *
	 * @param oldName
	 * 				 the old name of the file
	 * @param newName
	 * 				 the new name of the file
	 */
	private void writeFile(String oldName, String newName) {
		File file = new File(this.selectedFilePath + "/" + oldName);
		String path = this.selectedFilePath + "/" + newName;
		System.out.println(path);
		file.renameTo(new File(path));
	}

	/**
	 * Renames the image file based on the tag and modifies the FileRecord of
	 * that image if a FileRecord exists for the image, otherwise, creates a new
	 * FileRecord
	 *
	 * @param oldName
	 *            the oldname of the imagefile
	 * @param tagsToChange
	 *            the tags that require changing
	 * @param add
	 *            adds the tags if this flag is true
	 */
	public void modify(String oldName, List<String> tagsToChange, boolean add) {
		FileRecord fileRecord = null;
		if (fileRecords.containsKey(oldName)) {
			fileRecord = fileRecords.get(oldName);
		} else {
			fileRecord = new FileRecord();
		}
		fileRecords.remove(oldName);

		String newName;

		List<String> tags = fileRecord.getTags();

		if (add) {
			String tag = tagsToChange.get(0);
			newName = "@" + tag + oldName;
			if (!tags.contains(tag) && tag.length() > 0) {
				// System.out.println("tagging " + oldName + " with @" + tag);
				writeFile(oldName, newName);
				fileRecord.addChange(tag, oldName, newName);

			} else {
				System.out.println("Uh-oh! " + oldName + " is already tagged with @" + tag);
			}
		} else {
			tags.removeAll(tagsToChange);
			newName = fileRecord.getLogs().get(0).getFrom();
			for (String fileTag : fileRecord.getTags()) {
				newName = "@" + fileTag + newName;
			}
			writeFile(oldName, newName);
			fileRecord.addLog(oldName, newName);
		}
		fileRecords.put(newName, fileRecord);
	}

	private FileNameManager() {
		fileChangedListeners = new ArrayList<>();
        fileRecords = new HashMap<>();
	}

	/**
	 * Returns True iff the file is read successfully, otherwise, return False
	 *
	 * @return a boolean of whether a file has been read
	 */
	private static boolean readFile() {
		try {
			FileInputStream fis = new FileInputStream(selectedFilePath + "/" + recordName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			instance = (FileNameManager) ois.readObject();
			System.out.println("found file");
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Saves a FileNameManager into a file to hold records
	 *
	 */
	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(selectedFilePath + "/" + recordName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(instance);
			notifyFilesChanged();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Attach a fileChangedListener to the list of fileChangedListeners
	 *
	 * @param fileChangedListener
	 *            the fileChangedListener to attach
	 */
	public void attachListener(FileChangedListener fileChangedListener){
		if (!fileChangedListeners.contains(fileChangedListener)) {
			fileChangedListeners.add(fileChangedListener);
		}
	}
	/**
	 * Detach a fileChangedListener to the list of fileChangedListeners
	 *
	 * @param fileChangedListener
	 *            the fileChangedListener to detach
	 */
	public void detachListener(FileChangedListener fileChangedListener){
		fileChangedListeners.remove(fileChangedListener);
	}

	@Override
	/**
	 * Return a String representation of FileNameManager that shows a file's
	 * past and current names
	 *
	 * @return a String representation of this the FileNameManager
	 */
	public String toString() {
		String toReturn = "";
		for (String name : fileRecords.keySet()) {
			toReturn += "Filename: " + name + "\n";
			toReturn += fileRecords.get(name);
		}
		return toReturn;
	}

	/**
	 * Notifies that the files have changed
	 *
	 */
	private void notifyFilesChanged() {
		for(FileChangedListener fileChangedListener : fileChangedListeners){
			if (fileChangedListener != null) {
				fileChangedListener.onFilesChanged();
			}
		}
	}


	/**
	 * Get the path
	 *
	 * @return
	 * 			the selectedFilePath
	 */
	public static String getSelectedFilePath() {
		return selectedFilePath;
	}

	/**
	 * Get the fileChangedListeners
	 *
	 * @return
	 * 			the fileChangedListeners
	 */
	public List<FileChangedListener> getFileChangedListeners() {
		return fileChangedListeners;
	}

	/**
	 * Get the fileRecords
	 *
	 * @return
	 * 			the fileRecords
	 */
	public HashMap<String, FileRecord> getFileRecords() {
		return fileRecords;
	}
}
