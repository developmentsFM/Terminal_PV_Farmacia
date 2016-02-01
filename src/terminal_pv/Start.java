/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

String dateValue = resultset.getString(...); // What ever column
java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateValue);


 */
package terminal_pv;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.Conexion;
import info.Info;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Luis Fm
 */
public class Start extends javax.swing.JPanel {

    
    JPanel content;    
    String idU, nameU, userU, passU, roleU;    
    DefaultTableModel tabla1, tabla2, tabla3, tabla4, tabla5, tabla6, tabla7, tabla8; 
    int Tart= 0, idVenta =0;
    float Tventa = 0;
    boolean inPro = false;
    JLabel dateFecha;
    
    public Start(JPanel content, String datos[], JLabel fecha) {        
        
        this.dateFecha = fecha;
        
        
        
        this.content = content;
        idU=datos[0]; nameU=datos[1]; userU=datos[2]; passU=datos[3]; roleU=datos[4];
        
        if(idU == null) idU="0";
        
        initComponents();
        
        tabla2 = (DefaultTableModel) jTable2.getModel();
        tabla3 = (DefaultTableModel) jTable3.getModel();
        tabla4 = (DefaultTableModel) sustances.getModel();
        tabla5 = (DefaultTableModel) tablasustances.getModel();
        tabla6 = (DefaultTableModel) tabalMP.getModel();
        tabla7 = (DefaultTableModel) tablaUsuario.getModel();
        tabla8 = (DefaultTableModel) jTable1.getModel();
        
        grupo1 = new ButtonGroup();
        grupo1.add(jRadioButton1);
        grupo1.add(jRadioButton3);
        jRadioButton1.setSelected(true);
        
        setConfigTable();
        setIcon();
        setBox();
        setTableArt("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity, products.image FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
        
        setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
        setTableSus();
        setTableSustances("SELECT *FROM substances");
        setTableUser();
      
        
        contenedor.add(Venta);        
        setEnableContainer(Inventario, false);
        
        
        
        if(roleU.equals("1")){
           nameUser.setText("Administrador  ");
           nameUser2.setText("Administrador  ");
        }
        else{
            nameUser.setText(nameU+"  ");
            nameUser2.setText(nameU+"  ");            
        }
        
        if( roleU.equals("0")){        
            invent.setEnabled(false); invent.setVisible(false);
            config.setEnabled(false); config.setVisible(false);
        }
        
        loadVenta();   
       
       ((JTextField) Ccadu.getDateEditor().getUiComponent()).setEditable(false); 
                 
    }
    
    public void paint(Graphics g){
        codigo.requestFocusInWindow(); 
        super.paint(g);
    } 
    
    
    //Iconos en la interfaz 
    public void setIcon(){
                    
        ImageIcon icono = new ImageIcon ("src/iconos/cobrar.png");
        cobrar.setIcon(new ImageIcon(icono.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));        
        icono = new ImageIcon ("src/iconos/cancel.png");
        cancel.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        icono = new ImageIcon ("src/iconos/eliminar.png");
        delete.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        
        userEli.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        icono = new ImageIcon ("src/iconos/cotizacion.png");
        etc.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));        
        icono = new ImageIcon ("src/iconos/add2.png");
        search.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));        
              
        icono = new ImageIcon ("src/iconos/new.png");
        Bnew.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        userNew.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        agrega.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        icono = new ImageIcon ("src/iconos/salir.png");
        exit.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        
        icono = new ImageIcon ("src/iconos/cancel.png");
        Bcancel.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        cancela.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        icono = new ImageIcon ("src/iconos/edit.png");
        Bedit.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        userEdi.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        icono = new ImageIcon ("src/iconos/eliminar.png");
        Belimina.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        elimina.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        icono = new ImageIcon ("src/iconos/save.png");
        Bsave.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));  
        guarda.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        
        icono = new ImageIcon ("src/iconos/add.png");
        addM.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        addC.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));         
        addS.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH)));         
        
        icono = new ImageIcon ("src/iconos/venta.png");
        venta.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));        
        icono = new ImageIcon ("src/iconos/invent.png");
        invent.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        icono = new ImageIcon ("src/iconos/config.png");
        config.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH))); 
        icono = new ImageIcon ("src/iconos/help.png");
        help.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH))); 
        icono = new ImageIcon ("src/iconos/search.png");
        
        
        lista.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        buscarInv.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        buscarproduct.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        jPanel3.setVisible(false);
        
    }    
    
    // Propiedades de las Tablas 
    public void setConfigTable(){
           
        TableVenta.getColumnModel().getColumn(0).setCellRenderer( new CellRenderer_V(0) );
        TableVenta.getColumnModel().getColumn(1).setCellRenderer( new CellRenderer_V(2) );
        TableVenta.getColumnModel().getColumn(2).setCellRenderer( new CellRenderer_V(0) );
        TableVenta.getColumnModel().getColumn(3).setCellRenderer( new CellRenderer_V(0) );             
        TableVenta.getColumnModel().getColumn(4).setCellRenderer( new CellRenderer_V(1) );
        TableVenta.getColumnModel().getColumn(5).setCellRenderer( new CellRenderer_V(3) );  
        
        TableVenta.setRowHeight(21);
        TableVenta.setRowSelectionAllowed(true);
        TableVenta.setColumnSelectionAllowed(false);
       
        //Propiedades Tabla de articulos
        jTable2.getColumnModel().getColumn(0).setCellRenderer( new CellRenderer_V(0) );
        jTable2.getColumnModel().getColumn(1).setCellRenderer( new CellRenderer_V(0) );
        jTable2.getColumnModel().getColumn(2).setCellRenderer( new CellRenderer_V(2) );
        jTable2.getColumnModel().getColumn(3).setCellRenderer( new CellRenderer_V(0) );        
        jTable2.getColumnModel().getColumn(4).setCellRenderer( new CellRenderer_V(4) );
        jTable2.getColumnModel().getColumn(5).setCellRenderer( new CellRenderer_V(-1) );
        
        jTable2.setRowHeight(21);
        jTable2.setRowSelectionAllowed(true);
        jTable2.setColumnSelectionAllowed(false); 
        
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        jTable2.getColumnModel().getColumn(5).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(5).setMinWidth(0);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(0);
        
        tablaUsuario.setRowHeight(22);
        tablaUsuario.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaUsuario.getColumnModel().getColumn(0).setMinWidth(0);
        tablaUsuario.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        //Propiedades Tabla de articulos
        jTable3.getColumnModel().getColumn(0).setCellRenderer( new CellRenderer_V(0) );
        jTable3.getColumnModel().getColumn(1).setCellRenderer( new CellRenderer_V(0) );
        jTable3.getColumnModel().getColumn(2).setCellRenderer( new CellRenderer_V(2) );        
        jTable3.getColumnModel().getColumn(3).setCellRenderer( new CellRenderer_V(0) );        
        jTable3.getColumnModel().getColumn(4).setCellRenderer( new CellRenderer_V(4) );
        
        jTable3.setRowHeight(21);
        jTable3.setRowSelectionAllowed(true);
        jTable3.setColumnSelectionAllowed(false); 
        
        jTable3.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable3.getColumnModel().getColumn(0).setMinWidth(0);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        tabalMP.getColumnModel().getColumn(0).setMaxWidth(0);
        tabalMP.getColumnModel().getColumn(0).setMinWidth(0);
        tabalMP.getColumnModel().getColumn(0).setPreferredWidth(0);        
        
        sustances.getColumnModel().getColumn(0).setMaxWidth(0);
        sustances.getColumnModel().getColumn(0).setMinWidth(0);
        sustances.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        tablasustances.getColumnModel().getColumn(0).setMaxWidth(0);
        tablasustances.getColumnModel().getColumn(0).setMinWidth(0);
        tablasustances.getColumnModel().getColumn(0).setPreferredWidth(0);
        
    
    }
    
    //Cargar datos en la tabla Lista de sustances 
    public void setTableSus(){        
        for( int i = sustances.getRowCount() - 1; i >= 0; i-- ){ tabla4.removeRow(i);  }        
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT *FROM substances"); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) sustances.getModel();
                modelo.addRow(new Object[]{rs.getInt(1), rs.getString(2), false});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();  
    }
    
    public void setTableMP(String tabla){        
        for( int i = tabalMP.getRowCount() - 1; i >= 0; i-- ){ tabla6.removeRow(i);  }        
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT *FROM "+tabla); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) tabalMP.getModel();
                modelo.addRow(new Object[]{rs.getInt(1), rs.getString(2), false});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();  
    }
    
    
    //Cargar datos en la tabla Lista de sustances 
    public void setTableSustances(String query){        
        for( int i = tablasustances.getRowCount() - 1; i >= 0; i-- ){ tabla5.removeRow(i);  }        
        Conexion con = new Conexion();
        ResultSet rs = con.select(query); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) tablasustances.getModel();
                modelo.addRow(new Object[]{rs.getInt(1), rs.getString(2), false});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();  
    }
    
    //Cargar datos en la tabla Lista de articulos 
    public void setTableArt(String query){
        
        for( int i = tabla2.getRowCount() - 1; i >= 0; i-- ){ tabla2.removeRow(i); }
        
        Conexion con = new Conexion();
        ResultSet rs = con.select(query); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
                modelo.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5),rs.getFloat(6),rs.getInt(7),rs.getString(8)});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
  
    }
    
    public void setTableArtInv(String query){
        
        for( int i = tabla3.getRowCount() - 1; i >= 0; i-- ){ tabla3.removeRow(i); }
        
        Conexion con = new Conexion();
        ResultSet rs = con.select(query); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) jTable3.getModel();
                modelo.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5),rs.getFloat(6),rs.getInt(7)});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
  
    }
    
    
    public void setTableHisto(String query){
        
        for( int i = tabla8.getRowCount() - 1; i >= 0; i-- ){ tabla8.removeRow(i); }
        
        Conexion con = new Conexion();
        ResultSet rs = con.select(query); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
  
    }
    
    
    public void setTableUser(){
        
        for( int i = tablaUsuario.getRowCount() - 1; i >= 0; i-- ){ tabla7.removeRow(i); }
        
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT *FROM users"); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) tablaUsuario.getModel();
                String ad = "Operador";
                
                if(rs.getInt(8) != 0){
                    ad = "Administrador";                
                }
                
                modelo.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),ad});
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
  
    }
    
    
    public void setDatosUser(int id){
        
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT *FROM users WHERE id="+id); 
        try { 
            while(rs.next()){                         
                DefaultTableModel modelo = (DefaultTableModel) tablaUsuario.getModel();
                String ad = "Operador";
                
                if(rs.getInt(8) == 0){
                     jRadioButton3.setSelected(true);               
                }else 
                     jRadioButton1.setSelected(true);
                
                nombre1.setText(rs.getString(2));
                direccion.setText(rs.getString(3));
                telefono.setText(rs.getString(4));
                correo.setText(rs.getString(5));
                name.setText(rs.getString(6));
                passs.setText(rs.getString(7));
            } 
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
    
    
    }
    
    //Cargar venta pendiente
    public void loadVenta(){
        int n = 1;
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT (SELECT id FROM sales WHERE status=0), products.sku, products.name, products.description, marca.name , products.cost_sale, content_sales.cuantity, content_sales.amount, products.cuantity FROM products \n" +
                                  " LEFT JOIN marca ON products.marca_id = marca.id\n" +
                                  " LEFT JOIN content_sales ON products.id = content_sales.product_id \n" +
                                  " WHERE sale_id = (SELECT id FROM sales WHERE status=0)"); 
        try { 
            while(rs.next()){ 
                idVenta = rs.getInt(1);
                if(idVenta != 0){                
                    if(n==1) n = JOptionPane.showConfirmDialog(this,"Existe una venta pendiente \nDesea continuarla ?", "Continuar venta !!", JOptionPane.YES_NO_OPTION );
                    if(n==0){                    
                        DefaultTableModel modelo = (DefaultTableModel) TableVenta.getModel();
                        modelo.addRow(new Object[]{rs.getString(2),rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5),rs.getFloat(6),rs.getInt(7),rs.getInt(8),rs.getInt(9)});
                        totalVenta();
                        jPanel3.setVisible(true);                        
                    }else{
                        n=2;
                        con.execute("DELETE FROM sales WHERE id ="+idVenta);
                        con.execute("DELETE FROM content_sales WHERE sale_id ="+idVenta);                        
                    }
                }
            }
        
            
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
    
        
    
    
    }
     
    //Agregar articulos a las lista de venta
    public void addArt(String codigo){
        if(codigo.length() > 0 ){
            
            int cant = 1, exist = -1;
            DefaultTableModel modelo = (DefaultTableModel) TableVenta.getModel();
            String cc = codigo;
            Object datos[] = null;
            int idPro=0, canti =0;;
            float importe=0;
            
            if(inPro == true){      
                   inPro=false;
                   String dat[] = cc.split("X");
                   cc = dat[1];
                   cant = Integer.parseInt(dat[0]);
            }

            if(cc.length() > 0){
                   
                    Conexion con = new Conexion();
                    ResultSet rs = con.select("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id WHERE products.sku='"+cc+"'");
                    try { 
                        while(rs.next()){  
                            exist = rs.getInt(7);
                            idPro = rs.getInt(1);
                            if(exist > 0  && exist >= cant ){
                                exist = exist - cant;
                                con.execute("UPDATE products SET cuantity="+exist+" WHERE sku ='"+cc+"'");
                                String sku = rs.getString(2); 
                                
                                for (int i = 0; i < TableVenta.getRowCount(); i++) {
                                    String skuT = TableVenta.getValueAt(i, 0).toString();
                                    if(skuT.equals(sku)){
                                        canti = Integer.parseInt(TableVenta.getValueAt(i, 3).toString());
                                        modelo.removeRow(i);
                                        con.execute("DELETE FROM content_sales WHERE product_id ="+idPro);     
                                    }
                                }
                                
                                datos = new Object[]{rs.getString(2),rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5),rs.getFloat(6),cant+canti,rs.getFloat(6)*(cant+canti),exist};
                                importe = rs.getFloat(6)*(cant+canti);
                            }else{
                                if(exist > 0 ) JOptionPane.showMessageDialog(this, "Solo hay "+exist+" piezas disponibles.");
                                else JOptionPane.showMessageDialog(this, "No hay piezas disponibles.");
                                break;
                            }
                        } 
                    }catch(SQLException ex){
                        System.err.println(""+ex.getMessage());
                    }               
                    
                    
                    if(exist != -1){ 
                        if( datos != null){                            
                            modelo.addRow(datos);
                            totalVenta();
                            
                            if(idVenta == 0){
                                String fecha = dateFecha.getText();
                                con.execute("INSERT INTO sales VALUES (null, "+idU+", "+Tart+", "+Tventa+",'"+fecha+"',0)");
                                rs = con.select("SELECT id FROM sales WHERE time='"+fecha+"'");
                                try { 
                                    while(rs.next()){ idVenta = rs.getInt(1); }
                                }catch(Exception e){}
                                
                            }else{
                                con.execute("UPDATE sales SET products="+Tart+", total="+Tventa+" WHERE id ="+idVenta+"");
                            }
                            
                            con.execute("INSERT INTO content_sales VALUES(null, "+idVenta+", "+idPro+", "+(cant+canti)+", "+importe+")");
                            
                            jPanel3.setVisible(true);
                                                  
                        }
                    }else{  JOptionPane.showMessageDialog(this, "No hay articulo existente");}    
                    con.desconectar();
            }            
        }
    }
    
    
    
    //Cambiar de panel depende del menu
    public void remPanel(JPanel x){
         contenedor.removeAll();
         contenedor.add(x);
         contenedor.revalidate();
         contenedor.repaint();
    }
    
    //Eliminar datos de jTable
    public void Clear_Table(JTable jt){
        DefaultTableModel tabla = (DefaultTableModel) jt.getModel();
        for( int i = tabla.getRowCount() - 1; i >= 0; i-- ){ tabla.removeRow(i); }
    }
    
    //Cerrar sesion
    public void exit(){
        int n = JOptionPane.showConfirmDialog(this,"Cerrar sesión ?", "Salir de Terminal_PV !!", JOptionPane.YES_NO_OPTION );
        if(n==0){
                content.removeAll();
                content.setLayout(new GridBagLayout());
                content.setPreferredSize(this.getSize());
                content.setBackground( new Color(224,224,224) );
            
                content.add(new Login(content, dateFecha));
                content.setName("Login");
                content.revalidate();
                content.repaint();
        }        
     }
    
   
    //Decodifica iamgenes de la db en formato de texto
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    //Codifica las imagenes para guardar en la bd modo texto
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    //Cargar o actualizar la informacion de las Combos 
    public void setBox(){
        
        Conexion con = new Conexion();
        combo(Ccatego,con.select("SELECT *FROM category"));
        combo(Cmarca,con.select("SELECT *FROM marca"));
        combo(usuarios,con.select("SELECT *FROM users"));
        con.desconectar();
        
    }
    
    public void combo(JComboBox combo,ResultSet rs){         
        combo.removeAllItems();        
        try {              
         while(rs.next()){   combo.addItem(new ComboItem(rs.getString(2),rs.getString(1))); }         
         rs.close();
        }catch (Exception ex) {
                System.out.println(ex.getMessage());
        }  
    }
    
    //Habilita y deshabilita el control del inventario
    public void setEnableContainer(Container c, boolean band){         
        Component[] components = c.getComponents();
        c.setEnabled(band);
        
        for(int i = 0; i < components.length; i++){            
            components[i].setEnabled(band);
            if(components[i] instanceof Container){
                setEnableContainer((Container)components[i], band);
            }
        } 
        tablasustances.setEnabled(true);
        jTable3.setEnabled(true);
        nameUser2.setEnabled(true);
        jLabel10.setEnabled(true);
        Bnew.setEnabled(true); 
        jTabbedPane1.setEnabled(true);
        Bedit.setEnabled(false);
        Belimina.setEnabled(false);
        setBox();
        
        buscarInv.setEnabled(true);
        codigoBusca.setEnabled(true);
        
        jPanel13.setEnabled(true);
        jPanel6.setEnabled(true);
        
        jPanel14.setEnabled(true);
        jPanel15.setEnabled(true);
        
        m1.setEnabled(true); m4.setEnabled(true);
        m2.setEnabled(true); m5.setEnabled(true);
        m3.setEnabled(true);
        
    }
    
    //Eliminar contenido de los campos de texto
    public void setNull(){
            
            susName.setText("");
            susDescrip.setText("");
        
            Tsku.setText("");
            Tname.setText("");
            Tdescrip.setText("");
            Tcadu.setText("");
            Ccadu.setDate(null);
            Testante.setText("");
            Tdetalles.setText("");
            Tcostoprovee.setText("");
            Tcosto.setText("");
            Tcodigo.setText("");
            Tcanti.setText("");
            
            Bfoto.setIcon(null);
            Bfoto.setText("....");
            Bfoto.setName(null);
            
            try{
            Cmarca.setSelectedIndex(0);
            Ccatego.setSelectedIndex(0);
            }catch(Exception e){}
            
            DefaultListModel model = new DefaultListModel();
            listasus.setModel(model);
            
            setTableSus();
            jTabbedPane1.setEnabledAt(1, true);
            jTabbedPane1.setEnabledAt(0, true);
            
            
            Lsku.setForeground(Color.black); 
            Lname.setForeground(Color.black); 
            Lcadu.setForeground(Color.black); 
            Ldescrip.setForeground(Color.black); 
            Lcanti.setForeground(Color.black); 
            Lcosto.setForeground(Color.black); 
            Lcodigocosto.setForeground(Color.black); 
            Lprovee.setForeground(Color.black);       
            Lestatnte.setForeground(Color.black);
            
            jLabel11.setForeground(Color.black);
            jLabel12.setForeground(Color.black);
        
    }
    
    //Ver propiedades al seleccionar alguna sustancia
    public void setDatosSus(){
        
        String id =  tablasustances.getValueAt(tablasustances.getSelectedRow(), 0).toString();
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT * FROM substances WHERE id = "+id); 
        try { 
            while(rs.next()){
               susName.setText(rs.getString(2));
               susDescrip.setText(rs.getString(3));
            }
        }catch(Exception e){}
        
    
    }
     
    //Ver propiedades al seleccionar algun articulo
    public void setDatos(){
    
        String id =  jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString();
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT * FROM products WHERE id = "+id); 
        try { 
            while(rs.next()){                         
               Tsku.setText(rs.getString(2));
               Tname.setText(rs.getString(3));
               
               for (int j = 0; j < Ccatego.getItemCount(); j++) {
                    Ccatego.setSelectedIndex(j);
                    String value = ((ComboItem)Ccatego.getSelectedItem()).getValue();
                    int susp = Integer.parseInt(value);
                    if(susp==rs.getInt(8)){
                    break;
                    }
                }
             
               for (int j = 0; j < Cmarca.getItemCount(); j++) {
                    Cmarca.setSelectedIndex(j);
                    String value = ((ComboItem)Cmarca.getSelectedItem()).getValue();
                    int susp = Integer.parseInt(value);
                    if(susp==rs.getInt(9)){
                    break;
                    }
                }
               
               Tdescrip.setText(rs.getString(4));
               Tdetalles.setText(rs.getString(5));               
               Tcadu.setText(rs.getString(6));
               
                try {((JTextField) Ccadu.getDateEditor().getUiComponent()).setText(rs.getString(6)); } catch (Exception ex) { }
                
               
               Testante.setText(rs.getString(7));
               Tcodigo.setText(rs.getString(11));
               Tcostoprovee.setText(rs.getString(12)); 
               Tcosto.setText(rs.getString(13));
               Tcanti.setText(rs.getString(14));
               
               BufferedImage  newImg = decodeToImage(rs.getString(10));  
               ImageIcon icono = null;
        
                try{
                    icono = new ImageIcon (newImg); 
                }catch(Exception ex){
                    icono = new ImageIcon ("src/iconos/image.png");
                }
                Bfoto.setText("");
                Bfoto.setIcon(new ImageIcon(icono.getImage().getScaledInstance(150,80,Image.SCALE_SMOOTH)));               
               
            }  
         
           
            rs = con.select("SELECT  substances.id, substances.name FROM substances_product " +
                            "LEFT JOIN substances ON substances_product.substance_id = substances.id " +
                            "WHERE product_id ="+id);


            for (int i = 0; i < sustances.getRowCount(); i++) {                    
                       sustances.setValueAt(false, i, 2);                    
            }
            
            
            DefaultListModel model = new DefaultListModel();
            while(rs.next()){ 
                model.addElement(rs.getString(2));
                for (int i = 0; i < sustances.getRowCount(); i++) {
                    if(sustances.getValueAt(i, 0).toString().equals(""+rs.getString(1))){
                       sustances.setValueAt(true, i, 2);                    
                    }else{
                    }
                }                       
            }
            listasus.setModel(model);

                           
             
        
            
            }catch(SQLException ex){
                System.err.println(""+ex.getMessage());
            }               
            con.desconectar();
    
    
    }
    
    
    //$ total y # productos de la venta actual
    public void totalVenta(){    
        float total = 0; int art = 0;
        
        for (int j = 0; j < TableVenta.getRowCount(); j++) {
            total = total + Float.parseFloat( TableVenta.getValueAt(j, 4).toString());
            art = art + Integer.parseInt( TableVenta.getValueAt(j, 3).toString());
        }               
        tventa.setText("$ "+total);
        jLabel8.setText(" Productos en la venta actual:  "+art);
        Tventa = total;
        Tart = art;
        
        setTableArt("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity, products.image FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
        setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Venta = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        lista = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        etc = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        cobrar = new javax.swing.JButton();
        tventa = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        nameUser = new javax.swing.JLabel();
        Productos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        buscarproduct = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Inventario = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        nameUser2 = new javax.swing.JLabel();
        Bsave = new javax.swing.JButton();
        Belimina = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Bnew = new javax.swing.JButton();
        Bcancel = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Lcanti = new javax.swing.JLabel();
        Lcosto = new javax.swing.JLabel();
        Lcodigocosto = new javax.swing.JLabel();
        Lprovee = new javax.swing.JLabel();
        Tcanti = new javax.swing.JTextField();
        Tcodigo = new javax.swing.JTextField();
        Tcostoprovee = new javax.swing.JTextField();
        Lestatnte = new javax.swing.JLabel();
        Testante = new javax.swing.JTextField();
        Ldescrip = new javax.swing.JLabel();
        Tcosto = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Tdetalles = new javax.swing.JTextArea();
        Tdescrip = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        Lsku = new javax.swing.JLabel();
        Ldetalles = new javax.swing.JLabel();
        Lname = new javax.swing.JLabel();
        Lmarca = new javax.swing.JLabel();
        Lpresen = new javax.swing.JLabel();
        Lcadu = new javax.swing.JLabel();
        Tsku = new javax.swing.JTextField();
        Tname = new javax.swing.JTextField();
        Cmarca = new javax.swing.JComboBox<>();
        Ccatego = new javax.swing.JComboBox<>();
        addM = new javax.swing.JButton();
        addC = new javax.swing.JButton();
        addS = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        listasus = new javax.swing.JList<>();
        Ccadu = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablasustances = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        susName = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        susDescrip = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        codigoBusca = new javax.swing.JTextField();
        buscarInv = new javax.swing.JButton();
        m1 = new javax.swing.JLabel();
        m2 = new javax.swing.JLabel();
        m3 = new javax.swing.JLabel();
        m4 = new javax.swing.JLabel();
        m5 = new javax.swing.JLabel();
        Configuracion = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaUsuario = new javax.swing.JTable();
        userNew = new javax.swing.JButton();
        userEdi = new javax.swing.JButton();
        userEli = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cal1 = new com.toedter.calendar.JDateChooser();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        usuarios = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        Cobrar = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Bfoto = new javax.swing.JButton();
        Sustancia = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        sustances = new javax.swing.JTable();
        addPM = new javax.swing.JPanel();
        agrega = new javax.swing.JButton();
        guarda = new javax.swing.JButton();
        nombreMP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabalMP = new javax.swing.JTable();
        elimina = new javax.swing.JButton();
        cancela = new javax.swing.JButton();
        propiedades = new javax.swing.JPanel();
        productosPanel = new javax.swing.JPanel();
        locali = new javax.swing.JLabel();
        precio = new javax.swing.JLabel();
        cc = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        detallada = new javax.swing.JTextArea();
        descri = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        activas1 = new javax.swing.JList();
        cantidad = new javax.swing.JLabel();
        present = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        productos1 = new javax.swing.JList();
        fecha = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        tmarca = new javax.swing.JLabel();
        activasPanel2 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        detalladaA = new javax.swing.JTextArea();
        nombreA = new javax.swing.JLabel();
        Usuarios = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        nombre1 = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        correo = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        name = new javax.swing.JTextField();
        passs = new javax.swing.JTextField();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        grupo1 = new javax.swing.ButtonGroup();
        ayuda = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        Tcadu = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        venta = new javax.swing.JLabel();
        invent = new javax.swing.JLabel();
        config = new javax.swing.JLabel();
        help = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        contenedor = new javax.swing.JPanel();

        Venta.setOpaque(false);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("  VENTA DE PRODUCTOS");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Código de Producto:");

        codigo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigo.setToolTipText("Codigo de producto");
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        codigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                codigoMousePressed(evt);
            }
        });
        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codigoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoKeyTyped(evt);
            }
        });

        search.setToolTipText("Agregar Articulo");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        lista.setToolTipText("Buscar Articulo");
        lista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(codigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lista, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2))
            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lista, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);

        etc.setText("F2 - Cotización");
        etc.setEnabled(false);
        etc.setMaximumSize(new java.awt.Dimension(150, 25));
        etc.setMinimumSize(new java.awt.Dimension(150, 25));
        etc.setPreferredSize(new java.awt.Dimension(150, 25));

        delete.setEnabled(false);
        delete.setLabel("Supr - Eliminar");
        delete.setMaximumSize(new java.awt.Dimension(150, 25));
        delete.setMinimumSize(new java.awt.Dimension(150, 25));
        delete.setPreferredSize(new java.awt.Dimension(150, 25));
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        cancel.setText("Esc - Cancelar");
        cancel.setMaximumSize(new java.awt.Dimension(150, 25));
        cancel.setMinimumSize(new java.awt.Dimension(150, 25));
        cancel.setPreferredSize(new java.awt.Dimension(150, 25));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        cobrar.setText("F10 - Cobrar");
        cobrar.setMaximumSize(new java.awt.Dimension(180, 23));
        cobrar.setMinimumSize(new java.awt.Dimension(110, 23));
        cobrar.setPreferredSize(new java.awt.Dimension(110, 23));
        cobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobrarActionPerformed(evt);
            }
        });

        tventa.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        tventa.setForeground(new java.awt.Color(255, 102, 0));
        tventa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tventa.setText("$00.00");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText(" 0   Productos en la venta actual");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(etc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cobrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tventa, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tventa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(etc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(2, 2, 2))
        );

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Precio", "Cantidad", "Importe", "Exist. Actual"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVenta.setOpaque(false);
        TableVenta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TableVenta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TableVentaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TableVentaFocusLost(evt);
            }
        });
        TableVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setMinWidth(130);
            TableVenta.getColumnModel().getColumn(1).setMinWidth(350);
        }

        nameUser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameUser.setForeground(new java.awt.Color(102, 102, 102));
        nameUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameUser.setText("Administrador  ");

        javax.swing.GroupLayout VentaLayout = new javax.swing.GroupLayout(Venta);
        Venta.setLayout(VentaLayout);
        VentaLayout.setHorizontalGroup(
            VentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(VentaLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        VentaLayout.setVerticalGroup(
            VentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentaLayout.createSequentialGroup()
                .addGroup(VentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(nameUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Productos.setOpaque(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Código", "Descripción", "Precio", "Cantidad", "Imagen"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable2FocusLost(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(350);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(10);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(10);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jTextField2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        buscarproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarproductActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Código o Nombre:");

        javax.swing.GroupLayout ProductosLayout = new javax.swing.GroupLayout(Productos);
        Productos.setLayout(ProductosLayout);
        ProductosLayout.setHorizontalGroup(
            ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductosLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ProductosLayout.createSequentialGroup()
                .addGroup(ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductosLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ProductosLayout.setVerticalGroup(
            ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(buscarproduct, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        Inventario.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText(" MENU INVENTARIO");

        nameUser2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameUser2.setForeground(new java.awt.Color(102, 102, 102));
        nameUser2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameUser2.setText("Administrador  ");

        Bsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsaveActionPerformed(evt);
            }
        });

        Belimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeliminaActionPerformed(evt);
            }
        });

        Bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeditActionPerformed(evt);
            }
        });

        Bnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BnewActionPerformed(evt);
            }
        });

        Bcancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcancelActionPerformed(evt);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del articulo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jPanel5.setMinimumSize(new java.awt.Dimension(370, 233));

        Lcanti.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lcanti.setText("Cantidad:");

        Lcosto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lcosto.setText("Costo\\Venta:");

        Lcodigocosto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lcodigocosto.setText("Código\\ Costo:");

        Lprovee.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lprovee.setText("Costo\\Proveedor:");

        Tcanti.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tcanti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TcantiKeyTyped(evt);
            }
        });

        Tcodigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TcodigoKeyTyped(evt);
            }
        });

        Tcostoprovee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tcostoprovee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TcostoproveeKeyTyped(evt);
            }
        });

        Lestatnte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lestatnte.setText("Estante:");

        Testante.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Testante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TestanteKeyTyped(evt);
            }
        });

        Ldescrip.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Ldescrip.setText("Descripción:");

        Tcosto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tcosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TcostoKeyTyped(evt);
            }
        });

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Detalles:");

        Tdetalles.setColumns(20);
        Tdetalles.setRows(5);
        Tdetalles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane4.setViewportView(Tdetalles);

        Tdescrip.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tdescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TdescripKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Lcanti, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ldescrip, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Lestatnte, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Lcodigocosto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lprovee, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tcostoprovee)
                    .addComponent(Tcodigo)
                    .addComponent(Testante)
                    .addComponent(Tcanti)
                    .addComponent(Tdescrip)
                    .addComponent(Tcosto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lcanti, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tcanti, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tcostoprovee, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lprovee, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lcodigocosto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lestatnte, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Testante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ldescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tdescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPanel4.setMinimumSize(new java.awt.Dimension(368, 233));
        jPanel4.setRequestFocusEnabled(false);

        Lsku.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lsku.setText("Sku:");

        Ldetalles.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Ldetalles.setText("Sustancias:");

        Lname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lname.setText("Nombre:");

        Lmarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lmarca.setText("Marca");

        Lpresen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lpresen.setText("Presentación:");

        Lcadu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lcadu.setText("Caducidad:");

        Tsku.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tsku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TskuKeyTyped(evt);
            }
        });

        Tname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Tname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TnameKeyTyped(evt);
            }
        });

        Cmarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Ccatego.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMActionPerformed(evt);
            }
        });

        addC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCActionPerformed(evt);
            }
        });

        addS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSActionPerformed(evt);
            }
        });

        jScrollPane8.setViewportView(listasus);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Lname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Lmarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Lpresen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Lsku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Ldetalles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Lcadu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Tsku, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(Ccatego, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(Cmarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(Ccadu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lsku, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tsku, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lmarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Lpresen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ccatego, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Lcadu, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ccadu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Ldetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de articulos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jScrollPane3.setOpaque(false);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Código", "Descripción", "Precio", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setOpaque(false);
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable3MousePressed(evt);
            }
        });
        jTable3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable3FocusGained(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(350);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(10);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Productos", jPanel7);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de sustancias", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        tablasustances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Sustancia Ativa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablasustances.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablasustancesMousePressed(evt);
            }
        });
        tablasustances.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablasustancesFocusGained(evt);
            }
        });
        tablasustances.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablasustancesKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablasustances);
        if (tablasustances.getColumnModel().getColumnCount() > 0) {
            tablasustances.getColumnModel().getColumn(0).setResizable(false);
            tablasustances.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la sustancia", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Nombre :");

        susName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                susNameKeyTyped(evt);
            }
        });

        susDescrip.setColumns(20);
        susDescrip.setRows(5);
        jScrollPane7.setViewportView(susDescrip);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Descripcion :");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addComponent(susName))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(susName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sustancias", jPanel8);

        codigoBusca.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        codigoBusca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigoBusca.setText("Buscar ...");
        codigoBusca.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigoBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoBuscaActionPerformed(evt);
            }
        });
        codigoBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codigoBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoBuscaFocusLost(evt);
            }
        });
        codigoBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoBuscaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoBuscaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoBuscaKeyTyped(evt);
            }
        });

        buscarInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarInvActionPerformed(evt);
            }
        });

        m1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        m1.setText("Nuevo");

        m2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        m2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m2.setText("Editar");

        m3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        m3.setText("Eliminar");

        m4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        m4.setText("Guardar");

        m5.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        m5.setText("Cancelar");

        javax.swing.GroupLayout InventarioLayout = new javax.swing.GroupLayout(Inventario);
        Inventario.setLayout(InventarioLayout);
        InventarioLayout.setHorizontalGroup(
            InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(InventarioLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Bnew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventarioLayout.createSequentialGroup()
                        .addComponent(Bedit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(Belimina, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(Bsave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InventarioLayout.createSequentialGroup()
                        .addComponent(m2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(m3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(m4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m5)
                    .addComponent(Bcancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(codigoBusca)
                .addGap(3, 3, 3)
                .addComponent(buscarInv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        InventarioLayout.setVerticalGroup(
            InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventarioLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Bsave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Belimina, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bedit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bnew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bcancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codigoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarInv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(InventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m1)
                    .addComponent(m2)
                    .addComponent(m3)
                    .addComponent(m4)
                    .addComponent(m5))
                .addGap(2, 2, 2)
                .addComponent(jTabbedPane1))
        );

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        tablaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre", "Dirección", "Telefono", "Correo", "Usuario", "Contraseña", "Cargo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUsuario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuarioMouseClicked(evt);
            }
        });
        tablaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaUsuarioFocusLost(evt);
            }
        });
        tablaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaUsuarioKeyReleased(evt);
            }
        });
        jScrollPane13.setViewportView(tablaUsuario);
        if (tablaUsuario.getColumnModel().getColumnCount() > 0) {
            tablaUsuario.getColumnModel().getColumn(0).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(1).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablaUsuario.getColumnModel().getColumn(2).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(2).setPreferredWidth(150);
            tablaUsuario.getColumnModel().getColumn(3).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(4).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaUsuario.getColumnModel().getColumn(5).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(6).setResizable(false);
            tablaUsuario.getColumnModel().getColumn(7).setResizable(false);
        }

        userNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNewActionPerformed(evt);
            }
        });

        userEdi.setEnabled(false);
        userEdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEdiActionPerformed(evt);
            }
        });

        userEli.setEnabled(false);
        userEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(userNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userEdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userEli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userEdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userEli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Usuarios", jPanel9);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Fecha", "Productos", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        cal1.setEnabled(false);

        jLabel28.setText("Mostrar ventas:");

        jLabel29.setText("Fecha:");

        jLabel30.setText("Usuario:");

        usuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Usuarios" }));
        usuarios.setEnabled(false);

        jButton4.setText("Mostrar datos:");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jRadioButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jRadioButton2PropertyChange(evt);
            }
        });

        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(30, 30, 30)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jRadioButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton4)))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Historial", jPanel10);

        jLabel4.setText("Configuración :");

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setEnabled(false);

        jLabel18.setText("              ");

        javax.swing.GroupLayout ConfiguracionLayout = new javax.swing.GroupLayout(Configuracion);
        Configuracion.setLayout(ConfiguracionLayout);
        ConfiguracionLayout.setHorizontalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addGroup(ConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConfiguracionLayout.setVerticalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane2))
        );

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" $ Dinero recibido");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Total a cobrar");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("$100");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Su cambio:  $100.0");

        javax.swing.GroupLayout CobrarLayout = new javax.swing.GroupLayout(Cobrar);
        Cobrar.setLayout(CobrarLayout);
        CobrarLayout.setHorizontalGroup(
            CobrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CobrarLayout.createSequentialGroup()
                .addGroup(CobrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CobrarLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTextField1)
                        .addGap(30, 30, 30))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        CobrarLayout.setVerticalGroup(
            CobrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CobrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Bfoto.setText("....");
        Bfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BfotoActionPerformed(evt);
            }
        });

        sustances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "id", "Nombre", "Estatus"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(sustances);
        if (sustances.getColumnModel().getColumnCount() > 0) {
            sustances.getColumnModel().getColumn(0).setResizable(false);
            sustances.getColumnModel().getColumn(1).setResizable(false);
            sustances.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout SustanciaLayout = new javax.swing.GroupLayout(Sustancia);
        Sustancia.setLayout(SustanciaLayout);
        SustanciaLayout.setHorizontalGroup(
            SustanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SustanciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SustanciaLayout.setVerticalGroup(
            SustanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SustanciaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        agrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregaActionPerformed(evt);
            }
        });

        guarda.setEnabled(false);
        guarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardaActionPerformed(evt);
            }
        });

        nombreMP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        nombreMP.setEnabled(false);
        nombreMP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreMPKeyTyped(evt);
            }
        });

        jLabel9.setText("Nombre:");

        tabalMP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabalMP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabalMPMousePressed(evt);
            }
        });
        tabalMP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabalMPFocusGained(evt);
            }
        });
        jScrollPane9.setViewportView(tabalMP);
        if (tabalMP.getColumnModel().getColumnCount() > 0) {
            tabalMP.getColumnModel().getColumn(0).setResizable(false);
            tabalMP.getColumnModel().getColumn(1).setResizable(false);
        }

        elimina.setEnabled(false);
        elimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminaActionPerformed(evt);
            }
        });

        cancela.setEnabled(false);
        cancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addPMLayout = new javax.swing.GroupLayout(addPM);
        addPM.setLayout(addPMLayout);
        addPMLayout.setHorizontalGroup(
            addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreMP, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addPMLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(guarda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(elimina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(agrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancela, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addPMLayout.setVerticalGroup(
            addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreMP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPMLayout.createSequentialGroup()
                        .addComponent(agrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(elimina, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guarda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancela, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        propiedades.setLayout(new java.awt.GridLayout(1, 0));

        productosPanel.setBackground(new java.awt.Color(255, 255, 255));
        productosPanel.setOpaque(false);

        locali.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        locali.setText("Localización:  ");

        precio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        precio.setText("Precio: $ ");

        cc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cc.setText("CC:  ");

        detallada.setEditable(false);
        detallada.setColumns(20);
        detallada.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        detallada.setRows(5);
        detallada.setText("\n");
        detallada.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Descripción detallada", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        detallada.setOpaque(false);
        jScrollPane10.setViewportView(detallada);

        descri.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        descri.setText("Descripción:  ");

        activas1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        activas1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        activas1.setOpaque(false);
        activas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activas1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                activas1MousePressed(evt);
            }
        });
        activas1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                activas1ValueChanged(evt);
            }
        });
        jScrollPane11.setViewportView(activas1);

        cantidad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cantidad.setText("Cantidad: ");

        present.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        present.setText("Presentación: ");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Activas:");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Productos similares");

        productos1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        productos1.setOpaque(false);
        productos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productos1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                productos1MousePressed(evt);
            }
        });
        productos1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                productos1ValueChanged(evt);
            }
        });
        jScrollPane12.setViewportView(productos1);

        fecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fecha.setText("Caducidad:  ");

        nombre.setFont(new java.awt.Font("Lucida Grande", 1, 22)); // NOI18N
        nombre.setForeground(new java.awt.Color(0, 51, 255));
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tmarca.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tmarca.setText("Marca:");

        javax.swing.GroupLayout productosPanelLayout = new javax.swing.GroupLayout(productosPanel);
        productosPanel.setLayout(productosPanelLayout);
        productosPanelLayout.setHorizontalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosPanelLayout.createSequentialGroup()
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productosPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descri, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(productosPanelLayout.createSequentialGroup()
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(92, 92, 92)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(productosPanelLayout.createSequentialGroup()
                                    .addComponent(precio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(192, 192, 192))
                                .addGroup(productosPanelLayout.createSequentialGroup()
                                    .addComponent(tmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                    .addComponent(present, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(productosPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(locali, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        productosPanelLayout.setVerticalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productosPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precio)
                    .addComponent(cantidad))
                .addGap(11, 11, 11)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locali)
                    .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(present)
                    .addComponent(tmarca))
                .addGap(10, 10, 10)
                .addComponent(fecha)
                .addGap(10, 10, 10)
                .addComponent(descri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        activasPanel2.setBackground(new java.awt.Color(255, 255, 255));

        detalladaA.setEditable(false);
        detalladaA.setColumns(20);
        detalladaA.setRows(5);
        detalladaA.setBorder(null);
        detalladaA.setOpaque(false);
        jScrollPane17.setViewportView(detalladaA);

        nombreA.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        nombreA.setForeground(new java.awt.Color(0, 51, 255));
        nombreA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout activasPanel2Layout = new javax.swing.GroupLayout(activasPanel2);
        activasPanel2.setLayout(activasPanel2Layout);
        activasPanel2Layout.setHorizontalGroup(
            activasPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, activasPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(activasPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nombreA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                .addContainerGap())
        );
        activasPanel2Layout.setVerticalGroup(
            activasPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(activasPanel2Layout.createSequentialGroup()
                .addComponent(nombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel90.setText("Nombre: ");

        jLabel91.setText("Dirección:");

        jLabel92.setText("Telefono:");

        nombre1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        nombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombre1KeyTyped(evt);
            }
        });

        direccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionKeyTyped(evt);
            }
        });

        telefono.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoKeyTyped(evt);
            }
        });

        jLabel93.setText("Correo:");

        correo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                correoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel91)
                            .addGap(18, 18, 18))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel90)
                            .addGap(23, 23, 23)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel92)
                            .addComponent(jLabel93))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(direccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(nombre1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(telefono, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(correo))
                .addGap(20, 20, 20))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de usuario", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel94.setText("Usuario:");

        jLabel95.setText("Contraseña:");

        jLabel24.setText("Permisos:");

        jRadioButton1.setText("Administrador");

        name.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameKeyTyped(evt);
            }
        });

        passs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        passs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passsKeyTyped(evt);
            }
        });

        jRadioButton3.setText("Operador");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94)
                    .addComponent(jLabel95)
                    .addComponent(jLabel24))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passs))))
                .addGap(20, 20, 20))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95))
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jLabel24)
                    .addComponent(jRadioButton1))
                .addGap(20, 20, 20))
        );

        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText(" ");

        javax.swing.GroupLayout UsuariosLayout = new javax.swing.GroupLayout(Usuarios);
        Usuarios.setLayout(UsuariosLayout);
        UsuariosLayout.setHorizontalGroup(
            UsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsuariosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(UsuariosLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(UsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UsuariosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        UsuariosLayout.setVerticalGroup(
            UsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsuariosLayout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addGap(11, 11, 11))
            .addGroup(UsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UsuariosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(188, Short.MAX_VALUE)))
        );

        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("Acerca de..");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton2.setText("Anterior");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Siguiente");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("1/100");
        jLabel34.setPreferredSize(new java.awt.Dimension(50, 16));

        javax.swing.GroupLayout ayudaLayout = new javax.swing.GroupLayout(ayuda);
        ayuda.setLayout(ayudaLayout);
        ayudaLayout.setHorizontalGroup(
            ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ayudaLayout.createSequentialGroup()
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ayudaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(ayudaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 281, Short.MAX_VALUE))))
                    .addGroup(ayudaLayout.createSequentialGroup()
                        .addContainerGap(220, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 221, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ayudaLayout.setVerticalGroup(
            ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ayudaLayout.createSequentialGroup()
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        Tcadu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        venta.setText("Venta");
        venta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        venta.setOpaque(true);
        venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ventaMousePressed(evt);
            }
        });
        jPanel1.add(venta);

        invent.setText("Inventario");
        invent.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        invent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                inventMousePressed(evt);
            }
        });
        jPanel1.add(invent);

        config.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        config.setText("Sistema");
        config.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        config.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                configMousePressed(evt);
            }
        });
        jPanel1.add(config);

        help.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        help.setText("Ayuda");
        help.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        help.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                helpMousePressed(evt);
            }
        });
        jPanel1.add(help);

        exit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exit.setText("Cerrar sesión");
        exit.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exitMousePressed(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitMouseEntered(evt);
            }
        });
        jPanel1.add(exit);

        contenedor.setOpaque(false);
        contenedor.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ventaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ventaMousePressed
        // TODO add your handling code here:
        venta.setOpaque(true);
        venta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        invent.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  invent.setOpaque(false);
        config.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  config.setOpaque(false);
        help.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  help.setOpaque(false);
        remPanel(Venta);
    }//GEN-LAST:event_ventaMousePressed

    private void inventMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventMousePressed
        // TODO add your handling code here:
        invent.setOpaque(true);
        invent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        venta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));   venta.setOpaque(false);
        config.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  config.setOpaque(false);
        help.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  help.setOpaque(false);
        remPanel(Inventario);
    }//GEN-LAST:event_inventMousePressed

    private void configMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configMousePressed
        // TODO add your handling code here:
        config.setOpaque(true);
        config.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        invent.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  invent.setOpaque(false);
        venta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));   venta.setOpaque(false);
        help.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  help.setOpaque(false);
        remPanel(Configuracion);
        setTableHisto("SELECT users.full_name, sales.time, sales.products, sales.total FROM sales " +
                                  "LEFT JOIN users ON users.id = sales.user_id");
    }//GEN-LAST:event_configMousePressed

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        // TODO add your handling code here:
        addArt(codigo.getText());    
        codigo.setText("");
    }//GEN-LAST:event_codigoActionPerformed

    private void codigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusGained
        // TODO add your handling code here:
        //delete.setEnabled(false);
    }//GEN-LAST:event_codigoFocusGained

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusLost
    
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        addArt(codigo.getText()); 
    }//GEN-LAST:event_searchActionPerformed

    private void codigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codigoMousePressed
        // TODO add your handling code here:
        //TableVenta.clearSelection(); 
        //delete.setEnabled(false);
    }//GEN-LAST:event_codigoMousePressed

    private void cobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobrarActionPerformed

      cobrarVenta();
            
    }//GEN-LAST:event_cobrarActionPerformed

    private void BnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BnewActionPerformed
        // TODO add your handling code here: 
                
        setEnableContainer(Inventario, true);  
        jTable3.clearSelection();
        jTable3.setEnabled(false);
        
        tablasustances.clearSelection();
        tablasustances.setEnabled(false);
        
        Bnew.transferFocus();
        Bnew.setEnabled(false);
        setNull();
        
        
        int i= jTabbedPane1.getSelectedIndex();        
        if(i==0){  jTabbedPane1.setEnabledAt(1, false); Tsku.requestFocus();}
        else{ jTabbedPane1.setEnabledAt(0, false); susName.requestFocus();}
        
       jPanel6.setBorder(createTitledBorder(null, "Nuevo articulo", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
       jPanel15.setBorder(createTitledBorder(null, "Nueva sustancia", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

       
    }//GEN-LAST:event_BnewActionPerformed

    private void BcancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcancelActionPerformed
        // TODO add your handling code here: 
        jPanel6.setBorder(createTitledBorder(null, "Datos del articulo", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel15.setBorder(createTitledBorder(null, "Datos de la sustancia", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        
        jTabbedPane1.setEnabledAt(1, true);
        jTabbedPane1.setEnabledAt(0, true);        
        jTable3.clearSelection();
        tablasustances.clearSelection();
        this.requestFocus();
        setEnableContainer(Inventario, false);
        setNull();        
    }//GEN-LAST:event_BcancelActionPerformed

    private void jTable3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable3FocusGained
        // TODO add your handling code here:
        Bedit.setEnabled(true); Belimina.setEnabled(true);
    }//GEN-LAST:event_jTable3FocusGained

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 27){  
            setNull();
            jTable3.clearSelection();
            this.requestFocus();
            Bedit.setEnabled(false); Belimina.setEnabled(false); 
        }
        
        if(Bnew.isEnabled() && evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){            
            setDatos();
        }
        
    }//GEN-LAST:event_jTable3KeyPressed

    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
        // TODO add your handling code here: 
        
        jPanel6.setBorder(createTitledBorder(null, "Editar articulo", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel6.setBorder(createTitledBorder(null, "Editar sustancia", TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        
        int i= jTabbedPane1.getSelectedIndex();        
        if(i==0)  jTabbedPane1.setEnabledAt(1, false);
        else jTabbedPane1.setEnabledAt(0, false);
        
        setEnableContainer(Inventario, true);
        Bnew.setEnabled(false); Bedit.setEnabled(false); Belimina.setEnabled(false);
        
        jTable3.setEnabled(false);
        tablasustances.setEnabled(false);
        
        
        
    }//GEN-LAST:event_BeditActionPerformed

    private void BeliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeliminaActionPerformed
        // TODO add your handling code here:
        int i= jTabbedPane1.getSelectedIndex();  
        if(i==0){
            String name =  jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString();
            int n = JOptionPane.showConfirmDialog(this,"Eliminar el articulo ' "+name+" ' ?", "Eliminar articulo !!", JOptionPane.YES_NO_OPTION );
            if(n==0){
                Conexion con = new Conexion(); 
                String id =  jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString();
                con.execute("DELETE FROM products WHERE id = "+id);        
                con.desconectar();            
                setEnableContainer(Inventario, false);            
                setTableArt("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity, products.image FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
                
                setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");  
                setNull();
            }
        }else{
            String name =  tablasustances.getValueAt(tablasustances.getSelectedRow(), 1).toString();
            int n = JOptionPane.showConfirmDialog(this,"Eliminar el sustancia ' "+name+" ' ?", "Eliminar sustancia !!", JOptionPane.YES_NO_OPTION );
            if(n==0){
                Conexion con = new Conexion(); 
                String id =  tablasustances.getValueAt(tablasustances.getSelectedRow(), 0).toString();
                con.execute("DELETE FROM substances WHERE id = "+id);        
                con.desconectar();            
                setEnableContainer(Inventario, false);            
                setTableSus();
                setTableSustances("SELECT *FROM substances");
                setNull();
            }
        
            
        
        
        }
        
        
    }//GEN-LAST:event_BeliminaActionPerformed

    private void BsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsaveActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this,"Guardar los datos ?", "Guardar datos !!", JOptionPane.YES_NO_OPTION );
        if(n==0){
            int i= jTabbedPane1.getSelectedIndex();   
            if(i == 0)  insertDatos();
            else insertSust();
        }
    }//GEN-LAST:event_BsaveActionPerformed

    private void addMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMActionPerformed
        // TODO add your handling code here:
        elimina.setEnabled(false);
        addPM.setName("Marca");
        setTableMP("marca");
        JOptionPane.showMessageDialog(this, addPM,"Marca",JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_addMActionPerformed
   
    private void BfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BfotoActionPerformed
        // TODO add your handling code here:
      try{  
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser file = new JFileChooser();
        file.setFileFilter(filter);
       
        file.showOpenDialog(this);        
        String url = file.getSelectedFile().toString();
        
        if(!url.isEmpty()){
            Bfoto.setText("");
            Bfoto.setName(url);
            ImageIcon icono = new ImageIcon (url);
            Bfoto.setIcon(new ImageIcon(icono.getImage().getScaledInstance(150,80,Image.SCALE_SMOOTH)));       
        }
      }catch(Exception w ){
      
      
      }
        
    }//GEN-LAST:event_BfotoActionPerformed

    private void jTable3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MousePressed
        // TODO add your handling code here:
        if(Bnew.isEnabled()){            
            setDatos();
        }
    }//GEN-LAST:event_jTable3MousePressed

    private void listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaActionPerformed
        // TODO add your handling code here:
        jTable2.clearSelection();
        jTextField2.setText("");
        jTextField2.requestFocus();
        JOptionPane.showMessageDialog(this, Productos, "Lista de productos", JOptionPane.PLAIN_MESSAGE);
        codigo.requestFocus();
    }//GEN-LAST:event_listaActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
            if (evt.getClickCount() == 2) {
                addArt(jTable2.getValueAt(jTable2.getSelectedRow(),1).toString());
            }
    }//GEN-LAST:event_jTable2MouseClicked

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        
        cancelarventaAct();
        
    }//GEN-LAST:event_cancelActionPerformed

    
    public void cancelarventaAct(){
    
        int n = JOptionPane.showConfirmDialog(this,"Cancelar la venta actual ?", "Cancelar venta", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE );
        if(n==0){                
                for (int i = 0; i < TableVenta.getRowCount(); i++) {
                    String cc = TableVenta.getValueAt(i, 0).toString();
                    int cant = Integer.parseInt(TableVenta.getValueAt(i, 3).toString());                    
                    int exist=0;
                    Conexion con = new Conexion();
                        
                    ResultSet rs = con.select("SELECT cuantity FROM products WHERE sku ='"+cc+"'"); 
                        try { 
                            while(rs.next()){                         
                               exist = rs.getInt(1);
                            }
                        }catch(Exception e){             
                        }
                        
                    con.execute("UPDATE products SET cuantity="+(cant+exist)+" WHERE sku ='"+cc+"'");
                    con.execute("DELETE FROM sales WHERE id ="+idVenta);
                    con.execute("DELETE FROM content_sales WHERE sale_id ="+idVenta);
               
                    idVenta=0;
                    con.desconectar();
                }
                Clear_Table(TableVenta);
                jPanel3.setVisible(false); 
                totalVenta();                    
        }
    
    
    }
    
    
    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        // TODO add your handling code here:
        
        
        if(TableVenta.getSelectedRow() >= 0){
        
            if(evt.getKeyCode() == 27){  
                TableVenta.clearSelection();
                codigo.requestFocusInWindow(); 
                delete.setEnabled(false);
            }
            if(evt.getKeyCode() == 127){ 
                eliminaArt();
            }
        
        }else {
        
        
                if(evt.getKeyCode() == 88){  
                   inPro = true;
                }

                if(evt.getKeyCode() == 121){  
                  cobrarVenta();
                }

                if(evt.getKeyCode() == 27 ){



                   if(0 < codigo.getText().length()){
                        codigo.setText("");
                        inPro = false;
                    }           
                    else{
                       if(0 < TableVenta.getRowCount()){
                            cancelarventaAct();
                       }
                    }   
                }
        }
        
        
        
    }//GEN-LAST:event_codigoKeyPressed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        eliminaArt();
        codigo.setRequestFocusEnabled(true);
    }//GEN-LAST:event_deleteActionPerformed

    private void TableVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentaKeyPressed
        // TODO add your handling code here:
        
        codigo.requestFocus();
        
    }//GEN-LAST:event_TableVentaKeyPressed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        buscaProducto();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        float cambio =-Tventa;
        if(!jTextField1.getText().equals("")){
            cambio = Float.parseFloat(jTextField1.getText()) - Tventa;
        }
        if(cambio >= 0){ 
            Window w = SwingUtilities.getWindowAncestor(jTextField1);
            if (w != null) { 
                w.setVisible(false);
                terminarVenta();
                ImageIcon icono = new ImageIcon ("src/iconos/cobrar.png");         
                JOptionPane.showMessageDialog(this, "Gracias por su compra.","Vuelva pronto !!",0, new ImageIcon(icono.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            }
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        
        if(!Bcancel.isEnabled()){
        
            int i= jTabbedPane1.getSelectedIndex();        
            if(i==0){ 
                tablasustances.clearSelection(); 
                if(jTable3.getSelectedRow() < 0){ 
                    setNull();
                    Bedit.setEnabled(false); Belimina.setEnabled(false);
                }
            }
            else{ 
                jTable3.clearSelection();
                if(tablasustances.getSelectedRow() < 0){ 
                    setNull();
                    Bedit.setEnabled(false); Belimina.setEnabled(false);
                }
            }

            this.requestFocus();
        
        }
         
        
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void addSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, Sustancia,"Sustancias contenidas:",JOptionPane.PLAIN_MESSAGE);
        
        

            
            DefaultListModel model = new DefaultListModel();
            
                
                for (int i = 0; i < sustances.getRowCount(); i++) {
                    if(sustances.getValueAt(i, 2 ).toString().equals("true")){
                       model.addElement(sustances.getValueAt(i, 1).toString());                   
                    }
                }                       
            
            listasus.setModel(model);
        
    }//GEN-LAST:event_addSActionPerformed

    private void tablasustancesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasustancesMousePressed
        // TODO add your handling code here:+
        if(Bnew.isEnabled()){            
            setDatosSus();
        }
    }//GEN-LAST:event_tablasustancesMousePressed

    private void tablasustancesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasustancesKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 27){  
            setNull();
            tablasustances.clearSelection();
            this.requestFocus();
            Bedit.setEnabled(false); Belimina.setEnabled(false); 
        }
        if(Bnew.isEnabled() && evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){            
            setDatosSus();
        }
    }//GEN-LAST:event_tablasustancesKeyPressed

    private void tablasustancesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablasustancesFocusGained
        // TODO add your handling code here:
        Bedit.setEnabled(true); Belimina.setEnabled(true);
    }//GEN-LAST:event_tablasustancesFocusGained

    private void buscarInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarInvActionPerformed
        // TODO add your handling code here:
        buscaInventario();
    }//GEN-LAST:event_buscarInvActionPerformed

    private void codigoBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscaKeyPressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_codigoBuscaKeyPressed

    private void codigoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoBuscaActionPerformed
        // TODO add your handling code here:
        buscaInventario();
    }//GEN-LAST:event_codigoBuscaActionPerformed

    private void codigoBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoBuscaFocusGained
        // TODO add your handling code here:
        if(!Bcancel.isEnabled()){
            if(codigoBusca.getText().equals("Buscar ...")){            
                codigoBusca.setText("");
            }
            jTable3.clearSelection();
            tablasustances.clearSelection();
        }
    }//GEN-LAST:event_codigoBuscaFocusGained

    private void codigoBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscaKeyReleased
        // TODO add your handling code here:
        int i= jTabbedPane1.getSelectedIndex();
        if(evt.getKeyCode() == 27){
            codigoBusca.setText("");
            Bnew.requestFocus();
        }
        
        if(!Bcancel.isEnabled()){
        
            if(codigoBusca.getText().length() ==0 ){
                if(i == 0)
                    setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products"+ 
                                           " LEFT JOIN marca ON products.marca_id = marca.id ");
                else 
                    setTableSustances("SELECT *FROM substances ");
            }
        }
    }//GEN-LAST:event_codigoBuscaKeyReleased

    private void codigoBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoBuscaFocusLost
        // TODO add your handling code here:
        if(codigoBusca.getText().equals("")){            
                codigoBusca.setText("Buscar ...");
        }
    }//GEN-LAST:event_codigoBuscaFocusLost

    private void addCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCActionPerformed
        // TODO add your handling code here:
        elimina.setEnabled(false);
        addPM.setName("category");
        setTableMP("category");
        JOptionPane.showMessageDialog(this, addPM,"Presenteciones",JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_addCActionPerformed

    private void agregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregaActionPerformed
        // TODO add your handling code here:
        tabalMP.clearSelection();
        tabalMP.setEnabled(false);
        nombreMP.setEnabled(true);
        elimina.setEnabled(false);
        cancela.setEnabled(true);
        guarda.setEnabled(true);
        nombreMP.requestFocus();
        
        agrega.setEnabled(false);
        
        nombreMP.requestFocus();
    }//GEN-LAST:event_agregaActionPerformed

    private void tabalMPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabalMPMousePressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_tabalMPMousePressed

    private void tabalMPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabalMPFocusGained
        // TODO add your handling code here:
        elimina.setEnabled(true);
    }//GEN-LAST:event_tabalMPFocusGained

    private void cancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelaActionPerformed
        // TODO add your handling code here:
        
        tabalMP.setEnabled(true);
        nombreMP.setEnabled(false);
        elimina.setEnabled(false);
        cancela.setEnabled(false);
        guarda.setEnabled(false);
        agrega.setEnabled(true);
        nombreMP.setText("");
        jLabel9.setForeground(Color.black);
        
    }//GEN-LAST:event_cancelaActionPerformed

    private void guardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardaActionPerformed
        // TODO add your handling code here:
        if(nombreMP.getText().length() == 0  ) { 
            jLabel9.setForeground(Color.red);
            jLabel19.setText("Favor de ingresar datos correctos."); JOptionPane.showMessageDialog(this, jLabel19);
            nombre.requestFocus();
        
        } else { jLabel9.setForeground(Color.black); 
        
        Conexion con = new Conexion();
            con.execute("INSERT INTO "+addPM.getName()+" VALUES(null,'"+nombreMP.getText()+"' , null)");        
        con.desconectar();
        setTableMP(addPM.getName());
        
        tabalMP.setEnabled(true);
        nombreMP.setEnabled(false);
        elimina.setEnabled(false);
        cancela.setEnabled(false);
        guarda.setEnabled(false);
        agrega.setEnabled(true);
        nombreMP.setText("");
        
        setBox();
        
        }
        
    }//GEN-LAST:event_guardaActionPerformed

    private void eliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminaActionPerformed
        // TODO add your handling code here:
        Conexion con = new Conexion();
            con.execute("DELETE FROM "+addPM.getName()+" WHERE id="+tabalMP.getValueAt(tabalMP.getSelectedRow(), 0));        
        con.desconectar();
        setTableMP(addPM.getName());
        
        tabalMP.setEnabled(true);
        nombreMP.setEnabled(false);
        elimina.setEnabled(false);
        cancela.setEnabled(false);
        guarda.setEnabled(false);
        agrega.setEnabled(true);
        nombreMP.setText("");
        setBox();
    }//GEN-LAST:event_eliminaActionPerformed

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField2FocusLost

    private void buscarproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarproductActionPerformed
        // TODO add your handling code here:
        buscaProducto();
        jTextField2.requestFocus();
    }//GEN-LAST:event_buscarproductActionPerformed

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        // TODO add your handling code here:
        jTable2.clearSelection();
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        if(jTextField2.getText().length() > 0  || jTextField2.getText().length() == 0){
            buscaProducto();        
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTable2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable2FocusGained
        // TODO add your handling code here:
        jLabel13.setText(" F3 - Propiedades del producto.                                       Agregar producto - Doble click");
    }//GEN-LAST:event_jTable2FocusGained

    private void jTable2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable2FocusLost
        // TODO add your handling code here:
        jLabel13.setText("");
    }//GEN-LAST:event_jTable2FocusLost

    private void TableVentaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TableVentaFocusGained
        // TODO add your handling code here:
        delete.setEnabled(true);
        TableVenta.setRowSelectionInterval(0, 0);
        codigo.requestFocus();
    }//GEN-LAST:event_TableVentaFocusGained

    private void TableVentaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TableVentaFocusLost
        // TODO add your handling code here:
        codigo.requestFocus();
    }//GEN-LAST:event_TableVentaFocusLost

    private void activas1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activas1MousePressed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_activas1MousePressed

    private void activas1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_activas1ValueChanged
        // TODO add your handling code here:

      

    }//GEN-LAST:event_activas1ValueChanged

    private void productos1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_productos1ValueChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_productos1ValueChanged

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
       if(evt.getKeyCode() == 114){
           if(jTable2.getSelectedRow() >= 0 )   setPropiedades((int) jTable2.getValueAt(jTable2.getSelectedRow(), 0)); 
       }
       
       /*if(evt.getKeyCode() == 10){       
                
       }*/
       
       
    }//GEN-LAST:event_jTable2KeyPressed

    private void productos1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productos1MousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_productos1MousePressed

    private void productos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productos1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int id=0;
            System.err.println(""+productos1.getSelectedValue().toString());
           Conexion con = new Conexion();
            ResultSet rs = con.select("SELECT id FROM products WHERE name='"+productos1.getSelectedValue().toString()+"'"); 
            try { 
                while(rs.next()){
                    id = rs.getInt(1);
                    Window w = SwingUtilities.getWindowAncestor(productosPanel);
                    if (w != null) { 
                        w.setVisible(false);
                    }
                    setPropiedades(id); 
                }
            }catch(Exception ex){}
             
        }
    }//GEN-LAST:event_productos1MouseClicked

    private void activas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activas1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int id=0;
            Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT *FROM substances WHERE name='"+activas1.getSelectedValue().toString()+"'"); 
        try { 
            while(rs.next()){
                id=rs.getInt(1);
                nombreA.setText(rs.getString(2));
                detalladaA.setText(rs.getString(3));
            }}catch(Exception e){}
            JOptionPane.showMessageDialog(this, activasPanel2,"Propiedades de sustancia",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_activas1MouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        
        
        char c = evt.getKeyChar();
        
        float cambio =-Tventa;
            if(!jTextField1.getText().equals("")){
                cambio = Float.parseFloat(jTextField1.getText()) - Tventa;
                
        }
        
        if(c == evt.VK_ENTER){
            
            if(cambio >= 0){ 
                Window w = SwingUtilities.getWindowAncestor(jTextField1);
                if (w != null) { 
                    w.setVisible(false);
                    terminarVenta();
                    ImageIcon icono = new ImageIcon ("src/iconos/cobrar.png");         
                    JOptionPane.showMessageDialog(this, "Gracias por su compra.","Vuelva pronto !!",0, new ImageIcon(icono.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
                }
            }else{                
                jLabel7.setText("Falta efectivo: $ "+(cambio*-1));            
            }
        }
        
        
        if (((c < '0') || (c > '9')) && (c != evt.VK_BACK_SPACE) && (c != '.')) {
            getToolkit().beep(); 
            evt.consume();
        }
        if (c == '.' && jTextField1.getText().contains(".")) {
            getToolkit().beep(); 
            evt.consume();
        }

        
                        
                       
        
        
        
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        float cambio =-Tventa;
            if(!jTextField1.getText().equals("")){
                cambio = Float.parseFloat(jTextField1.getText()) - Tventa;
                
        }
            
        if(cambio < 0){ jLabel7.setText("Falta efectivo: $ "+(cambio*-1)); }
        else jLabel7.setText("Su cambio: $ "+cambio); 
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void TcantiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TcantiKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(Tcanti.getText().length() == 7){
            evt.consume(); getToolkit().beep();
        }else
        if (((c < '0') || (c > '9')) && (c != evt.VK_BACK_SPACE) ) {
            getToolkit().beep(); 
            evt.consume();
        }
    }//GEN-LAST:event_TcantiKeyTyped

    private void TcostoproveeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TcostoproveeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(Tcostoprovee.getText().length() == 7){
            evt.consume(); getToolkit().beep();
        }else{
            if (((c < '0') || (c > '9')) && (c != evt.VK_BACK_SPACE) && (c != '.')) {
                getToolkit().beep(); 
                evt.consume();
            }
            if (c == '.' && Tcostoprovee.getText().contains(".")) {
                getToolkit().beep(); 
                evt.consume();
            }
        }
    }//GEN-LAST:event_TcostoproveeKeyTyped

    private void TcostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TcostoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Tcosto.getText().length() == 7){
            evt.consume(); getToolkit().beep();
        }else{
            if (((c < '0') || (c > '9')) && (c != evt.VK_BACK_SPACE) && (c != '.')) {
                getToolkit().beep(); 
                evt.consume();
            }
            if (c == '.' && Tcosto.getText().contains(".")) {
                getToolkit().beep(); 
                evt.consume();
            }
        }
    }//GEN-LAST:event_TcostoKeyTyped

    private void TcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TcodigoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();  
        if(Tcodigo.getText().length() == 10){
            evt.consume(); getToolkit().beep();
        }else        
          if(!Character.isLetter(c)) { 
              getToolkit().beep();                
              evt.consume();                
          } 
    }//GEN-LAST:event_TcodigoKeyTyped

    private void userNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNewActionPerformed
        // TODO add your handling code here:
        addUsuario(0);
        
    }//GEN-LAST:event_userNewActionPerformed

    
    public void addUsuario(int a){
    
        if(a == 0){
        jLabel19.setText(" ");
        
        tablaUsuario.clearSelection();
        nombre1.setEditable(true);
        nombre1.setText(""); direccion.setText(""); telefono.setText("");
        correo.setText(""); name.setText(""); passs.setText("");
        jRadioButton1.setSelected(true);
        
        jLabel90.setForeground(Color.black);
        jLabel91.setForeground(Color.black);
        jLabel92.setForeground(Color.black);
        jLabel93.setForeground(Color.black);
        jLabel94.setForeground(Color.black);
        jLabel95.setForeground(Color.black);
        
        
        }
        
        int se = -1;
        se= JOptionPane.showOptionDialog(
                        this, Usuarios, "Nuevo usuario",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null,    // null para icono por defecto.
                        new Object[] { "Guardar", "Cancelar"},   // null para YES, NO y CANCEL
                        "");
        
        int t = -1;
        if(nombre1.getText().length() == 0  ) { jLabel90.setForeground(Color.red); t = 0;} else { jLabel90.setForeground(Color.black); }
        if(direccion.getText().length() == 0  ){ jLabel91.setForeground(Color.red); t = 0;} else { jLabel91.setForeground(Color.black); }
        if(telefono.getText().length() == 0  ) { jLabel92.setForeground(Color.red); t = 0;} else { jLabel92.setForeground(Color.black); }
        if(correo.getText().length() == 0  ) { jLabel93.setForeground(Color.red); t = 0;} else { jLabel93.setForeground(Color.black); }
        if(name.getText().length() == 0  ) { jLabel94.setForeground(Color.red); t = 0;} else { jLabel94.setForeground(Color.black); }
        if(passs.getText().length() == 0  ){ jLabel95.setForeground(Color.red); t = 0;} else { jLabel95.setForeground(Color.black); if(t==-1) t=1; }
                                
                
        
        
        
        if(se == 0 && t==1){
        
            Conexion con = new Conexion();
            
            int tipo = 0;            
            if(jRadioButton1.isSelected()) tipo = 1;
            
            
            String nombre = nombre1.getText(), direc = direccion.getText(), tele= telefono.getText(), 
                            corr = correo.getText(), usu=name.getText(), pass = passs.getText() ;
            boolean res = con.execute("INSERT INTO users VALUES(null,'"+nombre+"','"+direc+"','"+tele+"','"+corr+"','"+usu+"','"+pass+"',"+tipo+")");
            setTableUser();
            if(res) JOptionPane.showMessageDialog(this, "Datos guardados correctamente");   
            
            combo(usuarios,con.select("SELECT *FROM users"));
        
            
            con.desconectar();
        }else {
            if(se == 0){
                jLabel19.setText("Favor de ingresar datos correctos.");
                addUsuario(1);
            }
        }
    
    
    }
    
    
    private void tablaUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaUsuarioFocusGained
        // TODO add your handling code here:
        int id = (int) tablaUsuario.getValueAt(tablaUsuario.getSelectedRow(), 0);
        userEdi.setEnabled(true);
        userEli.setEnabled(true);
        
    }//GEN-LAST:event_tablaUsuarioFocusGained

    private void tablaUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaUsuarioFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaUsuarioFocusLost

    private void tablaUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaUsuarioKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==27){            
            userEdi.setEnabled(false);
            userEli.setEnabled(false);
            tablaUsuario.clearSelection();
            userNew.requestFocus();
        }
    }//GEN-LAST:event_tablaUsuarioKeyReleased

    private void userEdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEdiActionPerformed
        // TODO add your handling code here:
        edditUsuario(0);
        
    }//GEN-LAST:event_userEdiActionPerformed

    int id2 = 0;
    
    public void edditUsuario(int a){
        
      if(a == 0){
         
        int id = (int) tablaUsuario.getValueAt(tablaUsuario.getSelectedRow(), 0);
        setDatosUser(id);
        
        id2 = id;
        if(id == 1){  name.setEditable(false);  jRadioButton3.setEnabled(false); }
        else  { name.setEditable(true);  jRadioButton3.setEnabled(true);}
        
        nombre1.setEditable(false);
          
        jLabel90.setForeground(Color.black);
        jLabel91.setForeground(Color.black);
        jLabel92.setForeground(Color.black);
        jLabel93.setForeground(Color.black);
        jLabel94.setForeground(Color.black);
        jLabel95.setForeground(Color.black);
        
        
        
        }
        
        
        int se = -1;
        
        
        
       
        
        
        se = JOptionPane.showOptionDialog(
                        this, Usuarios, "Editar usuario",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null,    // null para icono por defecto.
                        new Object[] { "Guardar", "Cancelar"},   // null para YES, NO y CANCEL
                        "");
        
        
        int t = -1;
        if(nombre1.getText().length() == 0  ) { jLabel90.setForeground(Color.red); t = 0;} else { jLabel90.setForeground(Color.black); }
        if(direccion.getText().length() == 0  ){ jLabel91.setForeground(Color.red); t = 0;} else { jLabel91.setForeground(Color.black); }
        if(telefono.getText().length() == 0  ) { jLabel92.setForeground(Color.red); t = 0;} else { jLabel92.setForeground(Color.black); }
        if(correo.getText().length() == 0  ) { jLabel93.setForeground(Color.red); t = 0;} else { jLabel93.setForeground(Color.black); }
        if(name.getText().length() == 0  ) { jLabel94.setForeground(Color.red); t = 0;} else { jLabel94.setForeground(Color.black); }
        if(passs.getText().length() == 0  ){ jLabel95.setForeground(Color.red); t = 0;} else { jLabel95.setForeground(Color.black); if(t==-1) t=1; }
                                
                
        
        
        
        if(se == 0 && t==1){
            
            int tipo = 0;            
            if(jRadioButton1.isSelected()) tipo = 1;
            
            Conexion con = new Conexion();
            boolean res = con.execute("UPDATE users SET  addres='"+direccion.getText()+"',phone='"+telefono.getText()
                        +"',email='"+correo.getText()+"',user='"+name.getText()+"',pass='"+passs.getText()+"', role="+tipo+" WHERE id="+id2);
           
            if(res){ JOptionPane.showMessageDialog(this, "Datos guardados correctamente");  setTableUser(); }            
        }else {
            if(se == 0){
                jLabel19.setText("Favor de ingresar datos correctos.");
                edditUsuario(1);
            }
        }
        nombre1.setEditable(true);
        name.setEditable(true);
        jRadioButton3.setEnabled(true);
    
    
    }
    
    
    
    
    private void userEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEliActionPerformed
        // TODO add your handling code here:
         int id = (int) tablaUsuario.getValueAt(tablaUsuario.getSelectedRow(), 0);
       
         if(id!=1){
        Conexion con = new Conexion();
        int n = JOptionPane.showConfirmDialog(this,"Eliminar cuenta de usuario de "+tablaUsuario.getValueAt(tablaUsuario.getSelectedRow(), 1).toString()+" ?", "Usuarios", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE );
                    if(n==0){                                                    
                        con.execute("DELETE FROM users WHERE id ="+id);
                        setTableUser();
                    }
         }else JOptionPane.showMessageDialog(this, "No se puede eliminar esta cuenta");
    }//GEN-LAST:event_userEliActionPerformed

    private void tablaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuarioMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaUsuarioMouseClicked

    private void helpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpMousePressed
        // TODO add your handling code here:
        //help.setOpaque(true);
        //help.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        //invent.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  invent.setOpaque(false);
        //venta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));   venta.setOpaque(false);
        //config.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));  config.setOpaque(false);
        //remPanel(ayuda);
        JOptionPane.showMessageDialog(this, "Developing..."," Ayuda",JOptionPane.PLAIN_MESSAGE);
        
    }//GEN-LAST:event_helpMousePressed

    public void setTeImagen(){

        ImageIcon icono = new ImageIcon ("src/iconos/cobrar.png");
        cobrar.setIcon(new ImageIcon(icono.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));
    
    
    }
    
    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_exitMousePressed

    private void exitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseEntered
        // TODO add your handling code here:
        exit.setForeground(Color.RED);
    }//GEN-LAST:event_exitMouseEntered

    private void exitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseExited
        // TODO add your handling code here:
        exit.setForeground(Color.BLACK);
    }//GEN-LAST:event_exitMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Info inf = new Info("Terminal_PV","Terminal punto de venta 'Farmacia' \nTechno-World \nVersión 1.0","www.google.com","floresmiranda1593@gmail.com",new ImageIcon ("src/iconos/icono.jpg"));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyTyped
        // TODO add your handling code here:
        
        char c = evt.getKeyChar();
        
        if(telefono.getText().length() == 10){
            evt.consume(); getToolkit().beep();
        } else
        if ( !Character.isDigit(c) ) {
            getToolkit().beep(); 
            evt.consume();
        }
    }//GEN-LAST:event_telefonoKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
     
        if(jRadioButton2.isSelected() && jRadioButton4.isSelected()){
           String fecha  = ((JTextField)cal1.getDateEditor().getUiComponent()).getText();
           String user = ((ComboItem)usuarios.getSelectedItem()).getValue();             
           setTableHisto("SELECT users.full_name, sales.time, sales.products, sales.total FROM sales LEFT JOIN users ON users.id = sales.user_id WHERE sales.time LIKE '"+fecha+"%'  AND  users.id ="+user+"");
        
        }else{
        
            if(jRadioButton2.isSelected()){
                String fecha  = ((JTextField)cal1.getDateEditor().getUiComponent()).getText(); 
                setTableHisto("SELECT users.full_name, sales.time, sales.products, sales.total FROM sales LEFT JOIN users ON users.id = sales.user_id WHERE sales.time LIKE '"+fecha+"%' ");
            }else
            if(jRadioButton4.isSelected()){
                String user = ((ComboItem)usuarios.getSelectedItem()).getValue();                 
                setTableHisto("SELECT users.full_name, sales.time, sales.products, sales.total FROM sales LEFT JOIN users ON users.id = sales.user_id WHERE users.id ="+user+"");
            }else
               setTableHisto("SELECT users.full_name, sales.time, sales.products, sales.total FROM sales LEFT JOIN users ON users.id = sales.user_id ");
        
        }
       
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jRadioButton2PropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jRadioButton2PropertyChange

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton2.isSelected()) cal1.setEnabled(true);
            else  cal1.setEnabled(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton4.isSelected()) usuarios.setEnabled(true);
          else  usuarios.setEnabled(false);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        // TODO add your handling code here:
        if(jTabbedPane2.getSelectedIndex() == 1){
             setBox();
        }
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void codigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyTyped
        // TODO add your handling code here:
        if(codigo.getText().length() == 15){
            evt.consume(); getToolkit().beep();
        } 
    }//GEN-LAST:event_codigoKeyTyped

    private void TskuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TskuKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar(); 
        
        if(Tsku.getText().length() == 15){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                 evt.consume(); getToolkit().beep();
            }
        
       

        
    }//GEN-LAST:event_TskuKeyTyped

    private void TnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TnameKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        
        if(Tname.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                  if(c != KeyEvent.VK_SPACE){
                    evt.consume(); getToolkit().beep();
                }
            } 
        
       
        
    }//GEN-LAST:event_TnameKeyTyped

    private void TestanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TestanteKeyTyped
        // TODO add your handling code here:
        
        char c=evt.getKeyChar();
        
        if(Testante.getText().length() == 5){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                 evt.consume(); getToolkit().beep();
            } 
        
    }//GEN-LAST:event_TestanteKeyTyped

    private void TdescripKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TdescripKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        
        if(Tdescrip.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                if(c != KeyEvent.VK_SPACE){
                    evt.consume(); getToolkit().beep();
                }
            } 
    }//GEN-LAST:event_TdescripKeyTyped

    private void susNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_susNameKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        
        if(susName.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                    evt.consume(); getToolkit().beep();
            } 
        
    }//GEN-LAST:event_susNameKeyTyped

    private void codigoBuscaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoBuscaKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        
        if(codigoBusca.getText().length() == 20){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                    evt.consume(); getToolkit().beep();
            } 
    }//GEN-LAST:event_codigoBuscaKeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        
        char c=evt.getKeyChar();
        if(jTextField2.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }
        
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void nombreMPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreMPKeyTyped
        // TODO add your handling code here:}
        char c=evt.getKeyChar();
        if(nombreMP.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                    evt.consume(); getToolkit().beep();
            }
    }//GEN-LAST:event_nombreMPKeyTyped

    private void nombre1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombre1KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        
        if(nombre1.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) ){
                if(c != KeyEvent.VK_ESCAPE )
                    evt.consume(); getToolkit().beep();
            }
    }//GEN-LAST:event_nombre1KeyTyped

    private void direccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyTyped
        // TODO add your handling code here:
       char c=evt.getKeyChar();
       
         if(direccion.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                if(c != KeyEvent.VK_ESCAPE )
                    evt.consume(); getToolkit().beep();
            }
        
        
    }//GEN-LAST:event_direccionKeyTyped

    private void correoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_correoKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
       
         if(correo.getText().length() == 30){
            evt.consume(); getToolkit().beep();
        }
        
    }//GEN-LAST:event_correoKeyTyped

    private void nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
       
         if(name.getText().length() == 10){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) ){                
                    evt.consume(); getToolkit().beep();
            }
    }//GEN-LAST:event_nameKeyTyped

    private void passsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passsKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
       
         if(passs.getText().length() == 10){
            evt.consume(); getToolkit().beep();
        }else
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                
                    evt.consume(); getToolkit().beep();
            }
    }//GEN-LAST:event_passsKeyTyped

    
    public void cobrarVenta(){
    
        JOptionPane Cventa = null;
        TableVenta.clearSelection();
        
        ImageIcon icono = new ImageIcon ("src/iconos/cobrar.png");
        jLabel3.setText(tventa.getText());
        jLabel7.setText("Falta efectivo: $ "+Tventa);
        jTextField1.setText("");
        int se = Cventa.showOptionDialog(
                        this, Cobrar, "Cobrar venta",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null,    // null para icono por defecto.
                        new Object[] { "Cobrar", "Cancelar"},   // null para YES, NO y CANCEL
                        "");
        float cambio =-Tventa;
        if(!jTextField1.getText().equals("")){
            cambio = Float.parseFloat(jTextField1.getText()) - Tventa;
        }
 
        
        if( cambio >= 0 ){
            if (se == 0){
                terminarVenta();            
            }  
        }else
            if (se == 0){
                JOptionPane.showMessageDialog(this, "La compra no puede ser concretada \nPor favor ingrese la cantidad de efectivo correcta");
            }
    
    
    }
    
    public void eliminaArt(){
    
        Conexion con = new Conexion();
        if(-1 < TableVenta.getSelectedRow()){  
            int n = JOptionPane.showConfirmDialog(this,"Eliminar articulo de la venta?", "Venta", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE );
                    if(n==0){
                        String cc = TableVenta.getValueAt(TableVenta.getSelectedRow(), 0).toString();
                        int cant = Integer.parseInt(TableVenta.getValueAt(TableVenta.getSelectedRow(), 3).toString());
                        int exist=0, id=0;
                                                
                        ResultSet rs = con.select("SELECT id, cuantity FROM products WHERE sku ='"+cc+"'"); 
                        try { 
                            while(rs.next()){    
                                id = rs.getInt(1);
                               exist = rs.getInt(2);
                            }
                        }catch(Exception e){             
                        }
                        
                        con.execute("UPDATE products SET cuantity="+(cant+exist)+" WHERE sku ='"+cc+"'");                        
                        
                        DefaultTableModel modelo = (DefaultTableModel)TableVenta.getModel(); 
                        modelo.removeRow(TableVenta.getSelectedRow());
                        
                        con.execute("DELETE FROM content_sales WHERE product_id ="+id);                        
                        totalVenta();
                        con.execute("UPDATE sales SET products="+Tart+", total="+Tventa+" WHERE id ="+idVenta+"");
                                                
                    }else {
                        //TableVenta.clearSelection();
                        codigo.requestFocusInWindow();
                        
                    } 
            
            if( TableVenta.getRowCount() == 0){
               jPanel3.setVisible(false); 
               con.execute("DELETE FROM sales WHERE id ="+idVenta);
               idVenta=0;
            }
            
            con.desconectar();
           
        }
    
    
    }
    
    
    
     public void setPropiedades(int id) {
        String namee ="";
        Conexion con = new Conexion();
        ResultSet rs = con.select("SELECT products.id, products.sku, products.name, products.cost_sale, products.cost_code, products.cuantity,  products.expiration,  products.location, marca.name,  category.name, products.description, products.details  FROM products " +
                                  "LEFT JOIN marca ON products.marca_id = marca.id	LEFT JOIN category ON products.category_id = category.id WHERE products.id="+id); 
        try { 
            while(rs.next()){
                nombre.setName(""+id);
                namee = rs.getString(3);
                nombre.setText("Nombre: "+rs.getString(3));
                precio.setText("Precio: $"+rs.getString(4)); cc.setText("C.C: "+rs.getString(5)); cantidad.setText("Cantidad: "+rs.getString(6));
                fecha.setText("Caducidad: "+rs.getString(7)); locali.setText("Estante: "+rs.getString(8));
                tmarca.setText("Marca: "+rs.getString(9)); present.setText("Presentacion: "+rs.getString(10));
                descri.setText("Descripcion: "+rs.getString(11)); detallada.setText(rs.getString(12));
                
            } 
            
            rs = con.select("SELECT  substances.id, substances.name FROM substances_product " +
                            "LEFT JOIN substances ON substances_product.substance_id = substances.id " +
                            "WHERE product_id ="+id);

            DefaultListModel model = new DefaultListModel();
            while(rs.next()){ 
                model.addElement(rs.getString(2));
            }
            
            activas1.setModel(model);
            ArrayList lista = new ArrayList();
            
            for (int i = 0; i < activas1.getModel().getSize(); i++) {
                boolean a = false;
                rs = con.select("SELECT products.name FROM products" +
                                "LEFT JOIN substances_product ON products.id = substances_product.product_id " +
                                "LEFT JOIN substances ON substances.id = substances_product.substance_id WHERE substances.name = '"+activas1.getModel().getElementAt(i).toString()+"'");
                while(rs.next()){
                    for (int j = 0; j < lista.size(); j++) {
                        if(lista.get(j).equals(rs.getString(1)) ){
                           a = true;  break;
                        }                            
                    }                    
                    if(a == false && !rs.getString(1).equals(namee) ) lista.add(rs.getString(1));
                }               
                
            }
            
            DefaultListModel listModel = new DefaultListModel();
            for(int i=0; i<lista.size(); i++) {
                //Añadir cada elemento del ArrayList en el modelo de la lista
                listModel.add(i, lista.get(i));
            }
            //Asociar el modelo de lista al JList
            productos1.setModel(listModel);
            
            
            
            
            
        }catch(SQLException ex){
            System.err.println(""+ex.getMessage());
        }               
        con.desconectar();
        propiedades.add(productosPanel);
        
        JOptionPane.showMessageDialog(this, propiedades,"Propiedades de producto",JOptionPane.PLAIN_MESSAGE);
       }
   
    
    public void buscaProducto(){

        setTableArt("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity, products.image FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id WHERE  products.name LIKE '"+jTextField2.getText().toString()+"%' OR products.sku LIKE '"+jTextField2.getText().toString()+"%'");

    
    }
    
    public void buscaInventario(){
    
        if(!Bcancel.isEnabled()){
        
        int i= jTabbedPane1.getSelectedIndex();   
        if(i == 0)       
        setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products"+ 
                                   " LEFT JOIN marca ON products.marca_id = marca.id WHERE  products.name LIKE '"+codigoBusca.getText().toString()+"%' OR products.sku LIKE '"+codigoBusca.getText().toString()+"%'");
        else 
            setTableSustances("SELECT *FROM substances WHERE name LIKE '"+codigoBusca.getText().toString()+"%'");
        
        codigoBusca.requestFocus();
        }
    
    }

    public void insertDatos(){
        
        
        BufferedImage newImg = null;
        String imgstr = null;
        Conexion con = new Conexion(); 
        int t=-1;
      
        
        
        String sku = Tsku.getText(), name = Tname.getText(), marca="-1", categoria="-1", cadu="",
               descrip = Tdescrip.getText(), detalles = Tdetalles.getText(), cantidad = Tcanti.getText(), costo = Tcosto.getText(), ccosto= Tcodigo.getText(),
               cprove = Tcostoprovee.getText(), estante = Testante.getText(), imagen = Bfoto.getName();
        
        try{
            String formato = Ccadu.getDateFormatString();
            Date date = Ccadu.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            cadu = String.valueOf(sdf.format(date));
            System.err.println(""+cadu);
        }catch(Exception e){}
        
        try{ marca = ((ComboItem)Cmarca.getSelectedItem()).getValue();}catch(Exception e){}
        try{categoria = ((ComboItem)Ccatego.getSelectedItem()).getValue(); }catch(Exception e){}
        
        int m = Cmarca.getItemCount(), c = Ccatego.getItemCount();
        
        if(m == 0  ) { Lmarca.setForeground(Color.red); t = 0;} else { Lmarca.setForeground(Color.black); }
        if(c == 0  ) { Lpresen.setForeground(Color.red); t = 0;} else { Lpresen.setForeground(Color.black); }
        
        if(sku.length() == 0  ) { Lsku.setForeground(Color.red); t = 0;} else { Lsku.setForeground(Color.black); }
        if(name.length() == 0  ) { Lname.setForeground(Color.red); t = 0;} else { Lname.setForeground(Color.black); }
        if(cadu.length() == 0  ) { Lcadu.setForeground(Color.red); t = 0;} else { Lcadu.setForeground(Color.black); }
        if(descrip.length() == 0  ) { Ldescrip.setForeground(Color.red); t = 0;} else { Ldescrip.setForeground(Color.black); }
        if(cantidad.length() == 0  ) { Lcanti.setForeground(Color.red); t = 0;} else { Lcanti.setForeground(Color.black); }
        if(costo.length() == 0  ) { Lcosto.setForeground(Color.red); t = 0;} else { Lcosto.setForeground(Color.black); }
        if(ccosto.length() == 0  ) { Lcodigocosto.setForeground(Color.red); t = 0;} else { Lcodigocosto.setForeground(Color.black); }
        if(cprove.length() == 0  ) { Lprovee.setForeground(Color.red); t = 0;} else { Lprovee.setForeground(Color.black); }       
        if(estante.length() == 0  ){ Lestatnte.setForeground(Color.red); t = 0; } else { Lestatnte.setForeground(Color.black); if(t==-1) t=1; }
    
            
        try {
            if(imagen == null){            
                BufferedImage img = ImageIO.read(new File("src/iconos/image.png"));                
                imgstr = encodeToString(img, "png");
            }else{
                BufferedImage img = ImageIO.read(new File(imagen));               
                imgstr = encodeToString(img, "png");
            }
            
                        
            
        } catch (IOException ex) {
        }
       
        
        if(t==1){
        
            boolean resp = false;
        
        if(jTable3.getSelectedRow() > -1){
            String id =  jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString();
            con.execute("DELETE FROM substances_product WHERE product_id="+id);
            resp = con.execute("UPDATE products SET sku ='"+sku+"', name='"+name+"', description='"+descrip+"', details='"+detalles+"',expiration='"+cadu+"', location='"+estante+"', category_id="+categoria+", marca_id="+marca+",image='"+imgstr+"',cost_code='"+ccosto+"',cost_provider="+cprove+", cost_sale="+costo+", cuantity="+cantidad+" WHERE id="+id+";");  
        
        }else {
            
            System.err.println("New");           
            resp =  con.execute("INSERT INTO products VALUES (null,'"+sku+"','"+name+"','"+descrip+"','"+detalles+"','"+cadu+"','"+estante+"',"+categoria+","+marca+",'"+imgstr+"','"+ccosto+"',"+cprove+","+costo+","+cantidad+");");
        
        }
        
        if(resp) JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
        
        int id=0;
        ResultSet rs = con.select("SELECT id FROM products WHERE sku='"+sku+"'");
            try { 
                while(rs.next()){ id = rs.getInt(1); }
            }catch(Exception ex){}
        
        for (int i = 0; i < sustances.getRowCount(); i++) {
            try{
                if(sustances.getValueAt(i, 2).toString().equals("true")){
                    con.execute("INSERT INTO substances_product VALUES(null,"+id+","+sustances.getValueAt(i, 0).toString()+")");
                }
            }catch(Exception e){}
        }
            
        con.desconectar();
        setEnableContainer(Inventario, false);
        setNull();
        setTableArt("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity, products.image FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
        
        setTableArtInv("SELECT products.id, products.sku, products.name, products.description, marca.name , products.cost_sale, products.cuantity FROM products " +
                                   " LEFT JOIN marca ON products.marca_id = marca.id");
  
        }else  {
            jLabel19.setText("Favor de ingresar datos correctos."); JOptionPane.showMessageDialog(this, jLabel19);
        }

    }
    
    public void insertSust(){
        
        String nameSu = susName.getText(), descrip = susDescrip.getText();
        int t = -1;
        
        if(nameSu.length() == 0  ) { jLabel11.setForeground(Color.red); t = 0;} else { jLabel11.setForeground(Color.black); }       
        if(descrip.length() == 0  ){ jLabel12.setForeground(Color.red); t = 0; } else { jLabel12.setForeground(Color.black); if(t==-1) t=1; }
        
        boolean resp = false;
          
        if(t==1){
        
        
        Conexion con = new Conexion();
        
        if(tablasustances.getSelectedRow() > -1){
            String id =  tablasustances.getValueAt(tablasustances.getSelectedRow(), 0).toString();
            resp = con.execute("UPDATE substances SET name ='"+nameSu+"', description='"+descrip+"' WHERE id="+id+";");  
        }else {
            
            System.err.println("New");           
            resp = con.execute("INSERT INTO substances VALUES (null,'"+nameSu+"','"+descrip+"');");
        
        }
        
        if(resp) JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
        
        setEnableContainer(Inventario, false);
        setNull();
        setTableSustances("SELECT *FROM substances");
        setTableSus();
        
        }else  {
            jLabel19.setText("Favor de ingresar datos correctos."); JOptionPane.showMessageDialog(this, jLabel19);
        }
    
    }
    
    public void terminarVenta(){
        Conexion con = new Conexion();
        con.execute("UPDATE sales SET status=1 WHERE id="+idVenta);
        Clear_Table(TableVenta);
        totalVenta();
        jPanel3.setVisible(false);
        con.desconectar();
        idVenta = 0; 
        codigo.requestFocus();
    }
    
    public void validaForm(){
        
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcancel;
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Belimina;
    private javax.swing.JButton Bfoto;
    private javax.swing.JButton Bnew;
    private javax.swing.JButton Bsave;
    private com.toedter.calendar.JDateChooser Ccadu;
    private javax.swing.JComboBox<String> Ccatego;
    private javax.swing.JComboBox<String> Cmarca;
    private javax.swing.JPanel Cobrar;
    private javax.swing.JPanel Configuracion;
    private javax.swing.JPanel Inventario;
    private javax.swing.JLabel Lcadu;
    private javax.swing.JLabel Lcanti;
    private javax.swing.JLabel Lcodigocosto;
    private javax.swing.JLabel Lcosto;
    private javax.swing.JLabel Ldescrip;
    private javax.swing.JLabel Ldetalles;
    private javax.swing.JLabel Lestatnte;
    private javax.swing.JLabel Lmarca;
    private javax.swing.JLabel Lname;
    private javax.swing.JLabel Lpresen;
    private javax.swing.JLabel Lprovee;
    private javax.swing.JLabel Lsku;
    private javax.swing.JPanel Productos;
    private javax.swing.JPanel Sustancia;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTextField Tcadu;
    private javax.swing.JTextField Tcanti;
    private javax.swing.JTextField Tcodigo;
    private javax.swing.JTextField Tcosto;
    private javax.swing.JTextField Tcostoprovee;
    private javax.swing.JTextField Tdescrip;
    private javax.swing.JTextArea Tdetalles;
    private javax.swing.JTextField Testante;
    private javax.swing.JTextField Tname;
    private javax.swing.JTextField Tsku;
    private javax.swing.JPanel Usuarios;
    private javax.swing.JPanel Venta;
    private javax.swing.JList activas1;
    private javax.swing.JPanel activasPanel2;
    private javax.swing.JButton addC;
    private javax.swing.JButton addM;
    private javax.swing.JPanel addPM;
    private javax.swing.JButton addS;
    private javax.swing.JButton agrega;
    private javax.swing.JPanel ayuda;
    private javax.swing.JButton buscarInv;
    private javax.swing.JButton buscarproduct;
    private com.toedter.calendar.JDateChooser cal1;
    private javax.swing.JButton cancel;
    private javax.swing.JButton cancela;
    private javax.swing.JLabel cantidad;
    private javax.swing.JLabel cc;
    private javax.swing.JButton cobrar;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField codigoBusca;
    private javax.swing.JLabel config;
    private javax.swing.JPanel contenedor;
    private javax.swing.JTextField correo;
    private javax.swing.JButton delete;
    private javax.swing.JLabel descri;
    private javax.swing.JTextArea detallada;
    private javax.swing.JTextArea detalladaA;
    private javax.swing.JTextField direccion;
    private javax.swing.JButton elimina;
    private javax.swing.JButton etc;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel fecha;
    private javax.swing.ButtonGroup grupo1;
    private javax.swing.JButton guarda;
    private javax.swing.JLabel help;
    private javax.swing.JLabel invent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton lista;
    private javax.swing.JList<String> listasus;
    private javax.swing.JLabel locali;
    private javax.swing.JLabel m1;
    private javax.swing.JLabel m2;
    private javax.swing.JLabel m3;
    private javax.swing.JLabel m4;
    private javax.swing.JLabel m5;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameUser;
    private javax.swing.JLabel nameUser2;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nombre1;
    private javax.swing.JLabel nombreA;
    private javax.swing.JTextField nombreMP;
    private javax.swing.JTextField passs;
    private javax.swing.JLabel precio;
    private javax.swing.JLabel present;
    private javax.swing.JList productos1;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JPanel propiedades;
    private javax.swing.JButton search;
    private javax.swing.JTextArea susDescrip;
    private javax.swing.JTextField susName;
    private javax.swing.JTable sustances;
    private javax.swing.JTable tabalMP;
    private javax.swing.JTable tablaUsuario;
    private javax.swing.JTable tablasustances;
    private javax.swing.JTextField telefono;
    private javax.swing.JLabel tmarca;
    private javax.swing.JLabel tventa;
    private javax.swing.JButton userEdi;
    private javax.swing.JButton userEli;
    private javax.swing.JButton userNew;
    private javax.swing.JComboBox<String> usuarios;
    private javax.swing.JLabel venta;
    // End of variables declaration//GEN-END:variables

   
    
}
