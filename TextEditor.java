package packages;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TextEditor extends JFrame implements ActionListener {

    static JTextArea textarea;
    JMenuBar menuBar;
    JMenu file, edit;
    JMenuItem jNew, jOpen, jSave, jSaveas, jExit;
    Image icon;
    String fileName, findText, fileContent;
    JFileChooser fileChooser;
    JToolBar toolBar;

    public TextEditor() {

        super("Text Editor");
        
        icon=Toolkit.getDefaultToolkit().getImage("images/img3.png");
        setIconImage(icon);

        fileChooser=new JFileChooser(".");

        textarea=new JTextArea();
        getContentPane().add(textarea);
        getContentPane().add(new JScrollPane(textarea),BorderLayout.CENTER);
        
        menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        file=new JMenu("File");
        menuBar.add(file);
        edit=new JMenu("Edit");
        menuBar.add(edit);

        jNew=new JMenuItem("New");
        jOpen=new JMenuItem("Open");
        jSave=new JMenuItem("Save");
        jSaveas=new JMenuItem("Save As");
        jExit=new JMenuItem("Exit");
         
        file.add(jNew);
        file.add(jOpen);
        file.add(jSave);
        file.add(jSaveas);
        file.add(jExit);

        jNew.setActionCommand("New");
        jNew.addActionListener(this);
        jOpen.setActionCommand("Open");
        jOpen.addActionListener(this);
        jSave.setActionCommand("Save");
        jSave.addActionListener(this);
        jSaveas.setActionCommand("Save AS");
        jSaveas.addActionListener(this);
        jExit.setActionCommand("Exit");
        jExit.addActionListener(this);

        setSize(600,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {

        String command=e.getActionCommand();
        switch(command){
            case "New":
                newFile();
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save AS":
                saveasFile();
                break;
            case "Exit":
                exitFile();
                break;
            default:
                break;
        }

    }

    public void newFile() {

        if(!textarea.getText().equals("") && !textarea.getText().equals(fileContent)) {
            if(fileName==null) {
                int option=JOptionPane.showConfirmDialog(this, "Do you want to save the changes?");
                if(option==0) {
                    saveasFile();
                    clear();
                }
                else if(option==2) {
                    textarea.setText(textarea.getText());
                }
                else {
                    clear();
                }
            }
            else {
                int option=JOptionPane.showConfirmDialog(this, "Do you want to save the changes?");
                if(option==0) {
                    saveFile();
                    clear();
                }
                else if(option==2) {
                    textarea.setText(textarea.getText());
                } else {
                    clear();
                }
            }
        } 
        else {
            clear();
        }
      
    }

    public void openFile() {
        int option=fileChooser.showOpenDialog(this);
        try {
            if(option==JFileChooser.APPROVE_OPTION)
            {
                BufferedReader reader=new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                textarea.setText("");
                String line="";
                fileName=fileChooser.getSelectedFile().getName();
                setTitle(fileName);
                while ((line=reader.readLine()) != null) {
                    textarea.append(line + "\n");
                }
                reader.close();
            }
        }
        catch(Exception e) {
            System.out.println("File Not Found");
        }
    }

    public void clear() {
        textarea.setText("");
        setTitle("New");
        fileName = null;
        fileContent = null;
    }

    public void saveasFile() {

        FileWriter fw=null;
        int retval=-1;
        try {
            retval=fileChooser.showSaveDialog(this);
            if(retval==JFileChooser.APPROVE_OPTION) {
                File file=fileChooser.getSelectedFile();
                if(file.exists()) {
                    int option=JOptionPane.showConfirmDialog(this, "Do you want to Override this file ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(option==0){
                        fw=new FileWriter(fileChooser.getSelectedFile());
                        fw.write(textarea.getText());
                        JOptionPane.showMessageDialog(this, "File Saved");
                        fileName=fileChooser.getSelectedFile().getName();
                        setTitle(fileName);
                        fileContent=textarea.getText();
                    }
                    else {
                        saveasFile();
                    }
                }
                else {
                    fw=new FileWriter(fileChooser.getSelectedFile());
                    fw.write(textarea.getText());
                    JOptionPane.showMessageDialog(this, "File saved");
                    fileName=fileChooser.getSelectedFile().getName();
                    setTitle(fileName);
                    fileContent=textarea.getText();
                }
                fw.close();
            }
        }
        catch(Exception e) {
            System.out.println("Something Gone Wrong");
        }   

    }

    public void saveFile() {
        FileWriter fw=null;
        try {
            if(fileName==null) {
                saveasFile();
            }
            else{
                fw=new FileWriter(fileName);
                fw.write(textarea.getText());
                setTitle(fileName);
                JOptionPane.showMessageDialog(this, "File saved");
                fileContent=textarea.getText();
                fw.close();
            }
        }
        catch(Exception e) {
            System.out.println("Something Gone Wrong");
        }
    }

    public void exitFile() {
        System.exit(0);
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
    }

}