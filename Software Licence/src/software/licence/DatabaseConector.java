package software.licence;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DatabaseConector {
    //mysql

    static String driver = null;
    static String url = null;
    static String user = null;
    static String pass = null;
    Connection con;
    Statement stm;
    PreparedStatement pprds;
    String sqls;
    ResultSet rs = null;
    static String user_name = null;
    static String user_email = null;

    public void myDriver() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
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
            System.out.println("clss" + cnf);
            JOptionPane.showMessageDialog(null, cnf);
            return false;
        } catch (SQLException sqle) {
            System.out.println("sql" + sqle);
            JOptionPane.showMessageDialog(null, sqle);
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
//    boolean checkIdExist(String column_name, String table_name, String id) {
//        sqls = "select " + column_name + " from " + table_name + " where " + column_name + " = " + Integer.parseInt(id);
//        myDriver();
//        String str = null;
//        try {
//            stm = con.createStatement();
//            rs = stm.executeQuery(sqls);
//            while (rs.next()) {
//                str = rs.getString(1);
//            }
//            return id.equals(str);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//        return false;
//    }

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

    public void showSummaryData(ResultsModel rsm, String query) {

        myDriver();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(query);

        } catch (SQLException exp) {
            System.out.println(exp);
        }
        rsm.setResultSet(rs);

    }

    public void searchProduct(ResultsModel rsm, String tbl_name, String search) {
        if (!search.isEmpty()) {
            myDriver();
            if (tbl_name.equals("product_tbl")) {

                //sqls = "select * from " + tbl_name + "where id like '%" + search + "%' or product_name like '%" + search + "%' or product_code like '%" + search + "%' or status like '%" + search + "%' or database_support like '%" + search + "%' or licence_duration like '%" + search + "%' or unit like '%" + search + "%' or max_client like '%" + search + "%' or price";
                sqls = "select * from " + tbl_name + " where id like '% " + search + " %' or product_name like '% " + search + " %'";
            }

            try {
                stm = con.createStatement();
                rs = stm.executeQuery(sqls);

            } catch (SQLException exp) {
                System.out.println(exp);
            }
            rsm.setResultSet(rs);
        }
    }

//    public void showTopProduct(ResultsModel rsm) {
//
//        myDriver();
//        sqls = "select product_name as 'Product Name', unit_price as 'Price(TK)', sum(quantity) as 'Total Sell' , sum(total_price) as 'Total Price(TK)' from billing_tbl group by product_name order by sum(quantity) desc limit 5";
//        try {
//            stm = con.createStatement();
//            rs = stm.executeQuery(sqls);
//
//        } catch (SQLException exp) {
//            System.out.println(exp);
//        }
//        rsm.setResultSet(rs);
//
//    }
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
//    public void filterDataTest(ResultsModel rsm, String tbl_name, String column, String filter) {
//
//        myDriver();
//        sqls = "select * from product_tbl where category = '" + filter + "'";
//        try {
//            stm = con.createStatement();
//            rs = stm.executeQuery(sqls);
//            //System.out.println(rs);
//        } catch (SQLException exp) {
//            System.out.println(exp);
//        }
//        rsm.setResultSet(rs);
//
//    }

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

    void loadProductInformation(String name, JLabel product_name, JLabel product_code, JLabel db_support, JLabel duration, JLabel max_client, JLabel price) {
        if (!name.isEmpty()) {
            myDriver();
            try {

                stm = con.createStatement();

                rs = stm.executeQuery("select * from product_tbl where product_name='" + name + "'");

                if (rs.next()) {
                    product_name.setText(rs.getString("product_name"));
                    product_code.setText(rs.getString("product_code"));
                    db_support.setText(rs.getString("database_support"));
                    duration.setText(rs.getInt("licence_duration") + " " + rs.getString("unit"));
                    max_client.setText(rs.getInt("max_client") + "");
                    price.setText(rs.getDouble("price") + "");

                }
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null, sqle);
            }
        }
        //  value=[];
    }

    void insertOrUpdateAdmin(String name, String pass) {

        myDriver();
        sqls = "select * from admin_tbl where email='" + user_email + "'";
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
                sqls = "update admin_tbl set name=?, password=? where email='" + user_email + "'";
                pprds = con.prepareStatement(sqls);
                pprds.setString(1, name);
                pprds.setString(2, pass);
                pprds.executeUpdate();
                JOptionPane.showMessageDialog(null, "Admin successfully updated");
            }
        } catch (SQLException exp) {
            System.out.println(exp);
        }

    }

    void getUserDetails(JTextField name, JTextField email, JTextField pass) {
        myDriver();
        sqls = "select name,email,password from admin_tbl where email='" + user_email + "'";

        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            if (!rs.next()) {
                name.setText("");
                email.setText("");
                pass.setText("");
            } else {
                name.setText(rs.getString(1));
                email.setText(rs.getString(2));
                pass.setText(rs.getString(3));

                //pass.setText(new Hash().decrypt(rs.getString(2)));
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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return "";
    }

    Double getRenewPrice(String product_code) {
        myDriver();
        sqls = "Select renew_price from product_tbl where product_code='" + product_code + "'";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sqls);
            while (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0.0;
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

    public Boolean createTable(JPanel dataDiologuePane, JTextArea show) {
        try {

            Class.forName(driver);
            myDriver();
            stm = con.createStatement();
            String sql = null;

            sql = "CREATE TABLE customer_tbl ( id int(11) NOT NULL AUTO_INCREMENT,"
                    + "first_name varchar(30) NOT NULL,"
                    + "last_name varchar(30) NOT NULL,"
                    + "email varchar(30) NOT NULL,"
                    + "product varchar(30) NOT NULL,"
                    + "product_code text NOT NULL,"
                    + "database_support varchar(30) NOT NULL,"
                    + "max_client varchar(30) NOT NULL,"
                    + "price double NOT NULL,"
                    + "licence varchar(250) NOT NULL,"
                    + "purchase_date text NOT NULL,"
                    + "expired_date varchar(30) NOT NULL,"
                    + "duration varchar(30) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            //System.out.println("Billing table Created...");
            show.append("Customer table Created...\n");
            sql = "CREATE TABLE licence_renew_tbl ("
                    + "  id int(11) NOT NULL,"
                    + "  customer_id int(11) NOT NULL,"
                    + "  licence_expire_date varchar(30) NOT NULL,"
                    + "  renew_date varchar(30) NOT NULL,"
                    + "  renew_expired_date varchar(30) NOT NULL,"
                    + "  renew_price double NOT NULL DEFAULT 0,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            //System.out.println("Category table Created...");
            show.append("Licence Renew table Created...\n");
            sql = "CREATE TABLE product_tbl ("
                    + "id int(11) NOT NULL AUTO_INCREMENT,"
                    + "product_name varchar(50) NOT NULL,"
                    + "product_code text NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "database_support varchar(30) NOT NULL,"
                    + "licence_duration int(11) NOT NULL,"
                    + "unit varchar(30) NOT NULL,"
                    + "max_client int(11) NOT NULL,"
                    + "price double NOT NULL,"
                    + "renew_price double NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            //System.out.println("Product table Created...");
            show.append("Product table Created...\n");
            sql = "CREATE TABLE clients_tbl ("
                    + "  id int(11) NOT NULL AUTO_INCREMENT,"
                    + "  licence text NOT NULL,"
                    + "  os varchar(30) DEFAULT NULL,"
                    + "  uuid text NOT NULL,"
                    + "  PRIMARY KEY (id)"
                    + ") ";
            stm.executeUpdate(sql);
            //System.out.println("Seller table Created...");
            show.append("Clients table Created...\n");
            sql = "CREATE TABLE customer_db_tbl ("
                    + "  id int(11) NOT NULL,"
                    + "  licence varchar(250) NOT NULL,"
                    + "  db_name varchar(250) NOT NULL,"
                    + "  PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            //System.out.println("Seller table Created...");
            show.append("Customer database table Created...\n");
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

    public Boolean createDatabase(String dri, String urls, String users, String passwords, String dbnames) {
        // url = "jdbc:mysql://localhost:3306";
        try {
            byte[] array = new byte[7]; // length is bounded by 7
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            System.out.println(generatedString);
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
            sql = "CREATE TABLE admin_tbl (id INTEGER not NULL, name VARCHAR(45) not NULL,email VARCHAR(45) not NULL, password VARCHAR(45) not NULL,PRIMARY KEY ( id ))";
            stm.executeUpdate(sql);
            System.out.println("Admin table Created...");

            sql = "CREATE TABLE customer_tbl ( id int(11) NOT NULL AUTO_INCREMENT,"
                    + "first_name varchar(30) NOT NULL,"
                    + "last_name varchar(30) NOT NULL,"
                    + "email varchar(30) NOT NULL,"
                    + "product varchar(30) NOT NULL,"
                    + "product_code text NOT NULL,"
                    + "database_support varchar(30) NOT NULL,"
                    + "max_client varchar(30) NOT NULL,"
                    + "price double NOT NULL,"
                    + "licence varchar(250) NOT NULL,"
                    + "purchase_date text NOT NULL,"
                    + "expired_date varchar(30) NOT NULL,"
                    + "duration varchar(30) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Customer table Created...");

            sql = "CREATE TABLE licence_renew_tbl ("
                    + "  id int(11) NOT NULL,"
                    + "  customer_id int(11) NOT NULL,"
                    + "  licence_expire_date varchar(30) NOT NULL,"
                    + "  renew_date varchar(30) NOT NULL,"
                    + "  renew_expired_date varchar(30) NOT NULL,"
                    + "  renew_price double NOT NULL DEFAULT 0,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Licence renew table Created...");

            sql = "CREATE TABLE product_tbl ("
                    + "id int(11) NOT NULL AUTO_INCREMENT,"
                    + "product_name varchar(50) NOT NULL,"
                    + "product_code text NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "database_support varchar(30) NOT NULL,"
                    + "licence_duration int(11) NOT NULL,"
                    + "unit varchar(30) NOT NULL,"
                    + "max_client int(11) NOT NULL,"
                    + "price double NOT NULL,"
                    + "renew_price double NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Product table Created...");

            sql = "CREATE TABLE clients_tbl ("
                    + "  id int(11) NOT NULL AUTO_INCREMENT,"
                    + "  licence text NOT NULL,"
                    + "  os varchar(30) DEFAULT NULL,"
                    + "  uuid text NOT NULL,"
                    + "  PRIMARY KEY (id)"
                    + ") ";
            stm.executeUpdate(sql);
            System.out.println("Client table Created...");
            sql="CREATE TABLE customer_db_tbl ("
                    + "  id int(11) NOT NULL,"
                    + "  licence varchar(250) NOT NULL,"
                    + "  db_name varchar(250) NOT NULL,"
                    + "  PRIMARY KEY (id)"
                    + ")";
            stm.executeUpdate(sql);
            System.out.println("Customer database table Created...");
            return true;
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
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
            System.out.println("Import data Created...");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void importDemo(JPanel dataDiologuePane, JTextArea show) {
        myDriver();

        try {
            String[] sql = {
                // "INSERT INTO admin_tbl VALUES(1,'Helal Uddin','helal@gmail.com','123')",
                "INSERT INTO `customer_tbl` (`id`, `first_name`, `last_name`, `email`, `product`, `product_code`, `database_support`, `max_client`, `price`, `licence`, `purchase_date`, `expired_date`, `duration`) VALUES\n"
                + "(1, 'Jhon', 'Deo', 'jhon@yopmail.com', 'Super Shop with Diomond', 'MYEORPCB7S5RNC2JWC', 'Yes', '10', 5000, '5D31C4-9B6E24-5CAC5F-AD6768-839ED5-11EE0D-D973', '2022-08-07', '2030-08-07', '1 Year'),\n"
                + "(2, 'Carry', 'Hood', 'hood@yopmail.com', 'Super Shop with Gold', '55AO4U7FBAJ7BERBVJ', 'Yes', '5', 3000, '2D6B1B-3FE0D9-FDBA8B-F80206-2B1671-C30264-CB44', '2022-08-07', '2025-08-07', '1 Year'),\n"
                + "(3, 'Alex', 'Curry', 'curry@yopmail.com', 'Super Shop with Silver', 'DYI098RTFJ8ES96179', 'No', '3', 2000, '30DD2E-69CB9D-CA49C2-BB6954-8EF2E2-5D3028-2BFA', '2022-08-07', '2022-08-10', '1 Days'),\n"
                + "(4, 'Jon', 'Hels', 'hels@yopmail.com', 'Super Shop with Silver', 'DYI098RTFJ8ES96179', 'No', '3', 2000, '8E4160-E96DB6-3E01D0-CB95DB-7F7A84-9534A4-2A49', '2022-08-07', '2023-08-07', '1 Year')",
                "INSERT INTO `licence_renew_tbl` (`id`, `customer_id`, `licence_expire_date`, `renew_date`, `renew_expired_date`, `renew_price`) VALUES\n"
                + "(1, 1, '2024-08-07', '2022-08-07', '2026-08-07', 1000),\n"
                + "(2, 1, '2026-08-07', '2022-08-07', '2028-08-07', 1000),\n"
                + "(3, 1, '2028-08-07', '2022-08-07', '2029-08-07', 1000),\n"
                + "(4, 1, '2029-08-07', '2022-08-07', '2030-08-07', 1000),\n"
                + "(5, 3, '2022-08-08', '2022-08-07', '2022-08-09', 500),\n"
                + "(6, 3, '2022-08-09', '2022-08-07', '2022-08-10', 500),\n"
                + "(7, 2, '2024-08-07', '2022-08-07', '2025-08-07', 700)",
                "INSERT INTO `product_tbl` (`id`, `product_name`, `product_code`, `status`, `database_support`, `licence_duration`, `unit`, `max_client`, `price`, `renew_price`) VALUES\n"
                + "(1, 'Super Shop with Diomond', 'MYEORPCB7S5RNC2JWC', 'Active', 'Yes', 1, 'Year', 10, 5000, 1000),\n"
                + "(2, 'Super Shop with Gold', '55AO4U7FBAJ7BERBVJ', 'Active', 'Yes', 1, 'Year', 5, 3000, 700),\n"
                + "(3, 'Super Shop with Silver', 'DYI098RTFJ8ES96179', 'Active', 'No', 1, 'Year', 3, 2000, 500)"
            };
            //sqls="INSERT INTO 'admin_tbl' VALUES(1,'Helal Uddin','helal@gmail.com','123')";
            stm = con.createStatement();
            String[] tbl = {"Customer Table", "Licence Renew Table", "Product Table"};

            for (int i = 0; i < sql.length; i++) {
                stm.executeUpdate(sql[i]);
                show.append(tbl[i] + " data import successfully...\n");
            }
            show.append("Demo import Successfully...\n");
            JOptionPane.showMessageDialog(null, "Demo import Successfully");

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
