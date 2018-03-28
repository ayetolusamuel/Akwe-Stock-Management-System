package products.views;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import products.controller.DatabaseConnection;
public class Add extends JFrame implements ActionListener{
	
	
	private JLabel lblPCode,lblPName,lblPDescription,lblpPrice,lblPCategory,lblInStock,lblPlatform,lblPayment;
	private JTextField txtPCode,txtPName,txtPPrice,txtPInStock,txtDate,txtTime;
	private JTextArea txtPDescription;
	private JButton btnSave;
	
	private String[] cat= {"Gadgets & Accessories ",};
	private String[] payment = {"COD","Transfer","Prepaid","Cash","USSD Transfer"};
	private String[] platform = {"Jumia","Konga","Walk - In","Despatch"};
	
	
	private JComboBox<String> jcmbPDescription,jcmbPayment,jcmbPlatform;
	private JPanel jPanel;
	private final static  String  DEFAULT= "SET";
	private static java.util.Date currDate = new java.util.Date ();					//Creating Object.
	private static SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy", Locale.getDefault());	//Changing Format.
	private static String d = sdf.format (currDate);
	static DateTimeFormatter formatter;
	static String timeStamp = new SimpleDateFormat("hh :mm: ss").format(Calendar.getInstance().getTime());

	
	private JTextField txtPCategory;
	private DatabaseConnection databaseConnection = new DatabaseConnection();
	private JTextField txtDespatchName;
	private JTextField txtJumiaOrderNumber;
	
	
	
	public Add() {
		// TODO Auto-generated constructor stub
		
			displayGui();
			//my panel that hold my child view
		
			
			txtPCode.setCaretPosition(7);
		
	}
	
	
	
	
	private void displayGui() {
		jPanel = new JPanel();
		jPanel.setLayout(null);
		
		
		lblPCode = new  JLabel("Product Code");
		jPanel.add(lblPCode).setBounds(10, 10, 100, 25);
		
		txtPCode = new JTextField(2);
		String code = "SET0001";
		txtPCode.setText(code);
		txtPCode.setEditable(false);
		jPanel.add(txtPCode).setBounds(140,12,80,25);
		
		txtDate = new JTextField(d);
		txtDate.setToolTipText("The current Date!!!!!!");
		jPanel.add(txtDate).setBounds(250,12,80,25);
		
		
		txtTime = new JTextField(timeStamp);
		txtTime.setToolTipText("The current Time!!!!!!!");
		jPanel.add(txtTime).setBounds(360,12,80,25);
	
		
		lblPName= new  JLabel("Product Name");
		jPanel.add(lblPName).setBounds(10, 45, 100, 25);
		
		txtPName = new JTextField();
		jPanel.add(txtPName).setBounds(140,48,350,25);
		
		lblpPrice = new  JLabel("Product Price");
		jPanel.add(lblpPrice).setBounds(10, 80, 100, 25);
		
		txtPPrice = new JTextField();
		jPanel.add(txtPPrice).setBounds(140,82,100,25);
		
		
		
		
		lblInStock = new  JLabel("Product In- Stock");
		jPanel.add(lblInStock).setBounds(10, 115, 100, 25);
		
		txtPInStock= new JTextField("5");
		jPanel.add(txtPInStock).setBounds(140,117,100,25);
		
		
	
		
		lblPCategory = new  JLabel("Product Category");
		jPanel.add(lblPCategory).setBounds(10, 145, 100, 25);
		
	/*
		jcmbPDescription = new JComboBox<>(cat);
		jPanel.add(jcmbPDescription).setBounds(140,147,300,25);*/
		

		txtPCategory = new JTextField();
		jPanel.add(txtPCategory).setBounds(140,147,300,25);
		
		
		lblPDescription = new  JLabel("Product Descript.");
		jPanel.add(lblPDescription).setBounds(10, 190, 120, 25);
		
		txtPDescription = new JTextArea();
		txtPDescription.setWrapStyleWord(true);
		txtPDescription.setLineWrap(true);
		
		
		
		JScrollPane jScrollPane = new JScrollPane(txtPDescription);
		jScrollPane.setMaximumSize(getPreferredSize());

		jPanel.add(jScrollPane).setBounds(140,192,300,100);
		
		
		btnSave = new JButton("Save");
		jPanel.add(btnSave).setBounds(10,230,100,25);
		btnSave.addActionListener(this);
		
		
		
		getContentPane().add(jPanel, BorderLayout.CENTER);
		
	
		
	
	databaseConnection.open();
		
		lastIdNumber();
		
		
		
		
		//boolean comfirm = isTextFieldEmpty();
		//System.out.println(comfirm);
	}
	
	
	
	//this method verify the textfield, and return true if all fields are empty
	
	private boolean isTextFieldEmpty() {
		boolean flag = true;
		String code = txtPCode.getText().toString();
		String name = txtPName.getText().toString();
		String description = txtPDescription.getText().toString();
		String price = txtPPrice.getText().toString();
		String inStock = txtPInStock.getText().toString();
		//String cat = jcmbPDescription.getSelectedItem().toString();
		String cat = txtPCategory.getText().toString();
		
		String checkCode  = code.substring(0, 3);
		
		

		if (code.length()==0 || name.length()==0 || description.length()==0 || price.length()==0 || inStock.length()==0 || cat.length()==0 || checkCode.equals("Set")) {
			return flag;
		}
		
		
		return false;
		
	}

	
	private void saveToDatabase() throws SQLException {
		String date = txtDate.getText().toString();
		String time = txtTime.getText().toString();
		String code = txtPCode.getText().toString();
		String name = txtPName.getText().toString();
		String description = txtPDescription.getText().toString();
		String price = txtPPrice.getText().toString();
		String inStock = txtPInStock.getText().toString();
		String cat = txtPCategory.getText().toString();
		
		code = code.substring(3, 7);
		//System.out.println(code);
		if (description.length()>100) {
			
		
		description = description.substring(0, 100)+"......";
		//System.out.println(description);
		
		
		databaseConnection.open();
		
		
		
		PreparedStatement ps;
		  // String sql="INSERT INTO  products(ID,date,time,name,price,instock,category,platform,payment,description,despath,ordernumber)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql="INSERT INTO  product_addition(idnum,date,time,name,description,price,instock,category)values(?,?,?,?,?,?,?,?)";
		
        
		ps = databaseConnection.getConnection().prepareStatement(sql);
           
           
           ps.setString(1, code);
           ps.setString(2, date);
           ps.setString(3, time);
           ps.setString(4, name);
           ps.setString(5, description);
           ps.setString(6, price);
           ps.setString(7, inStock);
           ps.setString(8, cat);
           
           
         ps.executeUpdate();
           
           JOptionPane.showMessageDialog(null, "Record inserted successfully");
           
           txtPName.setText("");
           txtPDescription.setText("");
           txtPPrice.setText("");
           txtPCategory.setText("");
           
           
        
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Product Description length must be greater than 100 character");
		}
	}
	
	
	public void idIncreament() {
	int i =	Integer.parseInt(txtPCode.getText().toString().substring(3, 7));
	
	int result = i+1;
		//System.out.println(result);
		String resultString = Integer.toString(result);
		
		 try {
				saveToDatabase();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		 
	     if (resultString.length() == 2 ) 
	    	 txtPCode.setText(DEFAULT+"00"+result);
	    	
	     else if (resultString.length()==1) {
			txtPCode.setText(DEFAULT+"000"+result);
		}
	     else if (resultString.length() == 3) {
	    	 txtPCode.setText(DEFAULT+"0"+result);
		}
	     else if (resultString.length() == 4) {
	    	 txtPCode.setText(DEFAULT+""+result);
		}
	     else {
	    	// txtPCode.setText(t);
	     }
	     
	    

	
	
	}
	
	

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	void lastIdNumber() {
		int lNumber = 0;
		try {
			
			String query = "Select ID from product_addition order by ID DESC limit 1";
			
			
			databaseConnection.resultSet = databaseConnection.statement.executeQuery(query);
			
		
			
			while (databaseConnection.resultSet.next()) {
				
			 lNumber = databaseConnection.resultSet.getInt(1);
			 //lNumber = lNumber + 1;
			 
		//	 String num = String.format("%04d", lNumber);
			 
			 
		 
			 
				if (lNumber == 0) {
					txtPCode.setText("SET0001");
						
				}
				else {
				//	System.out.println(lNumber);
					int intNum = lNumber;
					intNum = intNum +1;
					String num = String.format("%04d", intNum);
					
					txtPCode.setText("SET"+num);
				}
				
			  }}
				catch (Exception e) {
					
					e.printStackTrace();
					// TODO: handle exception
				}
		
		
		
		
			}
	
		
		
		
	
	
	
	
	public static void main(String[] args) {
		
		
		
		
		
		Add addProduct = new Add();
		addProduct.setVisible(true);
		addProduct.setLocation(300,200);
		addProduct.setSize(550,450);
		addProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		if (event.equals(btnSave)) {
			idIncreament();
		}
		
	}

}
