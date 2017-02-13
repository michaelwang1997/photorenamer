package photo_renamer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vanshil on 2016-11-13.
 */
public class SelectableButton extends JButton{

    private boolean isSelected = false;
    
    /**
     * Creates a new SelectableButton with Text
     * 
     * @param text
     * 			  the text on the Button
     */
    public SelectableButton(String text){
        super(text);
        addToggleListener();
    }

    /**
     * Creates a new SelectableButton
     * 
     */
    public SelectableButton() {
        super();
        addToggleListener();
    }
    
    /**
     *The ActionListener when a button a selected
     * 
     */
    private void addToggleListener(){
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSelected();
                setBackground(isSelected()?Themer.buttonSelected:Themer.buttonPrimary);
            }
        });
    }
    
    public boolean isSelected(){
        return isSelected;
    }
    
    /**
     * Changes isSelected when the button is Selected
     */
    public void toggleSelected(){
        isSelected = !isSelected;
    }
}
