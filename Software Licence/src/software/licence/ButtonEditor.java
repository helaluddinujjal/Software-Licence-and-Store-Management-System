/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package software.licence;

import java.awt.*;
import javax.swing.*;

class ButtonEditor extends DefaultCellEditor {
JButton button = new JButton();
    private String label;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "Modify" : value.toString();
        button.setText(label);
        return button;
    }

    public Object getCellEditorValue() {
        return new String(label);
    }
}

