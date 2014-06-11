import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.MenuBar;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements ActionListener{
	//创建内容面板
	JPanel contentPane;
	
	//创建菜单栏组建的对象
	JMenuBar JMenuBar1 = new JMenuBar();
	JMenu JMenu0 = new JMenu("系统");
	JMenuItem JMenuItem0 = new JMenuItem("退出");
	//等级为最高级，1时才会有的权限
	JMenuItem JMenuItem10 = new JMenuItem("注册新用户");
	JMenuItem JMenuItem11 = new JMenuItem("切换用户");
	//等级为次高级管理，2时才会有的权限
	JMenu JMenu1 = new JMenu("职工管理");
	JMenuItem JMenuItem1 = new JMenuItem("添加职工");
	JMenuItem JMenuItem2 = new JMenuItem("修改职工");
	JMenuItem JMenuItem3 = new JMenuItem("删除职工");

	JMenu JMenu3 = new JMenu("职工信息及工资查询");
	JMenuItem JMenuItem6 = new JMenuItem("单个职工的信息查询");
	//这个也是只有等级为1,2的员工所有
	JMenuItem JMenuItem7 = new JMenuItem("所有职工的信息查询");
//	JMenuItem JMenuItem7 = new JMenuItem("按职工姓名查询");
	
	JMenu JMenu4 = new JMenu("帮助");
	JMenuItem JMenuItem9 = new JMenuItem("系统维护");
	JMenuItem JMenuItem = new JMenuItem("其他");
	JLabel JLabel1 = new JLabel("欢迎使用工资管理系统");
	
	ImageIcon ico = new ImageIcon("\\home\\cuijiaojiao\\图片\\background.jpg");
	JLabel Limage = new JLabel(ico);
	
	int grade;
	
	public MainFrame(int grade){
		try{
			this.grade = grade;
			
			System.out.println("grade = " + grade);
			//关闭窗口时的默认事件方法
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			System.out.println("Hello");
			JInit(grade);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//界面的初始化
	private void JInit(int grade){
		//创建内容面板
		contentPane = (JPanel)getContentPane();
		
		//设置内容面板的布局为空
		contentPane.setLayout(null);
		//主框架的大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation((int)(screenSize.width - 400)/2, (int)(screenSize.height - 300)/2);
		setSize(new Dimension(400, 360));
		
		setTitle("工资管理系统");
		
		//添加事件监听器
		JMenuItem0.addActionListener(this);
		JMenuItem1.addActionListener(this);
		JMenuItem2.addActionListener(this);
		JMenuItem3.addActionListener(this);
//		JMenuItem4.addActionListener(this);
//		JMenuItem5.addActionListener(this);
		JMenuItem6.addActionListener(this);
		JMenuItem7.addActionListener(this);
//		JMenuItem8.addActionListener(this);
		JMenuItem9.addActionListener(this);
		JMenuItem10.addActionListener(this);
		JMenuItem11.addActionListener(this);
		JMenuItem.addActionListener(this);
		setJMenuBar(JMenuBar1);
		
		//添加菜单到菜单条
		JMenuBar1.add(JMenu0);
		
		if (grade == 1 || grade == 2){
	//		System.out.println("有注册信息");
		//	JMenuItem10.addActionListener(this);
			JMenuBar1.add(JMenu1);
			JMenu1.add(JMenuItem1);
			JMenu1.add(JMenuItem2);
			JMenu1.add(JMenuItem3);
			
		}
		if (grade == 1){
			JMenu0.add(JMenuItem10);
		}
//		JMenuBar1.add(JMenu2);
		JMenuBar1.add(JMenu3);
		JMenuBar1.add(JMenu4);
		
		//添加菜单项到菜单
		JMenu0.add(JMenuItem0);
		JMenu0.add(JMenuItem11);
//		JMenu2.add(JMenuItem4);
//		JMenu2.add(JMenuItem5);
//		JMenu2.add(JMenuItem8);
		
		JMenu3.add(JMenuItem6);
		
		//等级为1,2的管理员所拥有的等级
		if (grade == 1 || grade == 2){
			JMenu3.add(JMenuItem7);
		}
		JMenu4.add(JMenuItem9);
		JMenu4.add(JMenuItem);
		
		//添加标签到主框架内容板
		contentPane.add(JLabel1);
	//	setJMenuBar(JMenuBar1);
		setResizable(false);
		setVisible(true);
		JLabel1.setFont(new java.awt.Font("黑体", Font.BOLD, 20));
		JLabel1.setBounds(new Rectangle(65, 70, 275, 55));
/*		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		Limage.setBounds(0,0,ico.getIconWidth(), ico.getIconHeight());
		panel1.add(Limage);
	*/}
	@Override
	//菜单事件的对应处理方法
	public void actionPerformed(ActionEvent actionEvent) {
		// TODO Auto-generated method stub
		if (actionEvent.getSource() == JMenuItem0){
			JOptionPane.showMessageDialog(null, "您已成功退出系统！","系统提示", JOptionPane.CANCEL_OPTION);
			System.exit(0);
		}
		if (actionEvent.getSource() == JMenuItem11){
		//	MainFrame.this.setVisible(false);
			//让之前的主界面消失
			this.setVisible(false);
			new loggins();
		}
		//用户管理，只有root用户有这个权限
		if (actionEvent.getSource() == JMenuItem10){
			//创建“注册面板”
			System.out.println("进入了注册界面");
			Signin signin = new Signin();
			
			System.out.println("开始移除主页面");
			//移除主框架原有的内容
			this.remove(this.getContentPane());
			System.out.println("注册页面加载k开始啦。。。");
				//加载“注册面板”
			this.setContentPane(signin);
			
			System.out.println("界面马上可见了");
			//令界面可见
			this.setVisible(true);
		}
		
		//添加职工,删除职工，修改职工，root用户的权限
		if (actionEvent.getSource() == JMenuItem1){
			System.out.println("root用户进行员工添加");
			 AddEmpl addEmpl = new AddEmpl();
			 
			 this.remove(this.getContentPane());
			 this.setContentPane(addEmpl);
			 this.setVisible(true);
		}
		
		if (actionEvent.getSource() == JMenuItem2){
			AlterEmpl alterEmpl = new AlterEmpl();
			
			this.remove(this.getContentPane());
			this.setContentPane(alterEmpl);
			this.setVisible(true);
		}
		
		if (actionEvent.getSource() == JMenuItem3){
			DelEmpl delEmpl = new DelEmpl();
			
			this.remove(this.getContentPane());
			this.setContentPane(delEmpl);
			this.setVisible(true);
		}
		//按照职工号查询员工信息
		if (actionEvent.getSource() == JMenuItem6){
			
			IDSelect idSelect = new IDSelect(grade);
			this.remove(this.getContentPane());
			this.setContentPane(idSelect);
			this.setVisible(true);
			
		}
		if (actionEvent.getSource() == JMenuItem7){
			AllSelect allSelect = new  AllSelect(grade);
			this.remove(this.getContentPane());
			this.setContentPane(allSelect);
			this.setVisible(true);
		}
		if (actionEvent.getSource() == JMenuItem9){
			JOptionPane.showMessageDialog(null,  "系统若出现故障，请联系管理员！", "系统提示", JOptionPane.ERROR_MESSAGE);	
		}
		if (actionEvent.getSource() == JMenuItem){
			JOptionPane.showMessageDialog(null, "如需其他操作，请联系管理员进行后台操作", "系统提示", JOptionPane.DEFAULT_OPTION);
		}
	}
}
