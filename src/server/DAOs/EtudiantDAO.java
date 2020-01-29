package server.DAOs;

import models.Etudiant;

import java.sql.*;
import java.util.ArrayList;

/* This class handles the actions coming from the client,
    and all classes who interact with database are in this ActionHandlers Package */

public class EtudiantDAO {
    public static Connection conn = DataSource.getInstance().getConnection();

    public static Etudiant login(String username, String password) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from etudiants where username=? and password=?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Etudiant fullEtudiant = new Etudiant();
            fullEtudiant.setCNE(resultSet.getString("CNE"));
            fullEtudiant.setIdGroupe(resultSet.getInt("id_groupe"));
            fullEtudiant.setNom(resultSet.getString("nom"));
            fullEtudiant.setPrenom(resultSet.getString("prenom"));
            fullEtudiant.setUsername(resultSet.getString("username"));
            fullEtudiant.setPassword(resultSet.getString("password"));
            PreparedStatement statement2 = conn.prepareStatement("select * from groupes where id_groupe=?");
            statement2.setInt(1, fullEtudiant.getIdGroupe());
            ResultSet resultSet2 = statement2.executeQuery();
            resultSet2.next();
            fullEtudiant.setNomGroupe(resultSet2.getString("nom"));
            return fullEtudiant;
        } else {
             throw new SQLException("Wrong information");
        }

    }

    // add Etudiant:
    public static void add(Etudiant etudiant) throws SQLException {
        PreparedStatement statement =conn.prepareStatement(
                "insert into etudiants(cne,id_groupe,username,password,nom,prenom) values(?,?,?,?,?,?);"
        );
        statement.setString(1, etudiant.getCNE());
        statement.setInt(2, etudiant.getIdGroupe());
        statement.setString(3, etudiant.getUsername());
        statement.setString(4, etudiant.getPassword());
        statement.setString(5, etudiant.getNom());
        statement.setString(6, etudiant.getPassword());
        if(statement.executeUpdate()==0){
            throw new SQLException("Problem in adding etudian");
        }
        System.out.println("Etudiant Added !! ");

    }

    // delete Student by cne
    public static void delete(String cne) throws SQLException {
        PreparedStatement statement =conn.prepareStatement("delete from etudiants where cne=?;");
        statement.setString(1,cne);
        if(statement.executeUpdate()!=0)
        {
            System.out.println("Etudiant deleted : ");
        }
        else{
            throw new SQLException("Etudiant doesn't exist");
        }
    }

    // getAll Students
    public static ArrayList<Etudiant> getAllEtudiants() throws SQLException {
        ResultSet resultSet=null;
        ArrayList<Etudiant> etudiants=new ArrayList<Etudiant>();

            Statement statement=conn.createStatement();
            resultSet=statement.executeQuery("select * from etudiants;");
            while (resultSet.next())
            {
                Etudiant fullEtudiant = new Etudiant();
                fullEtudiant.setCNE(resultSet.getString("CNE"));
                fullEtudiant.setIdGroupe(resultSet.getInt("id_groupe"));
                fullEtudiant.setNom(resultSet.getString("nom"));
                fullEtudiant.setPrenom(resultSet.getString("prenom"));
                fullEtudiant.setUsername(resultSet.getString("username"));
                fullEtudiant.setPassword(resultSet.getString("password"));
                etudiants.add(fullEtudiant);
            }
            return etudiants;
        }

    // getAll Student in a given groupe
    public static ArrayList<Etudiant> getAllEtudiantsInGroupe(int id_grp) throws SQLException {
        ResultSet resultSet=null;
        ArrayList<Etudiant> etudiants=new ArrayList<Etudiant>();
            PreparedStatement statement =conn.prepareStatement("select * from etudiants where id_groupe=? ;");
            statement.setInt(1,id_grp);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setCNE(resultSet.getString("CNE"));
                etudiant.setIdGroupe(resultSet.getInt("id_groupe"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setUsername(resultSet.getString("username"));
                etudiant.setPassword(resultSet.getString("password"));
                etudiants.add(etudiant);
            }
            return etudiants;
    }

    // Search student by cne
    public static Etudiant getEtudiantById(String cne) throws SQLException {
        PreparedStatement statement =conn.prepareStatement("select * from etudiants where cne=? ;");
        statement.setString(1,cne);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Etudiant etudiant = new Etudiant();
            etudiant.setCNE(resultSet.getString("CNE"));
            etudiant.setIdGroupe(resultSet.getInt("id_groupe"));
            etudiant.setNom(resultSet.getString("nom"));
            etudiant.setPrenom(resultSet.getString("prenom"));
            etudiant.setUsername(resultSet.getString("username"));
            etudiant.setPassword(resultSet.getString("password"));
            return etudiant;
        } else {
            throw new SQLException("Etudiant not found");
        }
    }


    // update Student
    public static void update(String oldEtudiantCne, Etudiant newEtudiant) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "update etudiants set cne=?,id_groupe=?,username=?,password=?,nom=?,prenom=? where cne=?;"
        );
        Etudiant oldEtudiant = getEtudiantById(oldEtudiantCne);
        if (newEtudiant.getCNE() != null) {
            statement.setString(1, newEtudiant.getCNE());
        } else {
            statement.setString(1, oldEtudiant.getCNE());
        }

        if (newEtudiant.getIdGroupe() != 0) {
            statement.setInt(2, newEtudiant.getIdGroupe());
        } else {
            statement.setInt(2, oldEtudiant.getIdGroupe());
        }

        if (newEtudiant.getUsername() != null) {
            statement.setString(3, newEtudiant.getUsername());
        } else {
            statement.setString(3, oldEtudiant.getUsername());
        }

        if (newEtudiant.getPassword() != null) {
            statement.setString(4, newEtudiant.getPassword());
        } else {
            statement.setString(4, oldEtudiant.getPassword());
        }

        if (newEtudiant.getNom() != null) {
            statement.setString(5, newEtudiant.getNom());
        } else {
            statement.setString(5, oldEtudiant.getNom());
        }

        if (newEtudiant.getPrenom() != null) {
            statement.setString(6, newEtudiant.getPrenom());
        } else {
            statement.setString(6, oldEtudiant.getPrenom());
        }
        statement.setString(7, oldEtudiantCne);
        if (statement.executeUpdate() == 0) {
            throw new SQLException("Etudiant doesn't exist");
        }
    }
}
