import java.sql.*;
import java.awt.Font;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


//root用户才会有的界面
public class Signin extends JPanel implements ActionListener{

	//直接创建标签和组件就行了，不用再创建Panel了
	
	//声明链接数据库对象
	Connection connection;
	
	//声明mysql语句对象
	Statement statement;
	
	Label label1 = new Label("对一般用户的注册");
    Label name=new Label("用户名");
    Label password=new Label("密 码");
    Label grade = new Label("用户等级：");
	JButton button1 = new JButton("注册");
	JTextField text1 = new JTextField(15);
	JTextField text2 = new JTextField(15);
	JTextField text3 = new JTextField(15);
	Label label2 = new Label("输入不能为空");
	Label label3 = new Label("输入不能为空");
	//用户之间会有等级的划分
	Label label4 = new Label("1 ~ 3");
	
	public Signin(){
		try{
			System.out.println("进来啦～～");
			SInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SInit() throws SQLException{
		
		//连接数据库
		connection = Connectmysql.getConn();
		statement = connection.createStatement();
		
		//框架布局
		this.setLayout(null);
		
		//设置组件
		label1.setFont(new java.awt.Font("黑体", Font.BOLD, 20));
		name.setBounds(new Rectangle(21, 50, 64, 24));
		password.setBounds(new Rectangle(21, 117, 64, 24));
		grade.setBounds(new Rectangle(21, 170, 64, 24));
	
		text1.setBounds(new Rectangle(102, 50, 200, 30));
		text2.setBounds(new Rectangle(102, 117, 200, 30));
		text3.setBounds(new Rectangle(102, 170, 200, 30));
		
		label2.setBounds(new Rectangle(240,50, 100, 30));
		label3.setBounds(new Rectangle(240, 117, 100, 30));
		label4.setBounds(new Rectangle(240, 170, 100, 30));
		
		button1.setBounds(new Rectangle(150, 260, 60, 30));
			
		button1.addActionListener(this);
		
		System.out.println("初始化啦～～");
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(name);
		this.add(password);
		this.add(grade);
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(button1);
		
	}

	@Override
	public void actionPerformed(ActionEvent Event) {
		// TODO Auto-generated method stub
		System.out.println("进入监听状态～");
		String username = text1.getText();
		String passwd = text2.getText();
		String grade = text3.getText();
		
		System.out.println(username + "\t" + passwd);
		try{
		//	statement.execute("set dateformat ymd");
		
		//	System.out.println("cui");
			statement.executeUpdate("insert into Login values('" + username + "','" + passwd + "',"+ grade +");");
			JOptionPane.showMessageDialog(null, "注册成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
		
			//清空编辑行
			text1.setText("");
			text2.setText("");
			text3.setText("");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,  "注册失败", "系统提示", JOptionPane.ERROR_MESSAGE);
	           e.printStackTrace();
	    }
	      
	}
}
