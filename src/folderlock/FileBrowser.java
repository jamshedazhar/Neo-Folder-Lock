package FolderLock;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 *
 * @author Jamshed Azhar
 */

public class FileBrowser {
    static FileSystemView fsv = FileSystemView.getFileSystemView();
//    private static final ResourceBundle bundle = ResourceBundle.getBundle("folderlock/Bundle");
   
    static void e1(){
        try {
            s111 = Runtime.getRuntime().exec("CMD /C WHOAMI");
         Scanner input=new Scanner(new InputStreamReader(s111.getInputStream()));
                 line=input.next();
        } catch (IOException ex) {
           
        }
    } 
    void changeIc(int j,File f){
    try {
        
            String f1=f.getAbsolutePath()+"\\DESKTOP.INI";
            Runtime.getRuntime().exec("CMD /C ECHO [.SHELLCLASSINFO] >\""+f1+"\"");
            Runtime.getRuntime().exec("CMD /C ECHO ICONFILE=%SYSTEMROOT%\\SYSTEM32\\SHELL32.DLL,"+j+">>\""+f1+"\"");
            Runtime.getRuntime().exec("CMD /C ATTRIB +H +R  \""+f1+"\"");
        } catch (Exception ex) {
        }
    }
    void lockAll(File f) {
     if (f.isDirectory()) {
    for (File c : f.listFiles()){
        lockAll(c);
    }}
    try {
        Runtime.getRuntime().exec("CMD /C CACLS \""+f.getAbsolutePath()+"\" /E /C /P "+line+":N");
            Runtime.getRuntime().exec("CMD /C ATTRIB  +H +A +R \""+f.getAbsolutePath()+"\"");
        if(f.getName().equalsIgnoreCase("DESKTOP.INI")){
        Runtime.getRuntime().exec("CMD /C CACLS \""+f.getAbsolutePath()+"\"   /E /C /P "+line+":F");
            Runtime.getRuntime().exec("CMD /C ATTRIB  +H +A +R \""+f.getAbsolutePath()+"\"");
   }} catch (Exception ex) {
   }}
   static  Process s111;
   static   String line,s;        
    static void unLockAll(File c) {
        try { 
            Runtime.getRuntime().exec("CMD /C CACLS \""+c.getAbsolutePath()+"\"   /E /C /P "+line+":F");
            Runtime.getRuntime().exec("CMD /C ATTRIB  -H -A -R \""+c.getAbsolutePath()+"\"");
        } catch (IOException ex) {
        }
        
    if(c.isDirectory()){
            try {
                Process p1=Runtime.getRuntime().exec("CMD /C CACLS \""+c.getAbsolutePath()+"\\*\"   /E /C /P "+line+":F");
                p1.waitFor();
            } catch (IOException | InterruptedException ex) {
            }
          for(File g:c.listFiles()){
              unLockAll(g);
          }}}
    static String compName;
    static       {
        try {
            compName=InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null,"ERROR OPENING.");
        }
    }
    /** Title of the application */
    public static final String APP_TITLE = "FOLDER LOCK: ";
    /** Used to open/edit/print files. */
    private Desktop desktop;
    /** Provides nice icons and names for files. */
    private FileSystemView fileSystemView;
    /** currently selected File. */
    public  File currentFile;
    /** Main GUI container */
    private JPanel gui;
    /** File-system tree. Built Lazily */
    private JTree tree;
    private DefaultTreeModel treeModel;
    /** Directory listing */
    private JTable table;
    private JProgressBar progressBar;
    /** Table model for File[]. */
    private FileTableModel fileTableModel;
    private ListSelectionListener listSelectionListener;
    private boolean cellSizesSet = false;
    private int rowIconPadding = 6;
    private JButton lock4;
    private JButton lock3;
    public Container getGui() {
        if (gui==null) {
            gui = new JPanel(new BorderLayout(3,3));
            gui.setBorder(new EmptyBorder(5,5,5,5));
            fileSystemView = FileSystemView.getFileSystemView();
            desktop = Desktop.getDesktop();
            JPanel detailView = new JPanel(new BorderLayout(3,3));
            table = new JTable();
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoCreateRowSorter(true);
            table.setShowVerticalLines(false);
            listSelectionListener = new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    int row = table.getSelectionModel().getLeadSelectionIndex();
                    setFileDetails( ((FileTableModel)table.getModel()).getFile(row) );
                }
            };
            table.getSelectionModel().addListSelectionListener(listSelectionListener);
            JScrollPane tableScroll = new JScrollPane(table);
            Dimension d = tableScroll.getPreferredSize();
            tableScroll.setPreferredSize(new Dimension((int)d.getWidth()*2, (int)d.getHeight()));
            detailView.add(tableScroll, BorderLayout.CENTER);

            // the File tree
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            treeModel = new DefaultTreeModel(root);

            TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent tse){
                    DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                    showChildren(node);
                    setFileDetails((File)node.getUserObject());
                }
            };

            // show the file system roots.
            File[] roots = File.listRoots();//fileSystemView.getRoots();
            for (File fileSystemRoot : roots) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                root.add( node );
                File[] files = fileSystemView.getFiles(fileSystemRoot, true);
                for (File file : files) {
                    if (file.isDirectory() && file.canRead()) {
                        node.add(new DefaultMutableTreeNode(file));
                    }
                }
            }

            tree = new JTree(treeModel);
            tree.setRootVisible(false);
            tree.addTreeSelectionListener(treeSelectionListener);
            tree.setCellRenderer(new FileTreeCellRenderer());
            tree.expandRow(0);
            JScrollPane treeScroll = new JScrollPane(tree);

            // as per trashgod tip
            tree.setVisibleRowCount(40);

            Dimension preferredSize = treeScroll.getPreferredSize();
            Dimension widePreferred = new Dimension(
                200,
                (int)preferredSize.getHeight());
            treeScroll.setPreferredSize( widePreferred );

            // details for a File
            JPanel fileMainDetails = new JPanel(new BorderLayout(4,2));
            fileMainDetails.setBorder(new EmptyBorder(0,10,0,10));

            JPanel fileDetailsLabels = new JPanel(new GridLayout(0,1,2,2));
            fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

            JPanel fileDetailsValues = new JPanel(new GridLayout(0,1,2,2));
            fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);
            JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING,6,0));
            JToolBar toolBar = new JToolBar();
            // mnemonics stop working in a floated toolbar
            toolBar.setFloatable(false);
            //Mouse Listener in JTree
            table.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     JTable target = (JTable)e.getSource();
                     int row = target.getSelectedRow();
                  //   row=table.getSelectedRow();
                 if (e.getClickCount() == 2) {
                     
                     try {
                         if(currentFile.isFile())
                         {
                         desktop.open(currentFile);
                         }
                     } catch (IOException ex) {
                         
                     }
                 
           //     int row = target.getSelectedRow();
           //     int column = target.getSelectedColumn();
      // do some action if appropriate column
    }
  }
});
            
            
            
            fileDetailsValues.add(flags);

            
            //to lock
            lock3 = new JButton("                                             LOCK   IT (ALT+L)                                                            ");
            lock3.setMnemonic('l');

            lock3.addActionListener(new ActionListener(){
                  @Override
                public void actionPerformed(ActionEvent ae) {
                      Date dt=new Date(currentFile.lastModified());
                      int  st=dt.getYear();
                      st+=1900;
                      if(st==1970){
                        showThrowable1("Already Locked.");
                      } 
                      else{
                      if(currentFile.isDirectory()){
                      changeIc(44,currentFile);
                      }
                      lockAll(currentFile);
                      showThrowable1(currentFile.getName()+" FILE LOCKED SUCESSFULLY.");
                     }
                    gui.repaint();
                  }
            });
            toolBar.add(lock3);
            toolBar.add(new Label("                    :          "));
//end of lock
            
            lock4 = new JButton("                                             UNLOCK   IT (ALT+U)                                                            ");
            lock4.setMnemonic('u');

            lock4.addActionListener(new ActionListener(){
                  @Override
                public void actionPerformed(ActionEvent ae) 
                  {
                      Date dt=new Date(currentFile.lastModified());
                      int  st=dt.getYear();
                      st+=1900;
                      if(st==1970){ 
                          unLockAll(currentFile);                      
                    if(currentFile.isDirectory()){
                    new File(currentFile+"\\"+"DESKTOP.INI").delete();
                    }
                    showThrowable1(currentFile.getName()+" UNLOCKED SUCESSFULLY  ");
                     }
                      else {
                     showThrowable1("ALREADY UNLOCKED.");
                      }
                    gui.repaint();
                }
            });
            toolBar.add(lock4);

            // Check the actions are supported on this platform!
   //         openFile.setEnabled(desktop.isSupported(Desktop.Action.OPEN));
   //         editFile.setEnabled(desktop.isSupported(Desktop.Action.EDIT));
   //         printFile.setEnabled(desktop.isSupported(Desktop.Action.PRINT));
           // lock6.setEnabled(Desktop.isDesktopSupported(Desktop.Action.));
         flags.add(new JLabel("                                                                                                                  MADE BY JAMSHED AZHAR"));
            int count = fileDetailsLabels.getComponentCount();
            for (int ii=0; ii<count; ii++) {
                fileDetailsLabels.getComponent(ii).setEnabled(false);
            }

            count = flags.getComponentCount();
            for (int ii=0; ii<count; ii++) {
                flags.getComponent(ii).setEnabled(false);
            }

            JPanel fileView = new JPanel(new BorderLayout(3,3));

            fileView.add(toolBar,BorderLayout.NORTH);
            fileView.add(fileMainDetails,BorderLayout.CENTER);

            detailView.add(fileView, BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                treeScroll,
                detailView);
            gui.add(splitPane, BorderLayout.CENTER);

            JPanel simpleOutput = new JPanel(new BorderLayout(3,3));
            progressBar = new JProgressBar();
            simpleOutput.add(progressBar, BorderLayout.EAST);
            progressBar.setVisible(false);

            gui.add(simpleOutput, BorderLayout.SOUTH);

        }
        return gui;
    }
    public void showRootFile() {
        // ensure the main files are displayed
        tree.setSelectionInterval(0,0);
    }
    public   void showThrowable1(String t) {
        Date dt1=new Date();
        
        JOptionPane.showMessageDialog(
            gui,
            "\nTIME:   "+dt1.toString(),
            "FILE EXPLORER: "+t,
            JOptionPane.ERROR_MESSAGE
            );
        gui.repaint();
    }
    /** Update the table on the EDT */
    private void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (fileTableModel==null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                if (!cellSizesSet) {
                    Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight( icon.getIconHeight()+rowIconPadding );

                    setColumnWidth(0,-1);
                    setColumnWidth(3,60);
                    table.getColumnModel().getColumn(3).setMaxWidth(120);
                    setColumnWidth(4,-1);
                    cellSizesSet = true;
                }
            }
        });
    }
    private void setColumnWidth(int column, int width) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (width<0) {
            // use the preferred width of the header..
            JLabel label = new JLabel( (String)tableColumn.getHeaderValue() );
            Dimension preferred = label.getPreferredSize();
            // altered 10->14 as per camickr comment.
            width = (int)preferred.getWidth()+14;
        }
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }
    /** Add the files that are contained within the directory of this node.
    Thanks to Hovercraft Full Of Eels for the SwingWorker fix. */
    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground()
            {
                File file = (File) node.getUserObject();
                
                if (file.isDirectory() || file.isHidden()) 
                {
                    
                    File[] files = fileSystemView.getFiles(file, false); //!!
                    
                    if (node.isLeaf())
                    {
                        for (File child : files)
                        {
                            if (child.isDirectory())
                            {
                                publish(child);
                            }
                        }
                    }
                    setTableData(files);
                }   
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }
    /** Update the File details view with the details of this File. */
    private void setFileDetails(File file) {
        currentFile = file;
       
        JFrame f = (JFrame)gui.getTopLevelAncestor();
                f.setLocation(100, 20);
            f.setTitle(
                APP_TITLE +"  "+compName+"  :: " +
                fileSystemView.getSystemDisplayName(file));
        gui.repaint();
    }
    public static void eND() {
       
        e1();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Significantly improves the look of the output in
                    // terms of the file names returned by FileSystemView!
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException weTried) {
                }
                JFrame f = new JFrame(APP_TITLE);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
               

                FileBrowser NewEmpty = new FileBrowser();
                f.setContentPane(NewEmpty.getGui());
                //add icon to application
                try {
                    URL urlBig = NewEmpty.getClass().getResource("ICO.PNG");
                    URL urlSmall = NewEmpty.getClass().getResource("FOLDER_LOCK.PNG");
                    ArrayList<Image> images = new ArrayList<>();
                    images.add( ImageIO.read(urlBig) );
                    images.add( ImageIO.read(urlSmall) );
                    f.setIconImages(images);
                } catch(Exception e) 
                {
                }

                f.pack();
                f.setLocationByPlatform(true);
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
                

                NewEmpty.showRootFile();
            }
        });
    }
}
/** A TableModel to hold File[]. */
class FileTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -6101682212645378856L;
    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
        "ICON",
        "FILE NAME",
      "PATH",
        "SIZE",
     "LAST MODIFIED"
    };
    FileTableModel() {
        this(new File[0]);
    }
    FileTableModel(File[] files) {
        this.files = files;
    }
    @Override
    public Object getValueAt(int row, int column) {
        File file = files[row];
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
                return file.length();
            case 4:
                return file.lastModified();
        }
        return "";
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5: 
                return Boolean.class;
        }
        return String.class;
    }
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public int getRowCount() {
        return files.length;
    }
    public File getFile(int row) {
        return files[row];
    }
    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}

/** A TreeCellRenderer for a File. */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = -7799441088157759804L;
    private FileSystemView fileSystemView;
    private JLabel label;
    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }
    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}
