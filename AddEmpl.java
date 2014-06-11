import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//添加员工信息
class AddEmpl extends JPanel implements ActionListener{

	//声明链接数据库对象
	Connection connection;
		
	//声明mysql语句对象
	Statement statement;
	
	Label label0 = new Label("员工号：");
	Label label1 = new Label("姓名:");
	Label label2 = new Label("性别:");
	Label label3 = new Label("年龄:");
	Label label4 = new Label("电话:");
	Label label5 = new Label("入职时间:");
	Label label6 = new Label("职位:");
	Label label7 = new Label("部门id:");
	
	Label sign0 = new Label("格式必须为:E****,eg:E0009");
	Label sign5 = new Label("填入年份的1月1号，eg:2014-1-1");
	Label sign7 = new Label("格式必须为：w***, eg:w001");
	
	JTextField text0 = new JTextField(15);
	JTextField text1 = new JTextField(15);
	JTextField text2 = new JTextField(15);
	JTextField text3 = new JTextField(15);
	JTextField text4 = new JTextField(15);
	JTextField text5 = new JTextField(15);
	JTextField text6 = new JTextField(15);
	JTextField text7 = new JTextField(15);
	
	JButton button = new JButton("确认添加");
	
	public AddEmpl(){
		try{
			AEInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void AEInit() throws SQLException{
		//连接数据库
		connection = Connectmysql.getConn();
		statement = connection.createStatement();
				
		//框架布局
		this.setLayout(null);
		label0.setBounds(new Rectangle(25, 25, 64, 22));
		label1.setBounds(new Rectangle(25, 50, 64, 22));
		label2.setBounds(new Rectangle(25, 75, 64, 22));
		label3.setBounds(new Rectangle(25, 100, 64, 22));
		label4.setBounds(new Rectangle(25, 125, 64, 22));
		label5.setBounds(new Rectangle(25, 150, 64, 22));
		label6.setBounds(new Rectangle(25, 175, 64, 22));
		label7.setBounds(new Rectangle(25, 200, 64, 22));
		
		sign0.setBounds(new Rectangle(210, 25, 200, 22));
		sign5.setBounds(new Rectangle(210, 150, 200, 22));
		sign7.setBounds(new Rectangle(210, 200, 200, 22));
		
		text0.setBounds(new Rectangle(100, 25, 110, 22));
		text1.setBounds(new Rectangle(100, 50, 110, 22));
		text2.setBounds(new Rectangle(100, 75, 110, 22));
		text3.setBounds(new Rectangle(100, 100, 110, 22));
		text4.setBounds(new Rectangle(100, 125, 110, 22));
		text5.setBounds(new Rectangle(100, 150, 110, 22));
		text6.setBounds(new Rectangle(100, 175, 110, 22));
		text7.setBounds(new Rectangle(100, 200, 110, 22));
		
		button.setBounds(new Rectangle(150, 260, 150, 30));
		
		button.addActionListener(this);
		
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(label5);
		this.add(label6);
		this.add(label7);
		this.add(text0);
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(text5);
		this.add(text6);
		this.add(text7);
		this.add(sign0);
		this.add(sign5);
		this.add(sign7);
		this.add(button);
	}
	@Override
	public void actionPerformed(ActionEvent Event) {
		// TODO Auto-generated method stub
		String id = text0.getText();
		String name = text1.getText();
		String sex = text2.getText();
		String age = text3.getText();
		String tel = text4.getText();
		String worktime = text5.getText();
		String position = text6.getText();
		String workid = text7.getText();
		
		try{
			
			statement.executeUpdate("insert into Employee values('" +id + "','" + name + "','" + sex + "','" + age + 
					"','" + tel + "','" + worktime + "','" + position + "','" + workid +"' , 0);" );
			//statement.executeUpdate("insert into Employee values('E0010', '汪峰', ‘男’, );");
			statement.executeUpdate("insert into Lncentivepay values('" +id + "',0,0,0,0,0,0,0);");
			//对表Lncentivepay中的rewardmoney进行计算
			statement.execute("update Lncentivepay set rewardmoney = (opencalss * (-50)) + (late * (-20)) + (Leavecount * (-10)) + holidayovertime * 100 + reward * 100 + usualovertime * 50;");
			//对表Employee中的allmoney进行计算
			statement.execute("update Employee set allmoney = (select sewage from Seniority where Seniority.worktime = Employee.worktime) + " +
					"					(select basicwage from Workinfo where Workinfo.workid = Employee.workid ) +  " +
					"					(select wage from Postsalary where Postsalary.position = Employee.position) + " +
					"					(select rewardmoney from Lncentivepay where Lncentivepay.id = Employee.id);");
			System.out.println("添加用户： ");
			JOptionPane.showMessageDialog(null, "添加成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("我就是我");
			text0.setText("");
			text1.setText("");
			text2.setText("");
			text3.setText("");
			text4.setText("");
			text5.setText("");
			text6.setText("");
			text7.setText("");
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,  "添加失败，请查证后填入信息", "系统提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
