package libraryBackend;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class CustomTableHeaderRenderer implements TableCellRenderer {
    private static final Color RED_BACKGROUND = Color.RED;
    private static final Color HEADER_FOREGROUND = Color.WHITE;
    private final DefaultTableCellRenderer renderer;
    
    public CustomTableHeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Set red background and white foreground for header
        component.setBackground(RED_BACKGROUND);
        component.setForeground(HEADER_FOREGROUND);
        
        return component;
    }
}




