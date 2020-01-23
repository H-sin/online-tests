package server.DAOs;

import models.Admin;
import models.Etudiant;
import util.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public static Connection conn = DataSource.getInstance().getConnection();

    public static Response login(Admin admin) {
        try {
            PreparedStatement statement = conn.prepareStatement("select * from Admin where login=? and password=?");
            statement.setString(1, admin.getLogin());
            statement.setString(2, admin.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin fullAdmin = new Admin();
                fullAdmin.setLogin(resultSet.getString("login"));
                fullAdmin.setNom(resultSet.getString("nom"));
                fullAdmin.setPrenom(resultSet.getString("prenom"));
                fullAdmin.setPassword(resultSet.getString("password"));
                return new Response(fullAdmin);
            } else {
                return new Response(1, "Wrong information");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Response(1, "SQL error");
        }
    }

    public static Response update(Admin oldAdmin,Admin newAdmin)
    {
        try{
            PreparedStatement pst =conn.prepareStatement("update admins set nom=?,prenom=?,password=?,login=? where login=?;");
            pst.setString(1, newAdmin.getNom());
            pst.setString(2, newAdmin.getPrenom());
            pst.setString(3, newAdmin.getPassword());
            pst.setString(4, newAdmin.getLogin());
            pst.setString(5, oldAdmin.getLogin());
            if(pst.executeUpdate()!=0) {
                System.out.println("Admin updated : " + newAdmin.getPrenom());
                return new Response(0, "You are Updated :" + newAdmin.getPrenom());
            }
            else
            {
                return new Response(1,"Admin doesn't exist");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Response(1, "SERVER DB ERROR :"+e.getMessage());
        }
    }
}

