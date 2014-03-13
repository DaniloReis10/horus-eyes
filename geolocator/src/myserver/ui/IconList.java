/*
 * Test.java
 *
 * Created on September 20, 2004, 9:47 PM
 */

package myserver.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 */
class MyCellRenderer extends JLabel implements ListCellRenderer {
    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	/**
     *
     */
    public MyCellRenderer() {
        // Don't paint behind the component
        setOpaque(true);
    }
    // Set the attributes of the
    // value to display
    // cell index
    // is selected
    // cell has focus?
    //class and return a reference
    public Component getListCellRendererComponent(JList list,Object value,int index, boolean iss,boolean chf) {
        // Set the text and color
        //background for rendering
        setText(((ListItem)value).getValue());
        setIcon(((ListItem)value).getIcon());
        // Set a border if the list
        //item is selected
        if (iss) {
            setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        }
        return this;
    }
}
/**
 *
 */
public class IconList {
    DefaultListModel model;
    JList             list;
    JFrame           frame;
    int       width,heigth;
    int                x,y;
    
    /**
     *
     */
    public IconList(){
        // Use a list model that
        //allows for updates
        model = new DefaultListModel();
        list  = new JList(model);
        list.setCellRenderer(new MyCellRenderer());
    }
    /**
     *
     */
    public JList getJList(){
        return list;
    }
    /**
     *
     */
    public int addItem(ListItem item){
        model.addElement(item);
        heigth+= 16;
        frame.setBounds(x,y,width,heigth);
        this.show();
        return list.getModel().getSize();
    }
    /**
     *
     */
    public ListItem getItem(int index){
        return (ListItem) list.getModel().getElementAt(index);
    }
    /**
     *
     */
    public void removeItem(int index){
        list.remove(index);
    }
    /**
     *
     */
    public void createWindow(int x,int y){
        
        this.x = x;
        this.y = y;
        frame = new JFrame("Current Connections");
        frame.setLocation(x, y);
        frame.addWindowListener(
        new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // Display the list
        JPanel panel = new JPanel();
        panel.add(this.getJList());
        frame.getContentPane().add("Center", panel);
        frame.pack();
        frame.setBounds(x,y,200,40);
        width  = frame.getWidth();
        heigth = frame.getHeight();
        frame.setVisible(true);
    }
    public void show(){
        frame.setVisible(false);
        frame.show();
        frame.setVisible(true);
        frame.show();
        
    }
}
