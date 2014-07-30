/*
 * ListItem.java
 *
 * Created on September 21, 2004, 2:22 PM
 */

package myserver.ui;

import javax.swing.Icon;

/**
 *
 * @author  Danilo
 */
public class ListItem {
    /**
     *
     */
    private Icon   icon;
    /**
     *
     */
    private String value;
    
    /**
     *
     */
    public ListItem(Icon ic, String s) {
        icon  = ic;
        value = s;
    }
    /**
     * Setter for property icon.
     * @param icon New value of property icon.
     */
    public void setIcon(javax.swing.Icon icon) {
        this.icon = icon;
    }
    
    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }
    
    /**
     * Getter for property icon.
     * @return Value of property icon.
     */
    public javax.swing.Icon getIcon() {
        return icon;
    }
    
    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public java.lang.String getValue() {
        return value;
    }
    
}
