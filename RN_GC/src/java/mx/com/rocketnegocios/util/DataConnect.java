package mx.com.rocketnegocios.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataConnect {
    
    public static Connection getConnection() throws ClassNotFoundException {
        Connection conn = null;
        try {
            InitialContext contex = new InitialContext();
            //DataSource dataSource = (DataSource) contex.lookup("java:app/GC_Produccion");
            //DataSource dataSource = (DataSource) contex.lookup("java:app/GC_Desarrollo");
            //DataSource dataSource = (DataSource) contex.lookup("java:app/GC_PROD");
            DataSource dataSource = (DataSource) contex.lookup("jdbc/GC_PROD");
            conn = dataSource.getConnection();//*/
            /*Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rn_gc_db_test?zeroDateTimeBehavior=convertToNull", "rngcusr", "rngcusr1");
            conn = DriverManager.getConnection("jdbc:mysql://conta-arc.rocketnegocios.com.mx:3306/rn_gc_db_prod", "rngcusrp", "Fctw967_");
            System.out.println("Conexion realizada");//*/
        } catch(SQLException | NamingException ex) {
            System.out.println("Login Error: " + ex);
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    } 
    
    public static void close (Connection con) {
        try {
            con.close();
            System.out.println("Conexión cerrada");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private DataConnect() {
    }
    
}
