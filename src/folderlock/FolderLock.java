
package FolderLock;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jamshed Azhar
 */
public class FolderLock extends  javax.swing.JFrame {    
 private JPanel gui;
 static final String regNeo="\"HKLM\\SOFTWARE\\NEO SOFTWARE\\NEO LOCK\"";
 static String pass="",hint="";
 static void jam(){
    Process ty;
    InputStream is;
     try {
         ty=Runtime.getRuntime().exec("REG QUERY "+regNeo+" /v Password");
         is=ty.getInputStream();
         StringBuilder s=new StringBuilder(); int c;
        while((c=is.read())!=-1){
            s.append((char)c); }
        pass=s.substring(s.indexOf("REG_SZ")+6).trim();
        ty=Runtime.getRuntime().exec("REG QUERY "+regNeo+" /v Hint");
         is=ty.getInputStream();
         s=new StringBuilder();
        while((c=is.read())!=-1){
            s.append((char)c); }
        hint=s.substring(s.indexOf("REG_SZ")+6).trim();
        }
      catch (Exception ex) {
     }
}
    public FolderLock() {        
        initComponents();
        if(pass.length()>0){
        jLabel2.setVisible(false);
        jTextField1.setVisible(false);
        }
        else {
        jButton3.setVisible(false);
        jLabel2.setVisible(true);
        jTextField1.setVisible(true);
        jButton2.setText("Create Password");
        jButton2.setToolTipText("Click to create Password");
        jLabel2.setText("Password Hint");
        }   
    }
    private void initComponents() 
    {
        setLocation(300,150);
        gui = new JPanel(new BorderLayout(3,3));
        gui.setBorder(new EmptyBorder(5,5,5,5));
        gui.setBackground(Color.red);
        ImageIcon lp = new ImageIcon(this.getClass().getResource("folder_lock.png"));        
        setIconImage(lp.getImage());
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3= new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Neo Folder Lock:  "+compName);
        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 0, 0));
        jButton1.setMnemonic('e');
        jButton1.setText("Exit");
        jButton1.setToolTipText("Click to Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.setBounds(480, 383, 150, 60);
        jDesktopPane1.setBackground(Color.DARK_GRAY);
        jDesktopPane1.add(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 0, 0));
        jButton2.setMnemonic('l');
        jButton2.setText("Login");
        jButton2.setToolTipText("Click to Login");
        jButton2.setAlignmentY(jButton1.getAlignmentX());
        jButton2.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.setBounds(240, 383, 240, 60);
        jDesktopPane1.add(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(204, 0, 0));
        jButton3.setMnemonic('p');
        jButton3.setText("Password Hint");
        jButton3.setAlignmentY(0);
        jButton3.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.setBounds(0, 383, 240, 60);
        jDesktopPane1.add(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Enter Password");
        jLabel1.setBounds(80, 40, 170, 50);
        jDesktopPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        addWindowListener( new WindowAdapter() {
    @Override
    public void windowOpened( WindowEvent e ){
        jPasswordField1.requestFocus();
    }
}); 
        jPasswordField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(0, 204, 0));
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });
        jPasswordField1.setBounds(230, 50, 330, 40);
        jDesktopPane1.add(jPasswordField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
      //  jLabel3.setText("Welcome   "+compName);
        jLabel3.setBounds(80, 180, 300, 50);
        jDesktopPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password Hint");
        jLabel2.setVisible(false);
        jLabel2.setBounds(80, 110, 180, 50);
        jDesktopPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(204, 0, 0));
        jTextField1.setBounds(230, 120, 330, 40);
        jTextField1.setVisible(false);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jDesktopPane1.add(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );

        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {     
        try {
            //show Security tab
                s1123=Runtime.getRuntime().exec("CMD /C REG ADD HKCU\\SOFTWARE\\MICROSOFT\\WINDOWS\\CURRENTVERSION\\POLICIES\\EXPLORER /V NOSECURITYTAB /T REG_DWORD /D 0 /F");
             }
        catch (Exception ex) {
            }
    System.exit(0);
    } 
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
     String ch=jPasswordField1.getText();
     String hi=jTextField1.getText();
    if(pass.length()==0)  {
         if(ch.length()<5){
         showThrowable1("Password must contain atleast 5 characters.","Error");
         }
         else if(hi.length()<1){ 
         showThrowable1("Enter password hint","Error");
         }
         else{
         try {             
                Runtime.getRuntime().exec("REG ADD "+regNeo+" /v Password /d "+ch+" /f").waitFor();
                Runtime.getRuntime().exec("CMD /C REG ADD "+regNeo+" /v Hint /d \""+hi+"\" /f").waitFor();
                showThrowable1("Password Created Sucessfully.","Sucess");
                setVisible(false);
                FileBrowser.eND();
        } 
         catch (IOException | InterruptedException ex) {
            showThrowable1("I/O  Error","Error");
        }}}
    else  {
                if( pass.equals(ch)){
                 setVisible(false);
                 FileBrowser.eND();
                }
                else if( pass.equalsIgnoreCase(ch))                {   
                showThrowable1("Password is CASE SENSITIVE","Error");
                }
                else {
                showThrowable1("Incorrect Password","Error");
                }}   }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {  
                showThrowable1(hint,"Password Hint");
    }         
    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {  
       if(evt.getKeyCode()==10){ //Enter Key
           java.awt.event.ActionEvent evt1=null;
           jButton2ActionPerformed(evt1);
         }         
       else if(evt.getKeyCode()==32){   //Space Key
                    showThrowable1("Password Must not contain SPACE.","Warning");
                     jPasswordField1.setText(null);
                 }
    }             
    
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {
       if(evt.getKeyCode()==10){
            java.awt.event.ActionEvent evt1=null;
         jButton2ActionPerformed(evt1);
         }
    }          
    static Process s1123;
    static String compName;
    static    {  
        try   {
                compName=InetAddress.getLocalHost().getHostName();              
                Runtime.getRuntime().exec("REG ADD HKCU\\SOFTWARE\\MICROSOFT\\WINDOWS\\CURRENTVERSION\\POLICIES\\EXPLORER /V NOSECURITYTAB /T REG_DWORD /D 1 /F");
                Runtime.getRuntime().exec("REG ADD HKCU\\SOFTWARE\\POLICIES\\MICROSOFT\\WINDOWS\\SYSTEM /V DISABLECMD /T REG_DWORD /D 0 /F");
                Runtime.getRuntime().exec("REG ADD "+regNeo+" /f");
                Runtime.getRuntime().exec("REG ADD "+regNeo+" /v Password ");
                Runtime.getRuntime().exec("REG ADD "+regNeo+" /v Hint ");
                Runtime.getRuntime().exec("CMD /C Taskkill /IM reg.exe /F");
                jam();
        }
        catch (Exception ex)   {
            showThrowable1("Error Opening File","Error");
        }
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FolderLock().setVisible(true);
            }
        });
    }
      private static void  showThrowable1(String t,String j)
      {             
        JOptionPane.showMessageDialog(null,t,"Neo Lock: "+j,0);
      }
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
}
