package photo_renamer;

import java.io.Serializable;

/**
 * Created by Vanshil on 2016-11-27.
 */
//This class uses the Observer design pattern which means there are relationships between this class and other objects
// This class is used throughout FileNameManager 
public interface FileChangedListener extends Serializable {
    public void onFilesChanged();
}
