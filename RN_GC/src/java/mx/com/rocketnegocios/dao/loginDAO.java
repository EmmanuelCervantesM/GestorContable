package mx.com.rocketnegocios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.com.rocketnegocios.util.DataConnect;

public class loginDAO {

    public static boolean validate(String user, String password) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("<--- Validate --->");
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select NombreCompleto from rn_gc_usuarios_tbl where usuarioClave = ? and Contrasenia = ?");
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error ---> " + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
   

    public static String getNombreUsuario(String user, String password) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("<--- getNombreUsuario --->");

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select NombreCompleto from rn_gc_usuarios_tbl where usuarioClave = ? and Contrasenia = ?");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Login error ---> " + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
        return null;
    }

    public static int getUsuarioId(String user) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("<--- getUsuarioId ---> " + user);

        try {            
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select Id from rn_gc_usuarios_tbl where usuarioClave = ?");
            ps.setString(1, user);
            System.out.println("Datos: " + ps);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Login error ---> " + ex.getMessage());
            return 0;
        } finally {
            DataConnect.close(con);
        }
        return 0;
    }

}
