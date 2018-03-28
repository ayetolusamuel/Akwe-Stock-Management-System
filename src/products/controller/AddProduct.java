package products.controller;

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
public class AddProduct extends JFrame implements ActionListener{
	
	
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
	private JButton btnComfirm;
	
	
	
	public AddProduct() {
		// TODO Auto-generated constructor stub
		
			displayGui();
			//my panel that hold my child view
		
			
			//databaseConnection.open();
		
		
	}
	
	
	
	
	private void displayGui() {
		jPanel = new JPanel();
		jPanel.setLayout(null);
		
		
		lblPCode = new  JLabel("Product Code");
		jPanel.add(lblPCode).setBounds(10, 10, 100, 25);
		
		txtPCode = new JTextField(2);
		String code = "Set0001";
		txtPCode.setText(code);
		txtPCode.setCaretPosition(code.length());
		jPanel.add(txtPCode).setBounds(140,12,80,25);
		
		txtDate = new JTextField(d);
		txtDate.setToolTipText("The current Date!!!!!!");
		jPanel.add(txtDate).setBounds(250,12,80,25);
		
		
		txtTime = new JTextField(timeStamp);
		txtTime.setToolTipText("The current Time!!!!!!!");
		jPanel.add(txtTime).setBounds(360,12,80,25);
		
		btnComfirm = new JButton(new ImageIcon("images//sam.gif"));
		jPanel.add(btnComfirm).setBounds(460,12,37,25);
		btnComfirm.addActionListener(this);
		
		
		
		lblPName= new  JLabel("Product Name");
		jPanel.add(lblPName).setBounds(10, 45, 100, 25);
		
		txtPName = new JTextField();
		jPanel.add(txtPName).setBounds(140,48,350,25);
		
		lblpPrice = new  JLabel("Product Price");
		jPanel.add(lblpPrice).setBounds(10, 80, 100, 25);
		
		txtPPrice = new JTextField();
		jPanel.add(txtPPrice).setBounds(140,82,100,25);
		
		
		lblpPrice = new  JLabel("Platform");
		jPanel.add(lblpPrice).setBounds(250, 80, 100, 25);
		
		jcmbPlatform = new JComboBox<>(platform);
		jPanel.add(jcmbPlatform).setBounds(350,82,170,25);
		
		
		
		lblInStock = new  JLabel("Product In- Stock");
		jPanel.add(lblInStock).setBounds(10, 115, 100, 25);
		
		txtPInStock= new JTextField("5");
		jPanel.add(txtPInStock).setBounds(140,117,100,25);
		
		
		lblInStock = new  JLabel("Payment Mode");
		jPanel.add(lblInStock).setBounds(250, 115, 100, 25);
		
		jcmbPayment = new JComboBox<>(payment);
		jPanel.add(jcmbPayment).setBounds(350,117,170,25);
		
		lblPCategory = new  JLabel("Product Category");
		jPanel.add(lblPCategory).setBounds(10, 145, 100, 25);
		
	/*
		jcmbPDescription = new JComboBox<>(cat);
		jPanel.add(jcmbPDescription).setBounds(140,147,300,25);*/
		

		txtPCategory = new JTextField("null");
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
		
	
		
		JLabel lbldespathName = new JLabel("Despath Name");
		lbldespathName.setToolTipText("Enter Despath name");
		jPanel.add(lbldespathName).setBounds(10,310,350,25);
		
		txtDespatchName = new JTextField("null");
		txtDespatchName.setToolTipText("Enter Despath name");
		jPanel.add(txtDespatchName).setBounds(140,310,350,25);
		
		
		JLabel lblJumiaOrderNumber = new JLabel("Jumia Order Number");
		jPanel.add(lblJumiaOrderNumber).setBounds(10,340,350,25);
		
		
		txtJumiaOrderNumber = new JTextField("null");
		txtJumiaOrderNumber.setToolTipText("Enter Jumia Order Number, must be nine digit");
		jPanel.add(txtJumiaOrderNumber).setBounds(140,342,350,25);
		
		
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
		String payment = jcmbPayment.getSelectedItem().toString();
		String platform = jcmbPlatform.getSelectedItem().toString();
		String despathName = txtDespatchName.getText().toString();
		String orderNumber = txtJumiaOrderNumber.getText().toString();
		
		databaseConnection.open();
		
		PreparedStatement ps;
		  // String sql="INSERT INTO  products(ID,date,time,name,price,instock,category,platform,payment,description,despath,ordernumber)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql="INSERT INTO  trying(ID,date,time)values(?,?,?)";
        
		ps = databaseConnection.getConnection().prepareStatement(sql);
           
           
           ps.setString(1, txtPCode.getText().substring(3, 7));
           ps.setString(2, txtDate.getText());
           ps.setString(3, txtTime.getText());
           
          /* ps.setString(1, txtDate.getText());
           ps.setString(2, txtTime.getText());
           ps.setString(3, txtGeneratorOnDate.getText());
           ps.setString(4, genOn);
           ps.setString(5, genOf);
           ps.setString(6, txtGeneratorRunHrs.getText());
           ps.setString(7, txtNepaOnDate.getText());
           ps.setString(8, nepaOn);
           ps.setString(9, nepaOf);
           ps.setString(10, txtNepaRunHrs.getText());
           ps.setString(11, txtRemark.getText());
           ps.setString(12, (String) jcmbWorking.getSelectedItem());
           ps.setString(13, nepaIntValue);
           ps.setString(14, generatorIntValue);*/
           
      
           
           ps.executeUpdate();
           
           JOptionPane.showMessageDialog(null, "Record inserted successfully");
        
		
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
			
			String query = "Select ID from trying order by ID DESC limit 1";
			
			
			databaseConnection.resultSet = databaseConnection.statement.executeQuery(query);
			
			while (databaseConnection.resultSet.next()) {
				
			 lNumber = databaseConnection.resultSet.getInt(1);
			 
			 String num = String.format("%04d", lNumber);
			 
			 
			// System.out.println(num);
			 
			 
				if (lNumber == 0) {
					txtPCode.setText("SET0001");
						
				}
				else {
					txtPCode.setText("SET"+num);
				}
			 
			  }}
				catch (Exception e) {
					
					e.printStackTrace();
					// TODO: handle exception
				}
		
		
		
		
			}
	
		private void retrieveDetailsBaseID() throws SQLException {
			  String id = txtPCode.getText().toString().substring(3, 7);
			//rs =st.executeQuery("select substring(dategenerated, 1,2)as days  from generatornepa where site like '"+s+"'AND dategenerated like '"+monthValue+"' order by dategenerated DESC limit 1");
			String query = "select * from product_addition  where id like '"+id+"'";
      // String query = "SELECT * FROM id";
       databaseConnection.resultSet = databaseConnection.statement.executeQuery(query);
       while (databaseConnection.resultSet.next()) {
    	   
    	   String date = txtDate.getText().toString();
   		String time = txtTime.getText().toString();
   		String code = databaseConnection.resultSet.getString("id").trim();
   		String name =  databaseConnection.resultSet.getString("name").trim();
   		String description = txtPDescription.getText().toString();
   		String price = txtPPrice.getText().toString();
   		String inStock = txtPInStock.getText().toString();
   		String cat = txtPCategory.getText().toString();

           String id= databaseConnection.resultSet.getString("id").trim();
          
			
       }
			
			
		}
		
		
	
	
	
	
	public static void main(String[] args) {
		
		
		
		
		
		AddProduct addProduct = new AddProduct();
		addProduct.setVisible(true);
		addProduct.setLocation(300,200);
		addProduct.setSize(550,450);
		addProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private String idIncrement(String originalCode)
	{
	     String id = originalCode.substring(3, 7);
	     int i = Integer.parseInt(id);
	     i = i++;
	     
	     String result = String.valueOf(i);
	     if (result.length() == 2 ) 
	    	 originalCode = DEFAULT+"00"+result;
			
	     else if (result.length()==1) {
			originalCode = DEFAULT+"000"+result;
		}
	     
	     txtPCode.setText(originalCode);
	     
	     return originalCode;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		
		if (event.equals(btnComfirm)) {
			
			try {
				retrieveDetailsBaseID();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (event.equals(btnSave)) {
			
	idIncreament();
	   
			
			
//			
//			txtPCode.setText(idIncrement(txtPCode.getText().toString()));
//			
//			System.out.println(idIncrement(txtPCode.getText().toString()));
			/*String id = txtPCode.getText().toString();
			id = id.substring(3, 7);
			
			
		int i = Integer.parseInt(id);
		
		System.out.println(String.format("%04d", i));
		
		
		
		i = i++;
		
		System.out.println(""+i);
		
		
		String incrementResult = "Set"+i;
		System.out.println(incrementResult);
		//txtPCode.setText(String.format("%04d", incrementResult));
			*/
		}
		
		
		// TODO Auto-generated method stub
		
	}

}
