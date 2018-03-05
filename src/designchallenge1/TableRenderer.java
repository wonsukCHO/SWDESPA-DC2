/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.lang.reflect.Field;
/**
 *
 * @author Arturo III
 */
public class TableRenderer extends JTextPane implements TableCellRenderer {
    private ArrayList<Event> addedEvents;
    
    public TableRenderer(ArrayList<Event> newEvents) {
        addedEvents = newEvents;
        
    }
    
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        
        Style style = this.addStyle("SAMPLE STYLE", null);
        
        if (value != null) {
            String val = value.toString();
            setText(val);
            
            String date = "";
            for (int i = 0; i < val.length();i++) {
                if (Character.isDigit(val.charAt(i)))
                    date += val.charAt(i);
                if (i >= 2)
                    break;
            }
            List<String> keys = new ArrayList<>();
            for (int i = 0; i <addedEvents.size();i++) {
                if (val.contains(addedEvents.get(i).getName())) {
                    int firstIndex = val.indexOf(addedEvents.get(i).getName());
                    int lastIndex = firstIndex + addedEvents.get(i).getName().length();
                    keys.add(val.substring(firstIndex, lastIndex));
                    keys.add(addedEvents.get(i).getColor());
                }
            }
            StyleConstants.setForeground(style, Color.black);
            setText(date);
            for (int i = 0; i < keys.size();i += 2) {
                Color c = null;
                try {
                    Field field = Class.forName("java.awt.Color").getField(keys.get(i+1).toLowerCase().trim().toLowerCase());
                    c = (Color)field.get(null);
                }
                catch (Exception e) {
                    c = Color.black;
                }
                StyleConstants.setForeground(style, c);
                this.append(keys.get(i) + "\n", style);
            }

        }
        else {
            setText("");
        }

        
        if (column == 0 || column == 6)
            setBackground(new Color(220,220,255));
        else
            setBackground(Color.WHITE);
        
        if (selected) {
            Color c = this.getBackground();
            setBackground(c.darker());
        }
            
        setBorder(null);
        return this;  

    }
    
    public void append(String s, Style style) {
        try {
            StyledDocument doc = this.getStyledDocument();
            doc.insertString(doc.getLength(), s, style);
        } catch(BadLocationException exc) {
            exc.printStackTrace();
        }
    }

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 
package designchallenge1;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6)
                    setBackground(new Color(220,220,255));
            else
                    setBackground(Color.WHITE);
            setBorder(null);
            setForeground(Color.black);
            return this;  
    }
}

*/