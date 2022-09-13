/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package software.licence;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author helal
 */
public class Basic {
    public void getActiveMenu(MouseEvent e,JLabel dash_menu,JLabel pro_menu,JLabel cust_menu,JLabel purchase_menu,JLabel licence_menu,JPanel dash_panel,JPanel pro_panel,JPanel cust_panel,JPanel purchase_panel,JPanel licence_panel){
        if (e.getSource()==dash_menu) {
            dash_menu.setForeground(new Color(144,143,155));
            dash_panel.setVisible(true);
        } else {
            dash_menu.setForeground(new Color(255,255,255));
             dash_panel.setVisible(false);
        }
        
        if (e.getSource()==pro_menu) {
            pro_menu.setForeground(new Color(144,143,155));
            pro_panel.setVisible(true);
        } else {
            pro_menu.setForeground(new Color(255,255,255));
            pro_panel.setVisible(false);
        }
        
        if (e.getSource()==cust_menu) {
            cust_menu.setForeground(new Color(144,143,155));
            cust_panel.setVisible(true);
        } else {
            cust_menu.setForeground(new Color(255,255,255));
            cust_panel.setVisible(false);
        }
        
        if (e.getSource()==purchase_menu) {
            purchase_menu.setForeground(new Color(144,143,155));
            purchase_panel.setVisible(true);
        } else {
            purchase_menu.setForeground(new Color(255,255,255));
             purchase_panel.setVisible(false);
        }
        
         if (e.getSource()==licence_menu) {
            licence_menu.setForeground(new Color(144,143,155));
            licence_panel.setVisible(true);
        } else {
            licence_menu.setForeground(new Color(255,255,255));
             licence_panel.setVisible(false);
        }
    }
}
