package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConn {
	private static Properties prop=new Properties();
	Connection conn=null;
	Statement st=null;
	ResultSet rs = null;

	static{
		try{
			//�������ݿ���Ϣ
			prop.load(DatabaseConn.class.getResourceAsStream("/database.properties"));
		}catch(Exception e){
			
		}
	}
	//�������ݿ�
	public boolean getFreeConnection(){
		boolean flag=false;
		try{
			Class.forName(prop.getProperty("driverClass")).newInstance();
			String url=prop.getProperty("url");
			String user=prop.getProperty("user");
			String password=prop.getProperty("password");
			conn=DriverManager.getConnection(url,user,password);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	//ȡ��Statement
	public boolean getStatement(){
		boolean flag = false;
		try{
			st=conn.createStatement();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	//ִ��sql���
	public boolean executeSql(String sql){
		boolean flag=false;
		try{
			st.execute(sql);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
		
	}
	//ȡ��ͨ��Statement��ѯ�õ���ResultSet
	public ResultSet getResult(String sql){
		try{
			rs=st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	//����ɾ����¼
	public int getUpdate(String sql){
		int rs = 0;
		try{
			rs=st.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	//ȡ��PreparedStatement
		public  PreparedStatement getPreparedStatement(String sql){
			PreparedStatement pstmt=null;
			try{
				pstmt=conn.prepareStatement(sql);
			}catch(Exception e){
				e.printStackTrace();
			}
			return pstmt;
		}
		//ȡ��PreparedStatement��DML����
		public static boolean execute(PreparedStatement pstmt){
			boolean re=false;
			int number=0;
			try{
				number=pstmt.executeUpdate();
				if(number>0){
					re=true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return re;
		}
		//ͨ��PreparedStatement��ѯ�õ���ResultSet
		public ResultSet getResult(PreparedStatement pstmt){
			try{
				rs=pstmt.executeQuery();
			}catch(Exception e){
				e.printStackTrace();
			}
			return rs;
		}
		//�ر�ResultSet
		public void closeResultSet(){
			try{
				if(rs!=null){
					rs.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//�ر�Statement
		public void closeStatement(){
			try{
				if(st!=null) st.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void closePstmt(PreparedStatement pstmt){
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	   //�ر�Connection
		public void closeConnection(){
			try{
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
