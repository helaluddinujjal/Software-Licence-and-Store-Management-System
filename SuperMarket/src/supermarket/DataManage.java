/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author helal
 */
public class DataManage {

    public boolean storeData(String path, String[] data, boolean hashFormat) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        try ( FileWriter myWriter = new FileWriter(path)) {
            if (data.length > 0) {
                if (hashFormat == true) {
                   
                    try {
                       Hash hash = new Hash();
                        for (String inp : data) {
                            myWriter.write(hash.encrypt(inp) + "\n");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(DataManage.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    for (String inp : data) {
                        myWriter.write(inp + "\n");
                    }
                }

            }
            myWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;

    }
    
      public Boolean fileExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

      public void getDbConnected(String path){
            File file=new File(path);
          try ( Scanner data = new Scanner(file)) {
              Hash hash;
              String[] dbData = {"", "", "", ""};
                try {
                    hash = new Hash();
                     int i = 0;
                    while (data.hasNextLine()) {
                        dbData[i] = hash.decrypt(data.nextLine());
                        i++;
                    }
                    data.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(DataManage.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
                    boolean connect = new DatabaseConector().checkDriver(dbData[0], dbData[1], dbData[2], dbData[3]);
                    if (connect) {
                        DatabaseConector.driver=dbData[0];
                        DatabaseConector.url=dbData[1];
                        DatabaseConector.user=dbData[2];
                        DatabaseConector.pass=dbData[3];
                        if (new DatabaseConector().checkAdmin()==true) {
                            new Login().setVisible(true);
                            new LicenceKeyActivation().dispose();
                        }else{
                            new AddAdmin().setVisible(true);
                            new LicenceKeyActivation().dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Your database  Connection is lost.Please connect again ");
                        new DBConnect().setVisible(true);
                        new LicenceKeyActivation().dispose();
                    }
                } catch (IOException e) {
                     JOptionPane.showMessageDialog(null, e);
              System.out.println("An error occurred.");
              e.printStackTrace();
          }
      } 
}
