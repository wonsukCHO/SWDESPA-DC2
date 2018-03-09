/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6)
                if (value != null)
                    setBackground(new Color(203,255,98));
                else
                    setBackground(Color.WHITE);
                    
            else
                if (value != null)
                    setBackground(new Color(176,216,255));
                else
                    setBackground(Color.WHITE);
            
            if (selected) {
            Color c = this.getBackground();
            setBackground(c.darker());
            }
            setBorder(null);
            setForeground(Color.black);
            return this;  
    }
}
