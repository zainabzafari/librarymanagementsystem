package libraryBackend;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
	private static final Color[] ROW_COLORS = {new Color(252, 220, 42), new Color(135, 169, 34)};

    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Set alternating colors for rows (excluding header row)
        if (!table.isRowSelected(row)) {
            cellComponent.setBackground(ROW_COLORS[row % ROW_COLORS.length]);
        }
        
        return cellComponent;
    }
}

