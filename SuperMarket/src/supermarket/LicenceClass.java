/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author helal
 */
public class LicenceClass {

    static String name = null;
    static String product = null;
    static String product_code = null;
    static String db_support = null;
    static String max_client = null;
    static String use_client = null;
    static String price = null;
    static String licence = null;
    static String expired_date = null;

    public void licenceFileCreate() {
        File file = new File("src/data/server.dat");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try ( FileWriter myWriter = new FileWriter("src/data/MainServer.dat")) {
//            myWriter.write(hash.encrypt(driver.getText()) + "\n");
//            DatabaseConector.soft_driver = driver.getText();
//            DatabaseConector.soft_url = url.getText();
//            DatabaseConector.soft_user = user.getText();
//            DatabaseConector.soft_pass = password.getText();
//            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static boolean checkExpiredDate() {
        if (expired_date != null) {
            LocalDate ld = LocalDate.parse(expired_date);
            ZoneId z = ZoneId.of("Asia/Dhaka");
            LocalDate today = LocalDate.now(z);
            System.out.println("today:" + today);
            if (today.isEqual(ld)) {
                return true;
            } else {
                return false;
            }
            
        }
        return false;
    }
    
        public static boolean checkUserUse() {
            if (Integer.parseInt(max_client)<=new ServerDBConnect().getUseLicence(licence)) {
                return true;
            }
            return false;
    }
}
