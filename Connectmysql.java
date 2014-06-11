import java.sql.*;

//创建数据库的链接类
public class Connectmysql {
	
	public static Connection getConn() throws SQLException{
		String url1 = "jdbc:mysql://127.0.0.1:3306/Intership";// JDBC ODBC 桥连接mysql 数据库
	    String username="root";
	    String password="1";
	    
	    try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	return DriverManager.getConnection(url1,username,password);
	
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }catch(ClassNotFoundException e){
	    	e.printStackTrace();
	    }
		return null;
	   }
}
