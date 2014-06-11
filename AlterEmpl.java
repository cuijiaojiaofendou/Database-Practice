
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
import javax.swing.JPanel;
import javax.swing.JTextField;

//添加员工信息
class AlterEmpl extends JPanel implements ActionListener{

	//声明链接数据库对象
	Connection connection;
		
	//声明mysql语句对象
	Statement statement;
	
	Label label0 = new Label("员工号：");
	Label label4 = new Label("电话:");
	Label label6 = new Label("职位:");
	Label label7 = new Label("部门id:");
	
	Label sign0 = new Label("格式必须为:E****,eg:E0009");
	Label sign7 = new Label("格式必须为：w***, eg:w001");
	
	JTextField text0 = new JTextField(15);
	JTextField text4 = new JTextField(15);
	JTextField text6 = new JTextField(15);
	JTextField text7 = new JTextField(15);
	
	JButton button = new JButton("确认修改");
	
	public AlterEmpl(){
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
		label0.setBounds(new Rectangle(25, 50, 64, 30));
		label4.setBounds(new Rectangle(25, 100, 64, 30));
		label6.setBounds(new Rectangle(25, 150, 64, 30));
		label7.setBounds(new Rectangle(25, 200, 64, 30));
		
		sign0.setBounds(new Rectangle(210, 50, 200, 30));
		sign7.setBounds(new Rectangle(210, 200, 200, 30));
		
		text0.setBounds(new Rectangle(100, 50, 110, 30));
		text4.setBounds(new Rectangle(100, 100, 110, 30));
		text6.setBounds(new Rectangle(100, 150, 110, 30));
		text7.setBounds(new Rectangle(100, 200, 110, 30));
		
		button.setBounds(new Rectangle(150, 260, 150, 30));
		
		button.addActionListener(this);
		
		this.add(label0);
		this.add(label4);
		this.add(label6);
		this.add(label7);
		this.add(text0);
		this.add(text4);
		this.add(text6);
		this.add(text7);
		this.add(sign0);
		this.add(sign7);
		this.add(button);
	}
	@Override
	public void actionPerformed(ActionEvent Event) {
		// TODO Auto-generated method stub
		String id = text0.getText();
		String tel = text4.getText();
		String position = text6.getText();
		String workid = text7.getText();
		System.out.println("id = " + id + "tel = " + tel + "position = " + position + "workid = " + workid);
		int flag = 0;
		try{
			
			ResultSet rs = statement.executeQuery("select id from Employee where id = '" + id + "';" );
			while(rs.next()){
				flag = 1;
			}
			if (flag == 1){
				if (tel != ""){
					System.out.println("aaa");
					statement.executeUpdate("Update Employee set tel = '" + tel + "' where id = '" + id + "';" );
				}
				if (position.equals("") == false){
					System.out.println("bbb");
					statement.executeUpdate("Update Employee set position = '" + position +"' where id = '" + id + "';");
					//statement.executeUpdate("Update Employee set position = '" + position + "' where id = '" + id + "';" );
				}
				if (workid.equals("") == false){
					System.out.println("ccc");
					statement.executeUpdate("Update Employee set workid = '" + workid + "' where id = '" + id + "';" );
				}
				//修改完信息后相应的工资也会发生改变，所以，在修改完信息后要重新计算总工资
				statement.execute("update Employee set allmoney = (select sewage from Seniority where Seniority.worktime = Employee.worktime) + " +
						"					(select basicwage from Workinfo where Workinfo.workid = Employee.workid ) +  " +
						"					(select wage from Postsalary where Postsalary.position = Employee.position) + " +
						"					(select rewardmoney from Lncentivepay where Lncentivepay.id = Employee.id);");
				System.out.println("修改用户： ");
				JOptionPane.showMessageDialog(null, "修改成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("我就是我");
			} else {
				JOptionPane.showMessageDialog(this, "不存在该工号，无法修改");
			}
			text0.setText("");
	
			text4.setText("");
			text6.setText("");
			text7.setText("");
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,  "修改失败，请查证后填入信息", "系统提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}

