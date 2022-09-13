/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package software.licence;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

  class ButtonRenderer extends JButton implements TableCellRenderer 
  {
    public ButtonRenderer() {
      setOpaque(true);
     
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
    boolean isSelected, boolean hasFocus, int row, int column) {
      setText((value == null) ? "Modify" : value.toString());
      return this;
    }
  }

