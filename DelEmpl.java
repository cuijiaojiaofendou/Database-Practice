import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class DelEmpl extends Panel implements ActionListener{

	//声明链接数据库对象
	Connection connection;
		
	//声明mysql语句对象
	Statement statement;
	
	Label label = new Label("员工号:");
	JTextField text = new JTextField(15);
	Label sign = new Label("格式必须为:E****,eg:E0009");
	JButton button = new JButton("确认删除");
	
	public DelEmpl(){
		try{
			DEInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void DEInit() throws SQLException{
		//连接数据库
		connection = Connectmysql.getConn();
		statement = connection.createStatement();
				
		//框架布局
		this.setLayout(null);
		
		label.setBounds(new Rectangle (25, 100, 64, 30));
		text.setBounds(new Rectangle(100, 100, 110, 30));
		sign.setBounds(new Rectangle(220, 100, 200, 30));
		
		button.setBounds(new Rectangle(150, 260, 150, 30));
		
		button.addActionListener(this);
		this.add(label);
		this.add(text);
		this.add(sign);
		this.add(button);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String id = text.getText();
		int flag = 0;
		
		try{

			ResultSet rs = statement.executeQuery("select id from Employee where id = '" + id + "';" );
			System.out.println("1");
			while(rs.next()){
				flag = 1;
			}
			if (flag == 1){	
				statement.executeUpdate("delete from Lncentivepay where id = '" + id + "';" );
				System.out.println("2");
				statement.executeUpdate("Delete from Employee where id = '" + id + "';" );
				System.out.println("3");
				JOptionPane.showMessageDialog(null, "删除成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "不存在该工号，无法删除！");
			}
	
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,  "删除失败，请查证后填入信息", "系统提示", JOptionPane.ERROR_MESSAGE);		
			e.printStackTrace();
		}
	}
}
