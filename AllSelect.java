import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class AllSelect extends JPanel implements ActionListener{
	//声明链接数据库对象
	Connection connection;
	//声明mysql语句对象
	Statement statement;
	
	int grade ;	
	
	JButton button1 = new JButton("查询所有员工信息");
	JButton button2 = new JButton("查询以注册用户表");
	JButton button3 = new JButton("查看公司总人数");
	JButton button4 = new JButton("查看所有员工的平均工资");
	JButton button5 = new JButton("查看所有员工加班信息");
	
	public AllSelect(int grade){
		try{
			this.grade = grade;
			Init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void Init() throws SQLException{
		//连接数据库
		connection = Connectmysql.getConn();
		statement = connection.createStatement();
		
		this.setLayout(null);
		button1.setBounds(new Rectangle(25, 25, 200, 30));
		button2.setBounds(new Rectangle(25, 60, 200, 30));
		button3.setBounds(new Rectangle(25, 95, 200, 30));
		button4.setBounds(new Rectangle(25, 130, 200, 30));
		button5.setBounds(new Rectangle(25, 165, 200, 30));
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
	}
	
	public void actionPerformed(ActionEvent Event){

		JFrame jframe = new JFrame();
		//声明一个表
		JTable table;
		JTableHeader jth;
		JScrollPane scrollPane = null;
		DefaultTableModel dtm;
		
		ResultSet rs;
		
		try{
			if (Event.getSource() == button1){
				rs = null;
				String[] Title = {"id","姓名", "性别", "年龄", "电话号码", "入职时间", "工作楼层", "部门id", "个人月总工资"};
				jframe.setSize(600, 400);
				jframe.setLocation(jframe.getSize().width/2, jframe.getSize().height/2);
				
				jframe.setTitle("所有员工信息");
				//链接数据库
				rs = statement.executeQuery("select * from Employee;");
				//通过dtm得到表中的信息
	
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				//首先初始化表的行数，设置行数为0
	
				while(rs.next()){
		
					Object[] row = {rs.getString("id"),
							rs.getString("name"),
							rs.getString("sex"),						
							rs.getString("age"),
							rs.getString("tel"),
							rs.getString("worktime"),
							rs.getString("position"),
							rs.getString("workid"),
							rs.getString("allmoney")};
						dtm.addRow(row);
				}
				scrollPane = new JScrollPane(table);
				jframe.add(scrollPane, BorderLayout.CENTER);
				jframe.setVisible(true);
			}
			if (Event.getSource() == button2 && this.grade == 2){
				
				String []Title = {"用户名", "等级"};
				jframe.setSize(500, 300);
				jframe.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - jframe.getSize().width)/2,
						(Toolkit.getDefaultToolkit().getScreenSize().height - jframe.getSize().height)/2);
				jframe.setTitle("所有注册管理员信息");
				rs = statement.executeQuery("select * from Login;");
				//通过dtm得到表中的信息
	
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				
				while(rs.next()){
					Object[] row = {
							rs.getString("Loginname"),
							rs.getString("Grade")
					};
					dtm.addRow(row);
				}
				scrollPane = new JScrollPane(table);
				jframe.add(scrollPane, BorderLayout.CENTER);
				jframe.setVisible(true);
			}
			if (Event.getSource() == button2 && this.grade == 1){
				String [] Title = {"用户名", "密码","等级"};
				jframe.setSize(500, 300);
				jframe.setLocation(jframe.getSize().width/2, jframe.getSize().height/2);
				jframe.setTitle("所有注册管理员信息");
				
				rs = statement.executeQuery("select * from Login;");
				//通过dtm得到表中的信息
	
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				
				while(rs.next()){
					Object[] row = {
							rs.getString("Loginname"),
							rs.getString("secret"),
							rs.getString("Grade")
					};
					dtm.addRow(row);
				}
				scrollPane = new JScrollPane(table);
				jframe.add(scrollPane, BorderLayout.CENTER);
				jframe.setVisible(true);
			}
			//查看公司总人数
			if (Event.getSource() == button3){
				int count = 0;
				rs = statement.executeQuery("select * from Employee");
				while(rs.next()){
					count++;
				}
				JOptionPane.showMessageDialog(null, "该公司总共的成员有: " + count +" 名员工", "系统提示", JOptionPane.DEFAULT_OPTION);
			}
			
			//查看所有员工的平均工资
			if (Event.getSource() == button4){
				rs = statement.executeQuery("select AVG(allmoney) as 'sum' from Employee");
				while(rs.next()){
					JOptionPane.showMessageDialog(null, "该公司所有员工的平均收入为 ：" + rs.getString("sum") + " 元" , "系统提示", JOptionPane.DEFAULT_OPTION);
				}
				
			}
			
			//查看所有员工加班信息
			if (Event.getSource() == button5){
				String[] Title = {"员工id","员工姓名", "旷班", "迟到","请假", "嘉奖", "节假日加班", "平时加班", "最终嘉奖所得"};
				jframe.setSize(800, 300);
				jframe.setLocation(jframe.getSize().width/2, jframe.getSize().height/2);
				jframe.setTitle("员工奖惩信息信息");
				//链接数据库
				rs = statement.executeQuery("select Employee.name, Lncentivepay.* from Employee, Lncentivepay where Employee.id = Lncentivepay.id;");
				//通过dtm得到表中的信息
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				
				while(rs.next()){
					
					Object[] row = {
							rs.getString("Lncentivepay.id"),
							rs.getString("Employee.name"),
							rs.getString("opencalss") + "次",
							rs.getString("late") + "次",
							rs.getString("Leavecount") + "次",						
							rs.getString("reward") + "次", 
							rs.getString("holidayovertime") + "次", 
							rs.getString("usualovertime") + "次",
							rs.getString("rewardmoney") + "元"};
						dtm.addRow(row);
				}
				scrollPane = new JScrollPane(table);
				jframe.add(scrollPane, BorderLayout.CENTER);
				jframe.setVisible(true);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "系统出错", "系统问题", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
		}
	}
