/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis FM
 */
public class Conexion {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private String db= "src/database/farmacia_pv.sql";    

//Constructor de clase que se conecta a la base de datos SQLite 
    public Conexion()
    {
      try{
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:" + this.db );
         System.out.println("Conectado a la base de datos SQLite [ " + this.db + "]");
      }catch(Exception e){
      
          JOptionPane.showMessageDialog(null,  "Error en la base de datos SQLite [ " + this.db + "]\n"+e.getMessage());        
             
      }

    }
    
 public boolean execute(String query){
        boolean res=false;
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.execute();
            pstm.close();
            res=true;
            
         }catch(Exception e){
             JOptionPane.showMessageDialog(null,  "Error en la base de datos SQLite [ " + this.db + "]\n"+e.getMessage());        
             
        }
      return res;
}

public ResultSet select(String query){
     
    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);
    }
    catch (SQLException ex) {
        System.out.println(ex);
    }
        
     return resultSet; 
 }

  public void desconectar(){
        try {
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(*) FROM usuarios");
            
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("Desconectado de la base de datos [ " + this.db + "]");
        }
        catch (SQLException ex) {
            
        }
 }
  
  
  
  

    
}
