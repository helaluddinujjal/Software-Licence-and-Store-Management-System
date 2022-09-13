package supermarket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DatabaseConector {
    //mysql

//    static String soft_driver = "";
//    static String soft_url = "";
//    static String soft_user = "";
//    static String soft_pass = "";
    static String driver = null;
    static String url = null;
    static String user = null;
    static String pass = null;
    Connection con = null;
    Statement stm = null;
    PreparedStatement pprds;
    String sqls;
    ResultSet rs = null;
    static String user_name = null;
    static String user_email = null;
    static String user_role = null;

    public DatabaseConector() {

        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //end
    public Boolean isMySQLVersion() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            con.close();
            return true;

        } catch (ClassNotFoundException cnf) {
            System.out.println("class: " + cnf);
            return true;
        } catch (SQLException sqle) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("sql: " + sqle);
            return false;
        }
    }

    public File[] searchFileName(String path, String name) {
        File root = new File(path);
        FilenameFilter beginswithm = new FilenameFilter() {
            public boolean accept(File directory, String filename) {
                return filename.startsWith(name);
            }
        };

        File[] files = root.listFiles(beginswithm);
        //System.out.println(files.length);
        for (File f : files) {
            System.out.println(f);
        }
        return files;
    }

    public Boolean deleteFile(String name) {
        File f = new File(name);           //file to be delete  
        if (f.delete()) //returns Boolean value  
        {
            //  System.out.println(f.getName() + " deleted");   //getting and printing the file name  
            return true;
        } else {
            //System.out.println("failed");
            return false;
        }
    }

    public boolean jdbcChange(String name) {
        DatabaseConector db = new DatabaseConector();
        File[] fileCopy = db.searchFileName("src/packageFolder/", name);
        if (fileCopy.length > 0) {
            for (File fc : fileCopy) {
                try {
                    File[] files = db.searchFileName("lib/", "mysql-connector-java");
                    if (files.length > 0) {
                        for (File f : files) {
                            //System.out.println(f.getName());
                            db.deleteFile(f.toString());
                            //System.out.println("deleted");
                            JOptionPane.showMessageDialog(null, "deleted" + f.getName());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "file not found in lib folder");
                    }
                    copyFileUsingStream(fc, new File("lib/" + fc.getName()));
                    return true;

                } catch (IOException ex) {
                    Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("deleted");
            }
        } else {
            JOptionPane.showMessageDialog(null, "file not found in package folder");
        }
        return false;
    }

    public void mysqlVersionConnected() {
        DatabaseConector db = new DatabaseConector();
        if (db.isMySQLVersion() == false) {
            File[] fileCopy = db.searchFileName("src/packageFolder/", "mysql-connector-java");
            if (fileCopy.length > 0) {
                for (File fc : fileCopy) {
                    try {
                        File[] files = db.searchFileName("lib/", "mysql-connector-java");
                        if (files.length > 0) {
                            for (File f : files) {
                                if (!fc.getName().equals(f.getName())) {

                                    copyFileUsingStream(fc, f);
                                    File rename = new File(f.getParent(), f.getName());
                                    boolean check = f.renameTo(rename);
                                    if (check == false) {
                                        JOptionPane.showMessageDialog(null, "File can' be renamedt");
                                    }
                                    break;
                                }

//                                if (db.isMySQLVersion()) {
//                                    JOptionPane.showMessageDialog(null, "connected");
//                                    break;
//                                } else {
//                                    JOptionPane.showMessageDialog(null, "not connected");
//                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "file not found in lib folder");
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("deleted");
                }
            } else {
                JOptionPane.showMessageDialog(null, "file not found in package folder");
            }
        }
    }

    public static void main(String[] args) {

//        File folder = new File("src/packageFolder/");
//        File[] listOfFiles = folder.listFiles();
//        System.out.println(folder.listFiles());
//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                System.out.println("File " + listOfFiles[i].getName());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
//        }
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            File src = new File("dist/lib/mysql-connector-java-8.0.29");
//            File dest = new File("src/packageFolder/mysql-connector-java-5.1.23-bin");
//            // Files.copy(src.toFile(), dest.toFile());
//            copyFileUsingStream(src, dest);
//            System.out.println(new File("src/packageFolder/").list().length);
//        } catch (Exception ex) {
//            // handle the error
//            System.out.println(ex);
//
//        }
    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            JOptionPane.showMessageDialog(null, "copy success");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "copy failed: " + e);
        }
    }

    public void myDriver() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException cnf) {
            //System.out.println(cnf);
            JOptionPane.showMessageDialog(null, cnf);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            mysqlVersionConnected();
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
            // mysqlVersionConnected();
            return false;
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
                        JOptionPane.showMessageDialog(null, "the database " + dbName + " exists");
                        System.out.println("the database " + dbName + " exists");
                         return false;
                    }
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "unable to create database connection");
                return false;
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            // mysqlVersionConnected();
            return false;
        }
    }

    public void executeQueryData(String query, String message) {
        myDriver();

        try {
            stm = con.createStatement();
            System.out.println(con);
            stm.executeUpdate(query);
            if (!message.isEmpty()) {
                JOptionPane.showMessageDialog(null, message);
            }

        } catch (SQLException sqles) {
            AddAdmin.status = false;
            JOptionPane.showMessageDialog(null, sqles);
        }
    }

    public String getTotal(boolean distinct_col, String func, String column_name, String tbl_name) {
        myDriver();

        try {
            if (distinct_col) {
                sqls = "Select " + func + "(distinct " + column_name + ") from " + tbl_name + "";
                //sqls="select  count(distinct customer_name) from billing_tbl";
            } else {
                sqls = "Select " + func + "(" + column_name + ") from " + tbl_name + "";
            }

            stm = con.createStatement();
            System.out.println(con);
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                return String.valueOf(rs.getInt(1));
            }

        } catch (SQLException sqles) {
            JOptionPane.showMessageDialog(null, sqles);

        }
        return "0";

    }

    boolean checkIdExist(String column_name, String table_name, String id) {
        sqls = "select " + column_name + " from " + table_name + " where " + column_name + " = " + Integer.parseInt(id);
        myDriver();
        String str = null;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                str = rs.getString(1);
            }
            return id.equals(str);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }

    public void showData(ResultsModel rsm, String tbl_name) {

        myDriver();
        sqls = "select * from " + tbl_name;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    public void showTopProduct(ResultsModel rsm) {

        myDriver();
        sqls = "select product_name as 'Product Name', unit_price as 'Price(TK)', sum(quantity) as 'Total Sell' , sum(total_price) as 'Total Price(TK)' from billing_tbl group by product_name order by sum(quantity) desc limit 5";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    public void filterData(ResultsModel rsm, String tbl_name, String column, String filter) {

        myDriver();
        sqls = "select * from " + tbl_name + " where " + column + " = '" + filter + "'";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            //System.out.println(rs);
        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    public void filterDataTest(ResultsModel rsm, String tbl_name, String column, String filter) {

        myDriver();
        sqls = "select * from product_tbl where category = '" + filter + "'";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            //System.out.println(rs);
        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    public String getNewId(String column_name, String table_name) {
        myDriver();
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

    void getDataByColumn(JComboBox category, boolean distnc, boolean itemRemove, String column_name, String table_name) {
        myDriver();
        try {
            if (itemRemove) {
                category.removeAllItems();
            }

            stm = con.createStatement();
            if (distnc) {
                rs = stm.executeQuery("select DISTINCT " + column_name + " from " + table_name);
            } else {
                rs = stm.executeQuery("select " + column_name + " from " + table_name);
            }

            while (rs.next()) {
                category.addItem(rs.getString(column_name));
            }

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }

    void insertOrUpdateAdmin(String name, String pass) {

        myDriver();
        if (user_role != null && user_role.equals("Admin")) {
            sqls = "select * from admin_tbl where id=1";
            try {
                stm = con.createStatement();
                rs = stm.executeQuery(sqls);
                if (!rs.next()) {
                    pprds = con.prepareStatement("insert into admin_tbl values(?,?,?)");
                    pprds.setInt(1, 1);
                    pprds.setString(2, name);
                    pprds.setString(3, pass);
                    pprds.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Admin created successfully");
                } else {
                    sqls = "update admin_tbl set id=?,admin_name=?, admin_pass=? where id=" + 1;
                    pprds = con.prepareStatement(sqls);
                    pprds.setInt(1, 1);
                    pprds.setString(2, name);
                    pprds.setString(3, pass);
                    pprds.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Admin successfully updated");
                }
            } catch (SQLException exp) {
                System.out.println(exp);
            }
        } else {
            sqls = "update seller_tbl set seller_name=?, seller_password=? where seller_email='" + user_email + "'";
            try {
                Hash hash = new Hash();
                pprds = con.prepareStatement(sqls);
                pprds.setString(1, name);
                pprds.setString(2, hash.encrypt(pass));
                pprds.executeUpdate();
                JOptionPane.showMessageDialog(null, "Account successfully updated");
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    void getUserDetails(JTextField name, JTextField pass) {
        myDriver();
        if (user_role != null && user_role.equals("Admin")) {
            sqls = "select admin_name,admin_pass from admin_tbl where id=1";
        } else {
            sqls = "select seller_name,seller_password from seller_tbl where seller_email='" + user_email + "'";
            System.out.println(user_email);
        }

        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (!rs.next()) {
                name.setText("");
                pass.setText("");
            } else {
                name.setText(rs.getString(1));

                if (user_role != null && user_role.equals("Admin")) {
                    pass.setText(rs.getString(2));
                }

                pass.setText(new Hash().decrypt(rs.getString(2)));
            }
        } catch (SQLException exp) {
            System.out.println(exp);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String checkAuth(String sqls) {

        myDriver();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                return rs.getString(2);
            }
            System.out.println(rs.next());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return "";
    }

    String[] invGetNameInf(int id) {
        myDriver();
        String[] data = new String[4];
        sqls = "select invoice_id,customer_name,phone,date,sum(total_price) as total from billing_tbl where invoice_id=" + id;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                data[0] = rs.getString("customer_name");
                data[1] = rs.getString("phone");
                data[2] = rs.getString("invoice_id");
                data[3] = rs.getString("date");
                return data;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return data;
    }

    public void invGetProductInf(ResultsModel rsm, int id) {

        myDriver();
        sqls = "select product_name as 'Product Name',weight as 'Weight',quantity as 'Qty',unit_price as 'Price' from billing_tbl where invoice_id=" + id;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);

        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    String invTotalPrice(int id) {
        myDriver();
        String data = "";
        sqls = "select sum(total_price) as total from billing_tbl where invoice_id=" + id;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                data = rs.getString("total");
                return data;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return data;
    }

    public Boolean createDatabase(String dri, String urls, String users, String passwords, String dbnames) {
        // url = "jdbc:mysql://localhost:3306";
        try {
//            byte[] array = new byte[7]; // length is bounded by 7
//            int leftLimit = 48; // numeral '0'
//            int rightLimit = 122; // letter 'z'
//            int targetStringLength = 10;
//            Random random = new Random();
//            String generatedString = random.ints(leftLimit, rightLimit + 1)
//                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//
//            System.out.println(generatedString);
            Class.forName(dri);
            con = DriverManager.getConnection(urls, users, passwords);
            stm = con.createStatement();
            // String sql = "CREATE DATABASE SuperShop_" + generatedString;
            String sql = "CREATE DATABASE " + dbnames;
            stm.executeUpdate(sql);
            System.out.println("Database create");
            url = urls + dbnames;
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

    Boolean dropDBTable(JPanel dataDiologuePane, JTextArea show) {
        try {
            myDriver();
            stm = con.createStatement();
            //Retrieving the data
            ResultSet rs = stm.executeQuery("Show tables");
            String arr;
            int i = 0;
            while (rs.next()) {
                if (!rs.getString(1).equals("admin_tbl")) {
                    arr = "drop table " + rs.getString(1);
                    stm = con.createStatement();
                    stm.executeUpdate(arr);
                    show.append(rs.getString(1) + " is deleted\n");
                    //JLabel lab=new JLabel(rs.getString(1)+" is deleted\n");
                    // lab.setVisible(true);
                    // lab.setBounds(30, 300, 200, 30);
                    // dataDiologuePane.add(lab);
                }

            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void importAdmin() {
        sqls = "INSERT INTO admin_tbl VALUES(1,'Helal Uddin','helal@gmail.com','123')";

        myDriver();
        //sqls = "select sum(total_price) as total from billing_tbl where invoice_id=" + id;
        try {
            stm = con.createStatement();
            stm.executeUpdate(sqls);
            System.out.println("Import Admin data Created...");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Boolean createTable(JPanel dataDiologuePane, JTextArea show) {
        try {

            Class.forName(driver);
            myDriver();
            stm = con.createStatement();
            String sql = null;

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
            //System.out.println("Billing table Created...");
            show.append("Billing table Created...\n");
            sql = "CREATE TABLE category_tbl ("
                    + "cat_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "cat_name varchar(30) NOT NULL,"
                    + "cat_desc text NOT NULL,"
                    + "PRIMARY KEY (cat_id)"
                    + ") ";
            stm.executeUpdate(sql);
            //System.out.println("Category table Created...");
            show.append("Category table Created...\n");
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
            //System.out.println("Product table Created...");
            show.append("Product table Created...\n");
            sql = "CREATE TABLE seller_tbl ("
                    + "  seller_id int(11) NOT NULL AUTO_INCREMENT,"
                    + "  seller_name varchar(30) NOT NULL,"
                    + "  seller_email varchar(30) DEFAULT NULL,"
                    + "  seller_password text NOT NULL,"
                    + "  PRIMARY KEY (seller_id)"
                    + ") ";
            stm.executeUpdate(sql);
            //System.out.println("Seller table Created...");
            show.append("Seller table Created...\n");
            return true;
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
            JOptionPane.showMessageDialog(null, cnf);
            // return false;
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            // return false;
        }
        return false;
    }

    void importDemo(JPanel dataDiologuePane, JTextArea show) {
        myDriver();

        try {
            String[] sql = {
                //"INSERT INTO admin_tbl VALUES(1,'Helal Uddin','helal@gmail.com','123')",
                "INSERT INTO `billing_tbl` VALUES\n"
                + "(1,2,'jon','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(2,3,'jon','0155555555','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),\n"
                + "(3,4,'sara','0155555555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),\n"
                + "(4,5,'sara','112143254','Dove Soap','100 g','Soap',3,30,90,'2022/05/09'),\n"
                + "(5,6,'jhon','214314325','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),\n"
                + "(6,7,'alex','1141241241','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(7,8,'alex','1141241241','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),\n"
                + "(8,9,'jessi','0123432555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),\n"
                + "(9,1,'jessi','0123432555','Dove Soap','100 g','Soap',2,30,60,'2022/05/09'),\n"
                + "(10,1,'jessi','0123432555','Vanila','100 g','Ice  Cream',2,200,400,'2022/05/09'),\n"
                + "(11,10,'jimi','0155555555','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),\n"
                + "(12,10,'jimi','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(13,10,'jimi','0155555555','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(14,11,'joy','04555554545','Snsilk Shampoo','100 g','Shampoo',1,200,200,'2022/05/09'),\n"
                + "(15,11,'joy','04555554545','dove','100 g','Soap',1,45.54,45.54,'2022/05/09'),\n"
                + "(16,11,'joy','04555554545','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),\n"
                + "(17,11,'joy','04555554545','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(18,12,'yuvi','06656556656','Vanila','100 g','Ice  Cream',1,200,200,'2022/05/09'),\n"
                + "(19,12,'yuvi','06656556656','Dove Soap','100 g','Soap',1,30,30,'2022/05/09'),\n"
                + "(20,13,'Helal','456546546','Dove Soap','50g','Ice  Cream',1,200,200,'2022/05/10'),\n"
                + "(21,13,'Helal','56546546','Dove Soap','75g','Ice  Cream',1,200,200,'2022/05/10'),\n"
                + "(22,14,'hell','534656','Vanila','500.0kg','Ice  Cream',1,200,200,'2022/05/10'),\n"
                + "(23,14,'hell','534656','Vanila','500.0kg','Ice  Cream',1,200,200,'2022/05/10')",
                "INSERT INTO `category_tbl` VALUES\n"
                + "(1,'Ice  Cream','descriptions here...............'),\n"
                + "(2,'Soap','The lorem ipsum is a placeholder text used in publishing and graphic design. This filler text is a short paragraph that contains all the letters of the alphabet.'),\n"
                + "(3,'Shampoo','The lorem ipsum is a placeholder text used in publishing and graphic design. This filler text is a short paragraph that contains all the letters of the alphabet. The characters are spread out evenly so that the readers attention is focused on the layout of the text instead of its content.')",
                "INSERT INTO `product_tbl` VALUES\n"
                + "(1,'dove',80,'Gram',45.54,'Soap',0),\n"
                + "(2,'Snsilk Shampoo',1,'Litter',200,'Shampoo',1),\n"
                + "(3,'Dove Soap',50,'kg',30,'Soap',36),\n"
                + "(4,'Vanila',500,'kg',200,'Ice  Cream',29)",
                "INSERT INTO `seller_tbl` VALUES\n"
                + "(1,'Helal','helel@gmail.com','c3n5+3QXnFI='),\n"
                + "(2,'Asif Awlad','asif@gmail.com','c3n5+3QXnFI='),\n"
                + "(3,'Demo','demo@gmail.com','c3n5+3QXnFI=')",};
            //sqls="INSERT INTO 'admin_tbl' VALUES(1,'Helal Uddin','helal@gmail.com','123')";
            stm = con.createStatement();
            String[] tbl = {"Billing Table", "Category Table", "Product Table", "Seller Table"};

            for (int i = 0; i < sql.length; i++) {
                stm.executeUpdate(sql[i]);
                show.append(tbl[i] + " data import successfully...\n");
            }
            JOptionPane.showMessageDialog(null, "Demo import Successfully\n");

        } catch (SQLException sqles) {
            JOptionPane.showMessageDialog(null, sqles);
        }
    }

    public boolean checkAdmin() {
        myDriver();
        //sqls = "SELECT LAST_INSERT_ID() as lastId FROM category_tbl";
        sqls = "SELECT * FROM admin_tbl";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (rs.next()) {
                System.out.println(rs.next());
                return true;
            } else {
                return false;
            }

        } catch (SQLException exp) {
            System.out.println(exp);

        }
        System.out.println("false");
        return false;
    }
}
