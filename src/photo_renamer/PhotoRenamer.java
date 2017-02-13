package photo_renamer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotoRenamer {
    public static final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 800;


    public static void main(String[] args) throws IOException {
    	
        JFrame jf = new JFrame("Photo Tagger");
        jf.setLocation(400, 400);
        jf.setResizable(false);
        jf.setLayout(new BorderLayout());
        jf.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jf.setLocationRelativeTo(null);
        //Let user select a file
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(filePanel);
        topPanel.add(scrollPane);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
        topPanel.add(imagePanel);
        
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("/Users/Vanshil/Pictures/city.jpg")));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setPreferredSize(new Dimension((int)(WINDOW_WIDTH*0.35), WINDOW_HEIGHT/2));
        imagePanel.add(imageLabel);

        JTextPane textField = new JTextPane();
        JScrollPane textScroll = new JScrollPane(textField);
        imagePanel.add(textScroll);

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.PAGE_AXIS));
        tagPanel.setPreferredSize(new Dimension(50, WINDOW_HEIGHT));
        JScrollPane tagScroll = new JScrollPane(tagPanel);
        topPanel.add(tagScroll);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        JButton addTag = new JButton("Add Tag");
        buttonPanel.add(addTag);

        JButton deleteTag = new JButton("Delete Tag");
        buttonPanel.add(deleteTag);

        JTextArea focusedButton = new JTextArea();
        focusedButton.setFont(Themer.largeFont);
        focusedButton.setPreferredSize(new Dimension(300, -1));
        //buttonPanel.add(focusedButton);

        jf.add(topPanel, BorderLayout.CENTER);
        jf.add(buttonPanel, BorderLayout.PAGE_END);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(jf);


        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            FileNameManager.setPath(selectedFile.getPath());
            System.out.println(selectedFile);

            FileNameManager.getInstance().attachListener(new FileChangedListener(){

                @Override
                public void onFilesChanged() {
                    reloadFiles(selectedFile, icon, imageLabel, textField, filePanel, tagPanel, focusedButton);
                    Themer.theme(filePanel);
                }
            });
            reloadFiles(selectedFile, icon, imageLabel, textField, filePanel, tagPanel, focusedButton);
        }

        //Add tag panel
        addTag.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                JFrame addTagFrame = new JFrame("Add Tag");
                addTagFrame.setLayout(new  GridLayout(3,1));
                addTagFrame.setLocation(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
                addTagFrame.setLocationRelativeTo(jf);
                JLabel text = new JLabel("Please add a tag");
                JTextField textField = new JTextField();
                JButton finishTag = new JButton("Add Tag");

                finishTag.addActionListener(
                        new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                for(Component fileButton : filePanel.getComponents()){
                                    if(((SelectableButton)fileButton).isSelected()){
                                        FileNameManager.getInstance().modify(((JButton)fileButton).getText(), Arrays.asList(textField.getText().split(",")), true);
                                    }
                                }
                                FileNameManager.getInstance().save();
                                addTagFrame.setVisible(false);
                            }
                        }
                );

                addTagFrame.add(text);
                addTagFrame.add(textField);
                addTagFrame.add(finishTag);

                addTagFrame.pack();
                addTagFrame.setVisible(true);

            }});
        deleteTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(focusedButton != null){
                    for(Component tagButton : tagPanel.getComponents()){
                        ArrayList<String> tagsToRemove = new ArrayList<String>();
                        if(((SelectableButton)tagButton).isSelected()){
                            tagsToRemove.add(((SelectableButton) tagButton).getText());
                        }
                        FileNameManager.getInstance().modify(focusedButton.getText(), tagsToRemove, false);
                        FileNameManager.getInstance().save();
                    }
                }
            }
        });
        Themer.theme(topPanel);
        Themer.theme(buttonPanel);
        jf.pack();
        jf.setVisible(true);

    }
    
    /**
     * Sets the name of the files on buttons and adds contents to the GUI
     * 
     * @param selectedFile
     * 				 A directory that may contain a list of pictures
     * @param icon
     * 			  The image icon that is used to show the picture on a panel
     * @param imageLabel
     * 					The label for the image
     * @param textField
     * 				   The text field to show the history of tagging
     * @param filePanel
     * 				   The panel to add all of the buttons of the photos
     * @param tagPanel
     * 				   A panel that holds the buttons when a tag is added
     * @param focusedButton
     * 				   The text of the file
     */
    private static void reloadFiles(File selectedFile, ImageIcon icon, JLabel imageLabel, JTextPane textField, JPanel filePanel, JPanel tagPanel, JTextArea focusedButton){
        File[] files = selectedFile.listFiles();
        filePanel.removeAll();

        for(File file : files){
            if(file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
                SelectableButton fileButton = new SelectableButton();
                fileButton.setPreferredSize(new Dimension(PhotoRenamer.WINDOW_WIDTH / 2 - 50, 30));
                fileButton.setText(file.getName());
                fileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!fileButton.isSelected()) {
                            try {
                                BufferedImage img = ImageIO.read(file);
                                //icon.setImage(img.getScaledInstance(400, 400, Image.SCALE_SMOOTH));
                                icon.setImage(img);
                                imageLabel.repaint();
                                imageLabel.setPreferredSize(new Dimension((int)(WINDOW_WIDTH*0.35), WINDOW_HEIGHT/2));

                                FileRecord record = FileNameManager.getInstance().getRecord(file.getName());
                                textField.setText(String.valueOf(record));

                                tagPanel.removeAll();
                                for (String tag : record.getTags()) {
                                    JButton tagButton = new SelectableButton(tag);
                                    tagPanel.add(tagButton);
                                }
                                tagPanel.repaint();
                                tagPanel.revalidate();
                                focusedButton.setText(fileButton.getText());
                                Themer.theme(tagPanel);

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

                filePanel.add(fileButton);
            }
        }

        //((JButton)filePanel.getComponents()[0]).doClick();
        filePanel.repaint();
        filePanel.revalidate();
    }
}
