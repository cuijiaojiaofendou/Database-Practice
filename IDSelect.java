import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

//查询
public class IDSelect extends JPanel implements ActionListener {

	//声明链接数据库对象
	Connection connection;
		
	//声明mysql语句对象
	Statement statement;
	
	Label label0 = new Label("员工号");
	Label label1 = new Label("展示台");
	Label label2 = new Label("员工号格式:E****,eg:E0001");
	JTextField text0 = new JTextField(15);
	JTextArea text1 = new JTextArea(100, 75);
	
	JButton button0 = new JButton("查询基本工资");
	JButton button1 = new JButton("查询最终工资");	
	
	JButton button2 = new JButton("查询基本个人信息");
	JButton button3 = new JButton("所在部门的最高工资");
	JButton button4 = new JButton("公司工资最高的部门名");
	JButton button5 = new JButton("公司所有的部门信息");
	JButton button6 = new JButton("查询员工加班信息");
	JButton button7 = new JButton("员工奖惩金");
	
	int grade;
	public IDSelect(int grade){
		try{
			this.grade = grade;
			Init(grade);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void Init(int grade) throws SQLException{
		//连接数据库
		connection = Connectmysql.getConn();
		statement = connection.createStatement();
		
		this.setLayout(null);
		label0.setBounds(new Rectangle(25, 25, 64, 22));
		label1.setBounds(new Rectangle(25, 50, 64, 22));
		label2.setBounds(new Rectangle(210, 25, 200, 22));
		text0.setBounds(new Rectangle(100, 25, 110, 22));
		text1.setBounds(new Rectangle(100, 50, 200, 120));
		
		button0.setBounds(new Rectangle(25, 175, 150, 30));
		button1.setBounds(new Rectangle(200, 175, 150, 30));
		button2.setBounds(new Rectangle(25, 220, 150, 30));
		button3.setBounds(new Rectangle(200, 220, 150, 30));
		button4.setBounds(new Rectangle(25, 260, 150, 30));
		button5.setBounds(new Rectangle(200, 260, 150, 30));
		button6.setBounds(new Rectangle(25, 300, 150, 30));
		button7.setBounds(new Rectangle(200, 300, 150, 30));
		
		button0.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		
		this.add(label0);
		this.add(label1);
		this.add(label2);
		this.add(text0);
		this.add(text1);
		this.add(button0);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
		this.add(button6);
		this.add(button7);
	}
	@Override
	public void actionPerformed(ActionEvent Event) {
		// TODO Auto-generated method stub
		JFrame jframe = new JFrame();
		//声明一个表
		JTable table;
		JTableHeader jth;
		JScrollPane scrollPane = null;
		DefaultTableModel dtm;
		
		ResultSet rs;
		String id = text0.getText();
		
		try{
			//基本工资
			if (Event.getSource() == button0){
				if (id.equals("") == true){
					JOptionPane.showMessageDialog(this, "请输入员工id!");
				} else {
					rs = statement.executeQuery("Select basicwage from Workinfo where workid = (select workid from Employee where id = '" + id + "');");
					if (rs.next()){
						text1.setText("基本工资为:" + rs.getString("basicwage"));
				//	text1.setText("");
					} else {
						JOptionPane.showMessageDialog(this, "不存在该工号");
					}
				}
			}
			//查询最终工资
			if (Event.getSource() == button1){
				if (id.equals("") == true){
					JOptionPane.showMessageDialog(this, "请输入员工id!");
				}else {
					float allmoney= 0;
					rs = statement.executeQuery("select * from Employee where id = '" + id +"';" );
					if (rs.next()){
						allmoney = Integer.parseInt(rs.getString("allmoney"));
					}
				
					text1.setText("总工资为：" + allmoney);
				}
			}
			
			//基本信息的反馈
			if (Event.getSource() == button2){
				if (id.equals("") == true){
					JOptionPane.showMessageDialog(this, "请输入员工id!");
				}else {
					rs = statement.executeQuery("select * from Employee where id = '" + id + "';");
					if (rs.next()){
						text1.setText("id: " + rs.getString("id") + "  姓名: " + rs.getString("name") + "\n性别: " + rs.getString("sex") + "    年龄: " + rs.getString("age")
							+ "\n电话： " + rs.getString("tel") + "\n入职年份:  " + rs.getString("worktime") + "\n职位:  " + rs.getString("position") + "\n所在部门id:  " + 
							rs.getString("workid"));
				
					} else {
						JOptionPane.showMessageDialog(this, "不存在该工号！");
					}
				}
			}
			if (Event.getSource() == button3){
				if (id.equals("") == true){
					JOptionPane.showMessageDialog(this, "请输入员工id!");
				}else {
					String workname = null;
					rs = statement.executeQuery("select workname from Workinfo where workid = (Select workid from Employee where id = '" + id +"')");
					if (rs.next()){
						workname = rs.getString("workname");
					} else {
						JOptionPane.showMessageDialog(this, "不存在该职工号！");
					}	
					rs = statement.executeQuery("select Max(allmoney) as 'money' from Employee where workid = (Select workid from Employee where id = '"+ id +"' );");
					if (rs.next()){
						text1.setText(workname + " 最高工资: "+ rs.getString("money"));
					} else {
						JOptionPane.showMessageDialog(this, "不存在该职工号！");
					}
				}
			}
			if (Event.getSource() == button4){
				rs = statement.executeQuery("Select workname from Workinfo where workid = (select workid from Employee where allmoney = (select Max(allmoney) from Employee));");
				if (rs.next()){
					text1.setText("总公司最高工资所在部门的名称:\n" + rs.getString("workname"));
				}
			}
			
			if (Event.getSource() == button5){
				String[] Title = {"部门id", "部门名称", "部门所在楼层", "部门的基本工资"};
				jframe.setSize(400, 300);
				jframe.setLocation(jframe.getSize().width/2, jframe.getSize().height/2);
				jframe.setTitle("所有部门信息");
				//链接数据库
				rs = statement.executeQuery("select * from Workinfo;");
				//通过dtm得到表中的信息
	
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				
				while(rs.next()){
					
					Object[] row = {rs.getString("workid"),
							rs.getString("workname"),
							rs.getString("workplace"),						
							rs.getString("basicwage")};
						dtm.addRow(row);
				}
				scrollPane = new JScrollPane(table);
				jframe.add(scrollPane, BorderLayout.CENTER);
				jframe.setVisible(true);
			}
			
			if (Event.getSource() == button6){
				if (id.equals("") == true){
					JOptionPane.showMessageDialog(this, "请输入员工id!");
				} else {
				String[] Title = {"旷班", "迟到","请假", "嘉奖", "节假日加班", "平时加班", "最终嘉奖所得"};
				jframe.setSize(400, 300);
				jframe.setLocation(jframe.getSize().width/2, jframe.getSize().height/2);
				jframe.setTitle("员工奖惩信息信息");
				//链接数据库
				rs = statement.executeQuery("select * from Lncentivepay where id = '" + id +"';");
				//通过dtm得到表中的信息
				dtm = new DefaultTableModel(null, Title);
				table = new JTable(dtm);
				
				while(rs.next()){
					
					Object[] row = {rs.getString("opencalss") + "次",
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
			}
			
			if (Event.getSource() == button7){
				text1.setText("旷班一次:" + "  -50元\n" + "迟到一次:" + "   -20元\n" + "请假一次" + "   -10元\n" + "嘉奖一次:" + "   + 100元\n" +
							"节假日加班一次" + "  + 100 元\n" + "平时加班一次:" + "  + 50元\n");
				
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "系统出错", "系统问题", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

}
