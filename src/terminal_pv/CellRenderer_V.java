package terminal_pv;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static terminal_pv.Start.decodeToImage;

public class CellRenderer_V extends DefaultTableCellRenderer{
    private Font bold = new Font( "Arial",Font.BOLD ,14 );
    
    int action=0;
    public CellRenderer_V(int action) {
        this.action = action;
    }
        
    @Override
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column )
    {
    
    if(action == -1){
        
        String text = value.toString();
       
        BufferedImage  newImg = decodeToImage(text);
       
        JLabel label = new JLabel();
        label.setOpaque(true);
        
        ImageIcon icono = null;
        
         try {
             try{
                 icono = new ImageIcon (newImg); 
             }catch(Exception ex){
                 icono = new ImageIcon ("src/iconos/image.png");
             }
  
           //label.setIcon(new ImageIcon(icono.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));            

        } catch (Exception ex) {
            System.out.println(ex);
        }
         // This also not working.....    
        if (selected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        } 
        
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(bold);
        
        if(selected) table.changeSelection(row,table.getColumnCount()-1,true,true);
        
        return label;
    
    
    }else{ 
        
        if(action==4){
            
            
            
            if( value == null ){
                this.setText( null );            
            }else{
                this.setText( "" +value.toString()); 
            }        
            this.setBackground((selected)? new Color(0,0,255):new Color(175,255,125));
            if(value.toString().equals("0"))
                this.setForeground( (selected)? new Color(255,0,0):new Color(255,0,0) );
            else
                this.setForeground( (selected)? new Color(255,255,255):new Color(0,0,0) );
            
        }
        
        
        if(action==3){    
            if( value == null ){
                this.setText( null );            
            }else{
                this.setText( "" +value.toString());  
            }        
            this.setBackground((selected)? new Color(0,0,255):new Color(255,255,255));
            if(value.toString().equals("0"))
                this.setForeground( (selected)? new Color(255,0,0):new Color(255,0,0) );
            else
                this.setForeground( (selected)? new Color(255,255,255):new Color(0,0,0) );
            
        }
        
        if(action==2){    
            if( value == null ){
                this.setText( null );            
            }else{
                this.setText( "" +value.toString()); 
            }        
            this.setBackground((selected)? new Color(0,0,255):new Color(255,255,255));
            this.setForeground( (selected)? new Color(255,255,255):new Color(0,0,0) );
        }
        
        
        
        
        if(action==1){        
            if( value == null ){
                this.setBackground( new Color(175,255,125) );
                this.setText( null );            
            }else{
                this.setText( "" + value.toString()); 
                this.setBackground((selected)? new Color(0,0,255):new Color(175,255,125));
            }        
            this.setForeground( (selected)? new Color(255,255,255):new Color(0,0,0) );            
        }
        
        if(action==0){    
            if( value == null ){
                this.setText( null );            
            }else{
                this.setText( "" +value.toString());               
            }        
            this.setBackground((selected)? new Color(0,0,255):new Color(255,255,255));
            this.setForeground( (selected)? new Color(255,255,255):new Color(0,0,0) );
        }
        
        this.setFont(bold); 
        if(action!=2) this.setHorizontalAlignment(JLabel.CENTER);
        else this.setHorizontalAlignment(JLabel.LEFT);
            
        if(focused) table.changeSelection(row,table.getColumnCount()-1,true,true);
        
        return this;
      
    }

        
        
        
    }
}
