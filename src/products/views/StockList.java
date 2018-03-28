package products.views;



import javax.swing.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.plaf.metal.*;

import java.sql.*;
import java.io.*;

import javax.swing.event.*;
import javax.swing.table.*;

import products.controller.DatabaseConnection;

import java.awt.print.*;
class StockList extends JFrame implements ActionListener
{
	JPanel main=new JPanel();
	Container c=getContentPane();
	private JTable table;
	Connection conn;
	Statement st;
	JComboBox cmb;
	JButton print;
	JButton cancle;
	private String msAccDB;
	private DatabaseConnection databaseConnection = new DatabaseConnection();
	private String dbURL;
	StockList()
	{
	
		setSize(880,520);
		setTitle("Staff's List");
		setLocation(240,80);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					/*setVisible(false);
					Home home = new Home();
					home.setVisible(true);
					home.setLocationRelativeTo(null);*/
					
					
				}
			});
		//stafflist.setDefaultLookAndFeelDecorated(true);
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images//mainicon.png"));
		c=getContentPane();

		main.setLayout(new BorderLayout());
		main.setBackground(new Color(245,240,255));
		JLabel l=new JLabel("<html><i><font size=6 color=#800080><i>Staff's List");
		JPanel title=new JPanel()
		{
			public void paintComponent(Graphics g)
			{

				Toolkit kit=Toolkit.getDefaultToolkit();
				Image img=kit.getImage("images//Gradien.jpg");
				
				MediaTracker t=new MediaTracker(this);
				t.addImage(img,1);
				while(true)
				{
					try
					{
						t.waitForID(1);
						break;
					}
					catch(Exception e)
					{
					}
				}
				g.drawImage(img,0,0,1350,50,null);
			}
		};
		//enableVisibility();
		title.add(l);
		main.add("North",title);
		Icon prt=new ImageIcon("images//PRINT.png");
		print=new JButton("Print",prt);
		print.setToolTipText("Print");
		cancle=new JButton("Exit");
		cancle.setToolTipText("Exit");
		print.addActionListener(this);
		cancle.addActionListener(this);
		JPanel butpan=new JPanel();
		butpan.add(print);
		butpan.add(cancle);
		butpan.setBackground(new Color(255,197,68));
		c.add("South",butpan);
		
		try {
			String comfirm = null;
			comfirm = checkDatabase();
			if (comfirm.equals(null)) {
				JOptionPane.showMessageDialog(null, "No data Yet, please register to have access!");
			}
			else if (!comfirm.equals(null)) {
				enableVisibility();
				StockList.this.setVisible(true);
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No data Yet, please to have access!");
		}
		
		
		
	}
	
	


public static void main(String args []){
	StockList sa1=new StockList();
	sa1.setSize(1320, 520);
	sa1.setLocationRelativeTo(null);
	//sa1.setVisible(true);
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	Object source=e.getSource();
	if (source ==cancle) {
		setVisible(false);
		
		
	}
	if (source == print) {
		try {
			boolean complete = table.print();
			if (complete) {
				/* show a success message */

				JOptionPane.showMessageDialog(null,
						"Printing was cancelled");

			} else {
				/* show a message indicating that printing was cancelled */
				JOptionPane.showMessageDialog(null,
						"Printing was cancelled");

			}
		} catch (PrinterException pe) {
			/* Printing failed, report to the user */

			JOptionPane.showMessageDialog(null, "printing failed");
		}
		
	}
	
	
}
private String checkDatabase() throws SQLException{
	databaseConnection.open();
    
    String name = null;
   
   ResultSet rs = databaseConnection.statement.executeQuery("select * from daily_stock_update");
	while(rs.next())
	{
		 name = rs.getString("id").trim();
	
	}
		
	return name;
}




private void compute(){
	try {
		databaseConnection.open();
		//checkDatabase();
		databaseConnection.resultSet = databaseConnection.statement.executeQuery("select * from daily_stock_update ");
		int row=0;
		int i=0;
		while(databaseConnection.resultSet.next())
		{
			row++;
		}
		DefaultTableModel model=new DefaultTableModel(new String[]{"id","Date","time","Platform","Order Num"},row);
		table=new JTable(model);
		
		databaseConnection.resultSet =databaseConnection.statement.executeQuery("select * from daily_stock_update");
		
		
		while(databaseConnection.resultSet.next())
		{
			
			model.setValueAt(databaseConnection.resultSet.getString(1),i,0);
			model.setValueAt(databaseConnection.resultSet.getString(2).trim(),i,1);
			model.setValueAt(databaseConnection.resultSet.getString(3).trim(),i,2);
			model.setValueAt(databaseConnection.resultSet.getString(4).trim(),i,3);
			model.setValueAt(databaseConnection.resultSet.getString(7).trim(),i,4);
			
			
			
			i++;
		}
		table=new JTable(model);
	}
	catch(Exception ex)
	{
	}
}


private void enableVisibility(){
	compute();
	
	JScrollPane sp=new JScrollPane(table);
	main.add(sp);
	table.setSelectionMode(0);
	table.setFont(new Font("Times New Roman",Font.PLAIN,18));
	table.setForeground(Color.black);
	table.setEnabled(false);
	table.setRowHeight(30);
	table.setGridColor(new Color(0,128,192));
    table.getTableHeader().setReorderingAllowed(false);
    c.add(main);
	
	
}}






/*cancle.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		
		setVisible(false);
		Home home = new Home();
		home.setVisible(true);
		home.setLocationRelativeTo(null);
		
	}
});*/
/*print.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		PrinterJob jb=PrinterJob.getPrinterJob();
		jb.printDialog();
		
	}
});*/

/*try {
	databaseConnection();
	ResultSet set=st.executeQuery("select * from registration ");
	int row=0;
	int i=0;
	while(set.next())
	{
		row++;
	}
	DefaultTableModel model=new DefaultTableModel(new String[]{"UserName","Full Name","Email","Phone Number","Position",},row);
	table=new JTable(model);
	set=st.executeQuery("select * from registration");
	while(set.next())
	{
		//model.setValueAt(set.getString(1).trim(),i,0);
		model.setValueAt(set.getString(2).trim(),i,0);
		model.setValueAt(set.getString(4).trim(),i,1);
		model.setValueAt(set.getString(5).trim(),i,2);
		model.setValueAt(set.getString(6).trim(),i,3);
		model.setValueAt(set.getString(7).trim(),i,4);
		
		
		
		i++;
	}
	table=new JTable(model);
}
catch(Exception ex)
{
}*/
/**/





