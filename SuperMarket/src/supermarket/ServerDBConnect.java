/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;
import static supermarket.DatabaseConector.url;

/**
 *
 * @author helal
 */
public class ServerDBConnect {

    static String domain = "jdbc:mysql://localhost:3306/";
    static String soft_driver = "";
    static String soft_url = "";
    static String soft_db_name = "";
    static String soft_user = "";
    static String soft_pass = "";
    Connection con;
    Statement stm;
    PreparedStatement pprds;
    String sqls;
    ResultSet rs = null;
    private static String OS = System.getProperty("os.name").toLowerCase();

    public String[] getUUID() throws IOException {
        String uuid = "";
        String machineId = null;
        if (OS.indexOf("win") >= 0) {
            StringBuffer output = new StringBuffer();
            Process process;
            String[] cmd = {"wmic", "csproduct", "get", "UUID"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            machineId = output.toString();
            // System.out.println(machineId);
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {

            StringBuffer output = new StringBuffer();
            Process process;
            String[] cmd = {"/bin/sh", "-c", "echo 123456 | sudo -S cat /sys/class/dmi/id/product_uuid"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            machineId = output.toString();
            // System.out.println(machineId);
        }
        String[] res = {OS, machineId};
        return res;
    }

    public void myServerDriver() {

        try {
            Class.forName(soft_driver);
            con = DriverManager.getConnection(soft_url, soft_user, soft_pass);
            System.out.println("connected");
        } catch (ClassNotFoundException cnf) {
            //System.out.println(cnf);
            JOptionPane.showMessageDialog(null, cnf);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }

    public boolean checkDriver(String driverDB, String urlDB, String userDB, String passDB) {
        try {
            Class.forName(driverDB);
            con = DriverManager.getConnection(urlDB, userDB, passDB);
            return true;
        } catch (ClassNotFoundException cnf) {
            //System.out.println(cnf);
            JOptionPane.showMessageDialog(null, cnf);
            return false;
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            return false;
        }
    }

    public Boolean checkLicence(String licence) {
        // System.out.println(licence);
        //System.out.println(soft_url);
        sqls = "select * from customer_tbl where licence = '" + licence + "'";
        myServerDriver();
        try {
            //System.out.println(soft_driver + soft_url);
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

            if (rs.next()) {
                LicenceClass.name = rs.getString("first_name") + " " + rs.getString("last_name");
                LicenceClass.product = rs.getString("product");
                LicenceClass.product_code = rs.getString("product_code");
                LicenceClass.max_client = rs.getString("max_client");
                LicenceClass.db_support = rs.getString("database_support");
                LicenceClass.price = rs.getString("price");
                LicenceClass.licence = rs.getString("licence");
                LicenceClass.expired_date = rs.getString("expired_date");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
//-------customer db process

    public void customerDbConnect(String licence) {

        if (checkUserDB(licence) == true && checkDatabase(soft_db_name) == true) {
            //System.out.println(soft_url);
            DatabaseConector.driver = soft_driver;
            DatabaseConector.url = domain + soft_db_name;
            DatabaseConector.user = soft_user;
            DatabaseConector.pass = soft_pass;
            new Splash().dispose();
            if (new DatabaseConector().checkAdmin()) {
                new Login().setVisible(true);
            } else {
                new AddAdmin().setVisible(true);
            }
        } else {
            if (createDatabase(soft_driver, domain, soft_user, soft_pass, "ss_")) {
                if (insertDBName(licence, soft_db_name)) {
                    DatabaseConector.driver = soft_driver;
                    DatabaseConector.url = domain + soft_db_name;
                    DatabaseConector.user = soft_user;
                    DatabaseConector.pass = soft_pass;
                    new Splash().dispose();
                    if (new DatabaseConector().checkAdmin()) {
                        new Login().setVisible(true);
                    } else {
                        new AddAdmin().setVisible(true);
                    }
                }
            }
        }

    }

    public boolean checkDatabase(String dbName) {
        try {
            // Class.forName(driverDB);
            // con = DriverManager.getConnection(urlDB, userDB, passDB);
            if (con != null) {
                rs = con.getMetaData().getCatalogs();

                while (rs.next()) {
                    String catalogs = rs.getString(1);

                    if (dbName.equals(catalogs)) {

                        return true;
                    }
                }
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            return false;
        }
        JOptionPane.showMessageDialog(null, "The database " + dbName + " is not found!!! It\'s time to create new database for your application.");
                return false;
    }

    public Boolean checkUserDB(String licence) {
        // System.out.println(licence);
        //System.out.println(soft_url);
        sqls = "select * from customer_db_tbl where licence = '" + licence + "'";
        myServerDriver();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

            if (rs.next()) {
                soft_db_name = rs.getString("db_name");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }

    public String getNewId(String column_name, String table_name) {
        myServerDriver();
        //sqls = "SELECT LAST_INSERT_ID() as lastId FROM category_tbl";
        sqls = "SELECT MAX( " + column_name + " ) FROM " + table_name;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (rs.next()) {
                //  System.out.println(rs.getInt("lastId"));
                return "" + (rs.getInt(1) + 1);

            }

        } catch (SQLException exp) {
            System.out.println(exp);
        }

        return "" + 1;
    }

    public boolean insertDBName(String licence, String name) {
        myServerDriver();
        sqls = "select * from customer_db_tbl where licence='" + licence + "'";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (!rs.next()) {
                pprds = con.prepareStatement("insert into customer_db_tbl(licence,db_name) values(?,?)");
                pprds.setString(1, licence);
                pprds.setString(2, name);
                pprds.executeUpdate();
                return true;
            } else {
                sqls = "update customer_db_tbl set db_name=? where licence='" + licence + "'";
                pprds = con.prepareStatement(sqls);
                pprds.setString(1, name);
                pprds.executeUpdate();
                return true;
            }
        } catch (SQLException exp) {
            JOptionPane.showMessageDialog(null, exp);
            return false;
        }
    }

    public Boolean createDatabase(String dri, String urls, String users, String passwords, String dbnames) {
        // url = "jdbc:mysql://localhost:3306";
        try {
            byte[] array = new byte[7]; // length is bounded by 7
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 7;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
//
//            System.out.println(generatedString);
            Class.forName(dri);
            con = DriverManager.getConnection(urls, users, passwords);
            stm = con.createStatement();
            dbnames = dbnames + generatedString + getNewId("id", "customer_db_tbl");
            String sql = "CREATE DATABASE " + dbnames;

            //String sql = "CREATE DATABASE " + dbnames;
            stm.executeUpdate(sql);
            System.out.println("Database create");
            soft_db_name = dbnames;

            url = domain + dbnames;
            con = DriverManager.getConnection(url, users, passwords);
            System.out.println("Connected database successfully...");

            stm = con.createStatement();
            sql = "CREATE TABLE admin_tbl (id INTEGER not NULL, admin_name VARCHAR(45) not NULL,admin_email VARCHAR(45) not NULL, admin_pass VARCHAR(45) not NULL,PRIMARY KEY ( id ))";
            stm.executeUpdate(sql);
            System.out.println("Admin table Created...");

            sql = "CREATE TABLE billing_tbl ( bill_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "invoice_id int(11) NOT NULL,"
                    + "customer_name varchar(30) NOT NULL,"
                    + "phone varchar(13) NOT NULL,"
                    + "product_name varchar(30) NOT NULL,"
                    + "weight varchar(30) NOT NULL,"
                    + "category varchar(30) NOT NULL,"
                    + "quantity int(11) NOT NULL,"
                    + "unit_price double NOT NULL,"
                    + "total_price double NOT NULL,"
                    + "date text NOT NULL,"
                    + "PRIMARY KEY (bill_id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Billing table Created...");

            sql = "CREATE TABLE category_tbl ("
                    + "cat_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "cat_name varchar(30) NOT NULL,"
                    + "cat_desc text NOT NULL,"
                    + "PRIMARY KEY (cat_id)"
                    + ") ";
            stm.executeUpdate(sql);
            System.out.println("Category table Created...");

            sql = "CREATE TABLE product_tbl ("
                    + "product_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "product_name varchar(50) NOT NULL,"
                    + "weight double NOT NULL,"
                    + "unit varchar(20) NOT NULL,"
                    + "price double NOT NULL,"
                    + "category varchar(50) NOT NULL,"
                    + "stock int(11) NOT NULL,"
                    + "PRIMARY KEY (product_id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Product table Created...");

            sql = "CREATE TABLE seller_tbl ("
                    + "  seller_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "  seller_name varchar(30) NOT NULL,"
                    + "  seller_email varchar(30) DEFAULT NULL,"
                    + "  seller_password text NOT NULL,"
                    + "  PRIMARY KEY (seller_id)"
                    + ") ";
            stm.executeUpdate(sql);
            System.out.println("Seller table Created...");
            return true;
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
            // return false;
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            // return false;
        }
        return false;
    }

    public int getUseLicence(String licence) {
        sqls = "select count(licence) from clients_tbl where licence = '" + licence + "'";
        myServerDriver();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

            if (rs.next()) {
                LicenceClass.use_client = rs.getString(1);
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }

    public boolean insertOrUpdateClientUUID(String licence, String os, String uuid) {
        myServerDriver();
        sqls = "select * from clients_tbl where licence='" + licence + "' and uuid='" + uuid + "'";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (!rs.next()) {
                pprds = con.prepareStatement("insert into clients_tbl(licence,os,uuid) values(?,?,?)");
                pprds.setString(1, licence);
                pprds.setString(2, os);
                pprds.setString(3, uuid);
                pprds.executeUpdate();
                return true;
            } else {
                sqls = "update clients_tbl set os=? where licence='" + licence + "' and uuid='" + uuid + "'";
                pprds = con.prepareStatement(sqls);
                pprds.setString(1, os);
                pprds.executeUpdate();
                return true;
            }
        } catch (SQLException exp) {
            JOptionPane.showMessageDialog(null, exp);
            return false;
        }
    }

}
