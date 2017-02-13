package photo_renamer;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Vanshil on 2016-11-13.
 */
public class Themer {
    public static final Font largeFont = new Font(Font.SANS_SERIF, 3, 24);
    public static final Color backgroundPrimary = new Color(255, 255, 255);
    public static final Color textPrimary = new Color(0,0,0);
    public static final Color buttonTextPrimary = new Color(255, 255, 255);
    public static final Color buttonPrimary = new Color(162, 222, 208);
    public static final Color buttonSelected = new Color(200, 247, 197);
   
    /**
     * Reformats a component 
     * 
     * @param component
     * 				   the component that is being reformatted
     */
    public static void theme(Component component){

        if (component instanceof JButton) {
            themeButton((JButton)component);
        }
        if (component instanceof JPanel) {
            themePanel((JPanel) component);
        }
        if (component instanceof JFrame) {
            themeFrame((JFrame)component);
        }
        if (component instanceof JScrollPane) {
            themeScrollPane((JScrollPane)component);
        }
        themeChildren(component);
    }
    /**
     * Reformat the JButton
     * 
     * @param jButton
     * 				  the button that is being reformatted
     */
    private static void themeButton(JButton jButton){
        //jButton.setBorder(BorderFactory.createEmptyBorder());
        //jButton.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, backgroundPrimary));
        jButton.setMargin(new Insets(15, 15, 15, 15));
        jButton.setBackground(buttonPrimary);
        jButton.setForeground(buttonTextPrimary);
    }
    /**
     * Reformat the JPanel
     *  
     * @param panel
     * 				the JPanel being reformmated
     */
    private static void themePanel(JPanel panel){
            panel.setBackground(backgroundPrimary);


    }
    /**
     * Reformat the JFrame
     * @param frame
     * 			   the JFrame that is being reformatted
     */
    private static void themeFrame(JFrame frame){
        frame.setBackground(backgroundPrimary);

    }
    /**
     * Reformat the JScrollPane
     * 
     * @param scrollPane
     * 				    the JScrollPane being reformatted
     */
    private static void themeScrollPane(JScrollPane scrollPane){
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, backgroundPrimary));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
    /**
     * Reformats all the children of a Component
     * 
     * @param component
     * 					the component that contains the children components
     */
    private static void themeChildren(Component component){
        Component[] children = new Component[0];
        if(component instanceof JFrame) {
            children = ((JFrame) component).getComponents();
        }
        if(component instanceof JPanel){
            children = ((JPanel) component).getComponents();
        }
        if(component instanceof JScrollPane){
            children = ((JScrollPane) component).getComponents();
        }
        if(component instanceof JViewport){
            children = ((JViewport) component).getComponents();
        }
        //System.out.println(children.length + component.toString());
        for(Component child : children){
            theme(child);
        }
    }
}
