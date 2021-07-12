package packages;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener {

    static JTextArea textarea;
    JMenuBar menuBar;
    JMenu file, edit, format;
    JMenuItem jNew, jOpen, jSave, jSaveas, jExit, jUndo, jRedo, jCut, jCopy, jPaste, jSelect, jSelectAll, jBackColor, jFontColor;
    Image icon;
    String fileName, findText, fileContent;
    JCheckBoxMenuItem jWordWrap;
    JFileChooser fileChooser;
    JToolBar toolBar;
    UndoManager un;

    public TextEditor() {

        super("Text Editor");
        
        icon=Toolkit.getDefaultToolkit().getImage("images/img3.png");
        setIconImage(icon);

        fileChooser=new JFileChooser(".");
        un=new UndoManager();

        textarea=new JTextArea();
        getContentPane().add(textarea);
        getContentPane().add(new JScrollPane(textarea),BorderLayout.CENTER);
        
        menuBar=new JMenuBar();
        setJMenuBar(menuBar);

        file=new JMenu("File");
        menuBar.add(file);
        edit=new JMenu("Edit");
        menuBar.add(edit);
        format = new JMenu("Format");
        menuBar.add(format);

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

        jUndo=new JMenuItem("Undo");
        jRedo=new JMenuItem("Redo");
        jCut=new JMenuItem("Cut");
        jCopy=new JMenuItem("Copy");
        jPaste = new JMenuItem("Paste");
        jSelectAll = new JMenuItem("Select All");

        edit.add(jUndo);
        edit.add(jRedo);
        edit.add(jCut);
        edit.add(jCopy);
        edit.add(jPaste);
        edit.add(jSelectAll);

        jUndo.setActionCommand("undo");
        jUndo.addActionListener(this);
        jRedo.setActionCommand("redo");
        jRedo.addActionListener(this);
        jCut.addActionListener(this);
        jCopy.addActionListener(this);
        jPaste.addActionListener(this);
        jSelectAll.addActionListener(this);

        jWordWrap=new JCheckBoxMenuItem("Word Wrap", true);
        jFontColor = new JMenuItem("Font Color");
        jBackColor = new JMenuItem("Background Color");
        
        format.add(jWordWrap);
        format.add(jFontColor);
        format.add(jBackColor);

        jWordWrap.setActionCommand("wrap");
        jWordWrap.addActionListener(this);
        jFontColor.setActionCommand("fontcolor");
        jFontColor.addActionListener(this);
        jBackColor.setActionCommand("backcolor");
        jBackColor.addActionListener(this);

        textarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                un.addEdit(e.getEdit());
            }
        });

        jNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        jOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        jSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        jSaveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        jExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        jCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        jCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        jPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        jUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        jRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        jSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        setSize(600,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {

        String command=e.getActionCommand();

        if(e.getSource()==jCut) {
            textarea.cut();
        }
        if(e.getSource()==jCopy) {
            textarea.copy();
        }
        if(e.getSource()==jPaste) {
            textarea.paste();
        }
        if(e.getSource() == jSelectAll) {
            textarea.selectAll();
        }
        if(command == "undo") {
            try {
                un.undo();
            } 
            catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Field is Empty");
            }
        }
        if(command == "redo") {
            try {
                un.redo();
            } 
            catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Field is Empty");
            }
        }

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
            case "wrap":
                if(jWordWrap.isSelected()) {
                    textarea.setLineWrap(true);
                    textarea.setWrapStyleWord(true);
                    jWordWrap.setText("Word Wrap");
                } 
                else {
                    textarea.setLineWrap(false);
                    textarea.setWrapStyleWord(false);
                    jWordWrap.setText("Word Wrap");
                }
                break;
            case "fontcolor":
                Color fontcolor = JColorChooser.showDialog(this, "Select Font Color", Color.black);
                textarea.setForeground(fontcolor);
                break;
            case "backcolor":
                Color backcolor = JColorChooser.showDialog(this, "Select Background Color", Color.black);
                textarea.setBackground(backcolor);
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