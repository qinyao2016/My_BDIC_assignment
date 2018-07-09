import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Main {
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "gdbsxwqj";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/assignment";
	public static Connection getConn() throws SQLException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		return conn;
	}

	public static int addMumber(Connection c) throws SQLException {
		System.out.println("you are entering a new mumber");
		Scanner a = new Scanner(System.in);
		System.out.println("Please enter Mumbership Number: ");
		int mumbershipNo = a.nextInt();
		System.out.println("Please enter Address: ");
		String address = a.next();
		System.out.println("Please enter First Name: ");
		String firstName = a.next();
		System.out.println("Please enter Last Name: ");
		String lastName = a.next();
		System.out.println("Please enter telephone number: ");
		String telNo = a.next();
		System.out.println("Please enter join date: ");
		String joinDate = a.next();
		Statement st = c.createStatement();
		int rs = st.executeUpdate("INSERT INTO mumbers VALUES (" + mumbershipNo + ",'" + address + "','" + firstName + "','" + lastName + "'," + telNo + ", '" + joinDate + "');");
		st.close();
		return rs;
	}
	public static int addCar(Connection c) throws SQLException {
		System.out.println("you are entering a new car");
		Scanner a = new Scanner(System.in);
		System.out.println("Please enter Registration Number: ");
		int RegistrationNumber = a.nextInt();
		System.out.println("Please enter Product Date: ");
		String ProductDate = a.next();
		System.out.println("Please enter Price per day: ");
		int PricePerDay = a.nextInt();
		System.out.println("Please enter Mileage in total: ");
		Double MileageInTotal = a.nextDouble();
		System.out.println("Please enter Model Name: ");
		String ModelName = a.next();
		System.out.println("Please enter Buy Expenditure: ");
		int BuyExpenditure = 0;
		String nullBuy = null;
		if(a.hasNextInt()) {
			BuyExpenditure = a.nextInt();
		}
		else{
			nullBuy = a.next();
		}
		System.out.println("Please enter Buy Date: ");
		String BuyDate = a.next();
		Statement st = c.createStatement();
		int rs = 0;
		if(nullBuy == null) {
			 rs = st.executeUpdate("INSERT INTO car VALUES (" + RegistrationNumber + ",'" + ProductDate + "'," + PricePerDay + "," + MileageInTotal + ",'" + ModelName + "'," + BuyExpenditure + ", '" + BuyDate + "');");
		}
		//BuyExpenditure and ProductDate are null,which means the car has not been sold
		else {
			 rs = st.executeUpdate("INSERT INTO car VALUES (" + RegistrationNumber + ",'" + ProductDate + "'," + PricePerDay + "," + MileageInTotal + ",'" + ModelName + "', null , null);");
		}
		st.close();
		return rs;
	}
	public static void findCar(Connection c) throws SQLException {
		System.out.println("you are finding a car");
		Scanner a = new Scanner(System.in);
		System.out.println("Please enter registration number :");
		int number = a.nextInt();
		PreparedStatement ps = c.prepareStatement("select * from car where RegistrationNumber = ?;");
		ps.setInt(1,number);
		ps.executeQuery();
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
		   int RegistrationNumber = rs.getInt("RegistrationNumber");
		   String ProductDate = rs.getString("ProductDate");
		   int PricePerDay = rs.getInt("PricePerDay");
		   double MileageInTotal = rs.getDouble("MileageInTotal");
		   String ModelName = rs.getString("ModelName");
		   int BuyExpenditure = rs.getInt("BuyExpenditure");
		   String BuyDate = rs.getString("BuyDate");
		   if(BuyDate == null)
			   System.out.println(RegistrationNumber + "\t" + ProductDate + "\t" + PricePerDay + "\t" +  MileageInTotal + " \t" + ModelName + "\tnull\tnull");
		   else{
			   System.out.println(RegistrationNumber + "\t" + ProductDate + "\t" + PricePerDay + "\t" +  MileageInTotal + "\t" + ModelName + "\t" + BuyExpenditure + "\t" + BuyDate);
		   }
		}
		rs.close();
		ps.close();
	}
	public static void findAvailableCar(Connection c) throws SQLException {
		System.out.println("you are finding all available cars");
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery("select * from car inner join status using(RegistrationNumber)  where status = 'available_rent'");
		while (rs.next()){
		   int RegistrationNumber = rs.getInt("RegistrationNumber");
		   int PricePerDay = rs.getInt("PricePerDay");
		   double MileageInTotal = rs.getDouble("MileageInTotal");
		   String ModelName = rs.getString("ModelName");
		   int BuyExpenditure = rs.getInt("BuyExpenditure");
		   String BuyDate = rs.getString("BuyDate");
		   String ProductDate = rs.getString("ProductDate");
		   String Status = rs.getString("Status");
		   System.out.println(RegistrationNumber + "\t" + PricePerDay + "\t" +  MileageInTotal + "\t" + ModelName + "\t" + BuyExpenditure + "\t" + BuyDate + "\t" + ProductDate + "\t" + Status);
		}
		rs.close();
		st.close();
	}
	public static void main(String[] args) throws SQLException {
		Connection conn = getConn();
		
		System.out.println("Connected to MySQL server!");
		
		addMumber(conn);
		addCar(conn);
		findCar(conn);
		findAvailableCar(conn);
		
		conn.close();
		
	}
}
