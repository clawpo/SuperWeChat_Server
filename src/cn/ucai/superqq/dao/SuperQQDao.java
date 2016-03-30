package cn.ucai.superqq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.GroupBean;
import cn.ucai.superqq.bean.UserBean;
import cn.ucai.superqq.servlet.I;
import cn.ucai.superqq.utils.JdbcUtils;
import cn.ucai.superqq.utils.Utils;

/**
 * 数据访问层
 * @author chen
 *
 */
public class SuperQQDao implements ISuperQQDao {

	@Override
	public UserBean findUserByUserName(String userName) {
		System.out.println("SuperQQDao.findUserByUserName,userName="+userName);
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if(set.next()){
				UserBean user = readUser(set);
				System.out.println("user="+userName.toString());
				return user;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	/**
	 * 从set中读取user表的一条记录
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	private UserBean readUser(ResultSet set) throws SQLException {
		int id = set.getInt(I.User.ID);
		String userName = set.getString(I.User.USER_NAME);
		String nick = set.getString(I.User.NICK);
		String avatar = set.getString(I.User.AVATAR);
		String password = set.getString(I.User.PASSWORD);
		double latitude = set.getDouble(I.User.LATITUDE);
		double longitude = set.getDouble(I.User.LONGITUDE);
		int unreadMsgCount = set.getInt(I.User.UN_READ_MSG_COUNT);
		String groups = set.getString(I.User.GROUPS);
		String result = "ok";
		UserBean user = new UserBean(id,result,userName,nick,password,avatar,groups,latitude,longitude,unreadMsgCount);
		return user;		
	}

	@Override
	public UserBean[] findUsersByUserName(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + " like ?"
				+ " limit ?,?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+userName+"%");
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			UserBean[] users = new UserBean[0];
			while(set.next()){
				UserBean user = readUser(set);
				users = Utils.add(users, user);
			}
			return users;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserBean[] findUsersByNick(String nick, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME
				+ " where " + I.User.NICK + " like ?"
				+ " limit ?,?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+nick+"%");
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			UserBean[] users = new UserBean[0];
			while(set.next()){
				UserBean user = readUser(set);
				users = Utils.add(users, user);
			}
			return users;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserBean[] findUsers4Location(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " +I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "<> ? "
				+ "and " + I.User.ID + " in(select "
				+ I.Contact.MYUID +" from " + I.Contact.TABLE_NAME
				+ " where " + I.Contact.IS_GET_MY_LOCATION + "=1"
				+ ") limit ?,?";
		try {
			System.out.println("sql="+sql);
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			UserBean[] users = new UserBean[0];
			while(set.next()){
				UserBean user = readUser(set);
				users = Utils.add(users, user);
			}
			return users;
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addUser(UserBean user) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + I.User.TABLE_NAME
				+ "(" + I.User.USER_NAME
				+ "," + I.User.NICK + "," + I.User.PASSWORD
				+ "," + I.User.LATITUDE + "," +I.User.LONGITUDE
				+ "," + I.User.UN_READ_MSG_COUNT + ")values(?,?,?,?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getNick());
			statement.setString(3, user.getPassword());
			statement.setDouble(4, 0);
			statement.setDouble(5, 0);
			statement.setInt(6, 0);
			int count = statement.executeUpdate();
			return count ==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean deleteUser(String userName) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "delete from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			int count = statement.executeUpdate();
			return count ==1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateUser(UserBean user) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "update " + I.User.TABLE_NAME
				+ " set "+ I.User.NICK + "=?,"
				+ I.User.AVATAR + "=?,"
				+ I.User.LATITUDE+"=?,"
				+ I.User.LONGITUDE+"=?,"
				+ I.User.GROUPS+"=?"
				+ " where "+I.User.USER_NAME+"=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getNick());
			statement.setString(2, user.getAvatar());
			statement.setDouble(3, user.getLatitude());
			statement.setDouble(4, user.getLongitude());
			statement.setString(5, user.getGroups());
			statement.setString(6, user.getUserName());
			int count = statement.executeUpdate();
			return count == 1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateUser4Group(int uId, String groups) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.User.TABLE_NAME
			+" set "+I.User.GROUPS+"=?"
			+" where "+I.User.ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groups);
			statement.setInt(2, uId);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	@Override
	public boolean isExistsContact(String userName, String name) {
		int myuid = findIdByUserName(userName);
		int cuid = findIdByUserName(name);
		PreparedStatement statement = null;
		ResultSet set = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select " + I.Contact.ID
				+ " from " + I.Contact.TABLE_NAME
				+ " where " + I.Contact.MYUID + "=?"
				+ " and " + I.Contact.CUID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			set = statement.executeQuery();
			if(set.next()){
				int id = set.getInt(I.Contact.ID);
				return id>0;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return false;
	}

	/**
	 * 根据账号查询id
	 * @param userName
	 * @return
	 */
	private int findIdByUserName(String userName){
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select " + I.User.ID
				+ " from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if(set.next()){
				int id = set.getInt(I.User.ID);
				return id;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	@Override
	public ContactBean findContactById(int myuid, int cuid) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.Contact.TABLE_NAME
				+ " where " + I.Contact.MYUID + "=? and "
				+ I.Contact.CUID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			set = statement.executeQuery();
			if(set.next()){
				ContactBean contact = readContact(set);
				return contact;
			}
		}catch (SQLException e){
			e.printStackTrace();
		} finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 从set中获取contact表中的一条记录
	 * @param set
	 * @return
	 * @throws SQLException 
	 */
	private ContactBean readContact(ResultSet set) throws SQLException {
		int myuid = set.getInt(I.Contact.MYUID);
		int cuid = set.getInt(I.Contact.CUID);
		boolean isGetMyLocation = set.getBoolean(I.Contact.IS_GET_MY_LOCATION);
		boolean isShowMyLocation = set.getBoolean(I.Contact.IS_SHOW_MY_LOCATION);
		String result = "ok";
		ContactBean contact = new ContactBean(result,myuid,cuid,isGetMyLocation,isShowMyLocation);
		return contact;
	}

	@Override
	public ContactBean[] findContactsByUserName(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Contact.TABLE_NAME
				+" where "+I.Contact.MYUID+" in("
				+"select "+I.User.ID
				+" from "+I.User.TABLE_NAME
				+" where "+I.User.USER_NAME+"=?)"
				+" limit ?,?";
			try {
				statement=connection.prepareStatement(sql);
				statement.setString(1, userName);
				statement.setInt(2, pageId);
				statement.setInt(3, pageSize);
				set=statement.executeQuery();
				ContactBean[] contacts=new ContactBean[0];
				while(set.next()){
					contacts=Utils.add(contacts, readContact(set));
				}
				return contacts;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				JdbcUtils.closeAll(set, statement, connection);
			}
			return null;
	}

	@Override
	public UserBean[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.User.TABLE_NAME
			+" where "+I.User.ID+" in("
			+"select "+I.Contact.CUID+" from "
			+I.Contact.TABLE_NAME
			+" where "+I.Contact.MYUID+"=?)"
			+" limit ?,?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			UserBean[] users=new UserBean[0];
			while(set.next()){
				UserBean user=readUser(set);
				users=Utils.add(users, user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		
		return null;
	}

	@Override
	public ContactBean addContact(String userName, String name) {
		boolean existsContact = isExistsContact(userName, name);
		if(existsContact){
			System.out.println("已经是联系人");
			return null;
		}
		int myuid=findIdByUserName(userName);
		int cuid=findIdByUserName(name);
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Contact.TABLE_NAME
			+"("+I.Contact.MYUID
			+","+I.Contact.CUID
			+")values(?,?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			ContactBean contact = findContactById(myuid, cuid);
			contact.setResult("ok");
			return contact;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return null;
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Contact.TABLE_NAME
			+" where "+I.Contact.MYUID+"=? and "
			+I.Contact.CUID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateContact(ContactBean contact) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Contact.TABLE_NAME
			+" set "+I.Contact.IS_GET_MY_LOCATION+"=?"
			+","+I.Contact.IS_SHOW_MY_LOCATION+"=?"
			+" where "+I.Contact.MYUID+"=?";
		try {
			System.out.println("sql="+sql);
			System.out.println("contact="+contact.toString());
			statement=connection.prepareStatement(sql);
			statement.setBoolean(1, contact.isGetMyLocation());
			statement.setBoolean(2, contact.isShowMyLocation());
			statement.setInt(3, contact.getCuid());
			int count = statement.executeUpdate();
			System.out.println("count="+count);
			return count>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		
		return false;
	}
	
	/**
	 * 打印UserBean数组
	 * @param users
	 */
	private static void printUsers(UserBean[] users){
		if(users!=null){
			for (UserBean u : users) {
				System.out.println(u.toString());
			}
		}
	}

	@Override
	public boolean createGroup(GroupBean group) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Group.TABLE_NAME+"("
			+I.Group.NAME+","
			+I.Group.GROUP_ID+","
			+I.Group.INTRO+","
			+I.Group.OWNER+","
			+I.Group.IS_PUBLIC+","
			+I.Group.MEMBERS+","
			+I.Group.IS_EXAME
			+")values(?,?,?,?,?,?,?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, group.getName());
			statement.setString(2, group.getGroupId());
			statement.setString(3, group.getIntro());
			statement.setString(4, group.getOwner());
			statement.setBoolean(5, group.isPublic());
			statement.setString(6, group.getMembers());
			statement.setBoolean(7, group.isExame());
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateGroupName(GroupBean group, String newGroupName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.NAME+"=?,"
			+I.Group.AVATAR+"=?,"
			+I.Group.MEMBERS+"=?"
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, newGroupName);
			statement.setString(2, group.getAvatar());
			statement.setString(3, group.getMembers());
			statement.setString(4, group.getName());
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
			
		return false;
	}

	@Override
	public boolean deleteGroup(String groupName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Group.TABLE_NAME
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupName);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public GroupBean[] findAllGroup(String userName) {
		String[] groupsName = findAllGroupByUserName(userName);
		if(groupsName==null){
			return null;
		}
		GroupBean[] groups=new GroupBean[0];
		for (String groupName : groupsName) {
			GroupBean group=findGroupByGroupName(groupName);
			groups=Utils.add(groups, group);
		}
		if(groups.length==0){
			groups=null;
		}
		return groups;
	}

	private String[] findAllGroupByUserName(String userName){
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+I.User.GROUPS
			+" from "+I.User.TABLE_NAME
			+" where "+I.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			String groupsName =null;
			if(set.next()){
				groupsName = set.getString(I.User.GROUPS);
				if(groupsName!=null){
					String[] groups = groupsName.split(",");
					return groups;
				}
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	
	private GroupBean findGroupsByName(String name){
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, name);
			set=statement.executeQuery();
			if(set.next()){
				GroupBean group=readGroup(set);
				return group;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return null;
	}
	
	/**
	 * 读取group表中的一条记录
	 * @param set
	 * @return
	 */
	private GroupBean readGroup(ResultSet set) {
		GroupBean bean=new GroupBean();
		try {
			bean.setAvatar(set.getString(I.Group.AVATAR));
			bean.setId(set.getInt(I.Group.ID));
			bean.setGroupId(set.getString(I.Group.GROUP_ID));
			bean.setIntro(set.getString(I.Group.INTRO));
			bean.setModifiedTime(set.getInt(I.Group.MODIFIED_TIME));
			bean.setName(set.getString(I.Group.NAME));
			bean.setOwner(set.getString(I.Group.OWNER));
			bean.setPublic(set.getBoolean(I.Group.IS_PUBLIC));
			bean.setExame(set.getBoolean(I.Group.IS_EXAME));
			bean.setMembers(set.getString(I.Group.MEMBERS));
			return bean;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args){

		UserBean user=null;
		UserBean[] users=null;
		ContactBean contact=null;
		ContactBean[] contacts=null;
		GroupBean group=null;
		SuperQQDao dao=new SuperQQDao();
		System.out.println("1-测试findUserByUserName()");
		System.out.println("2-测试findUsersByUserName()");
		System.out.println("3-测试findUsersByNick()");
		System.out.println("4-测试findUsers4Location()");
		System.out.println("5-测试addUser()");
		System.out.println("6-测试updateUser()");
		System.out.println("7-测试isExistsContact()");
		System.out.println("8-测试findContactByMyuid()");
		System.out.println("9-测试findContactsByUserName()");
		System.out.println("10-测试findContactListByMyuid()");
		System.out.println("11-测试addContact()");
		System.out.println("12-测试deleteContact()");
		System.out.println("13-测试updateContact()");
		System.out.println("14-测试createGroup()");
		System.out.println("15-测试updateGroup()");
		System.out.println("16-测试deleteGroup()");
		System.out.println("17-测试findAllGroup()");
		System.out.println("18-测试findGroupsByName()");
		System.out.println("19-测试addGroupMember()");
		System.out.println("20-测试deleteGroupMemeber()");
		System.out.println("select(1-20)");
		int select=new java.util.Scanner(System.in).nextInt();
		switch (select) {
		case 1:
			user=dao.findUserByUserName("a");
			System.out.println(user.toString());
			break;
		case 2:
			users=dao.findUsersByUserName("a", 0, 20);
			printUsers(users);
		case 3:
			users=dao.findUsersByNick("aa", 0, 20);
			printUsers(users);
			break;
		case 4:
			users=dao.findUsers4Location("a", 0, 20);
			printUsers(users);
			break;
		case 5:
			user=new UserBean("aaaaaa", "aaaaaa", "a");
			boolean isSuccess = dao.addUser(user);
			System.out.println("注册用户成功："+isSuccess);
			break;
		case 6:
			user=new UserBean("a", "张飞", "a");
			user.setAvatar("F:/0-Android/project/GeekGarden/supertqq/avatar/a.jpg");
			user.setLatitude(40.3957);
			user.setLongitude(116.785);
			boolean isSuccess2 = dao.updateUser(user);
			System.out.println("修改user成功："+isSuccess2);
			break;
		case 7:
			isSuccess = dao.isExistsContact("a", "aaa");
			System.out.println("a和aaa是联系人："+isSuccess);
			break;
		case 8:
			contact=dao.findContactById(1,2);
			if(contact!=null){
				System.out.println(contact.toString());
			}
			break;
		case 9:
			contacts=dao.findContactsByUserName("a", 0, 20);
			if(contacts!=null){
				for (ContactBean c : contacts) {
					System.out.println(c);
				}
			}
			break;
		case 10:
			users=dao.findContactListByMyuid(1,0,20);
			if(users!=null){
				for (UserBean u : users) {
					System.out.println(u);
				}
			}
			break;
		case 11:
			contact = dao.addContact("aa", "aaa");
			System.out.println("添加联系人记录成功："+(contact!=null));
			break;
		case 12:
			boolean deleteContact = dao.deleteContact(2, 3);
			if(deleteContact){
				System.out.println("删除联系人成功");
			}
			break;
		case 13:
			contact=new ContactBean("ok", 2, 1, true, true);
			isSuccess=dao.updateContact(contact);
			System.out.println("修改联系人成功："+isSuccess);
			break;
		case 14:
			group=new GroupBean("14033889966","超级QQ群2", "这是一个超级qq群", "a", true,true,"a,aa,aaa,b");
			isSuccess = dao.createGroup(group);
			System.out.println("创建群成功:"+isSuccess);
			break;
		case 15:
			group = dao.findGroupsByName("superQQ2");
			group.setAvatar("F:/0-Android/project/GeekGarden/superqq/group_avatar");
			group.setName("superQQ");
			isSuccess = dao.updateGroupName(group,"superQQ2");
			System.out.println("修改群成功:"+isSuccess);
			group = dao.findGroupsByName("superQQ2");
			System.out.println(group);
			break;
		case 16:
			isSuccess = dao.deleteGroup("superQQ");
			System.out.println("解散superQQ群:"+isSuccess);
			break;
		case 17:
			GroupBean[] groups=dao.findAllGroup("a");
			System.out.println(Arrays.toString(groups));
			break;
		case 18:
			group = dao.findGroupsByName("1405588");
			System.out.println(group);
			break;
		case 19:
			GroupBean group2 = dao.addGroupMember("1405588", "b");
			System.out.println("添加群成员:"+group2.toString());
			break;
		case 20:
			isSuccess=dao.deleteGroupMember("1405588", "aa");
			System.out.println("删除群成员aa成功:"+isSuccess);
			break;
		}
	}
	
	

	@Override
	public GroupBean findGroupByGroupName(String groupName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupName);
			set=statement.executeQuery();
			if(set.next()){
				GroupBean group = readGroup(set);
				return group;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public GroupBean findGroupByGroupId(String groupId) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+" where "+I.Group.GROUP_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupId);
			set=statement.executeQuery();
			if(set.next()){
				GroupBean group = readGroup(set);
				return group;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public GroupBean addGroupMember(String groupName, String memberName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String members=findMembersFromGroup(groupName);
		if(members!=null){
			members+=","+memberName;
		}else{
			members=memberName;
		}
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.MEMBERS+"=?"
			+" where "+I.Group.NAME+"=?";
		int count=0;
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, members);
			statement.setString(2, groupName);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		GroupBean group = null;
		if(count==1){
			group = findGroupByGroupName(groupName);
		}
		return group;
	}

	/**
	 * 查询群的members字段
	 * @param groupName
	 * @return
	 */
	private String findMembersFromGroup(String groupName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+I.Group.MEMBERS
			+" from "+I.Group.TABLE_NAME
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupName);
			set=statement.executeQuery();
			if(set!=null){
				String members=set.getString(I.Group.MEMBERS);
				return members;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public boolean deleteGroupMember(String groupName, String memberName) {
		//从user表中删除账号是userName的记录中groups属性里面的groupId
		boolean isSuccess = deleteGroups4User(memberName,groupName);
		if(isSuccess){
			//从groups表中删除groupId指定的记录中的members中的userName
			isSuccess=deleteMembers4Group(groupName,memberName);
		}
		return isSuccess;
	}

	@Override
	public GroupBean[] findPublicGroup(String userName, int pageId, int pageSize) {
		GroupBean[] groups=new GroupBean[0];
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+" where "+I.Group.IS_PUBLIC+"=1 limit ?,?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, pageId);
			statement.setInt(2, pageSize);
			set=statement.executeQuery();
			while(set.next()){
				GroupBean group = readGroup(set);
				groups=Utils.add(groups, group);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		//以下代码过滤掉包含userName的群组
		GroupBean[] result=new GroupBean[0];
		for(int i=0;i<groups.length;i++){
			GroupBean group = groups[i];
			//获取群成员数组
			String[] members = group.getMembers().split(",");
			boolean notFind=true;//未发现群成员中包含userName则notFind=true 
			for(int j=0;j<members.length&&notFind;j++){
				notFind=!userName.equals(members[j]);//查找群成员中是否包含userName
			}
			if(notFind){//若群成员中没有userName
				result=Utils.add(result, group);//添加至最终结果的数组中
			}
		}
		return result;
	}
	

	@Override
	public String[] findGroupMembersByGroupName(String groupName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+I.Group.MEMBERS
			+" from "+I.Group.TABLE_NAME
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupName);
			set=statement.executeQuery();
			if(set.next()){
				String member=set.getString(I.Group.MEMBERS);
				String[] members=member.split(",");
				return members;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}


	@Override
	public boolean updateGroupAvatar(String name, String avatar) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.AVATAR+"=?"
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, avatar);
			statement.setString(2, name);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
			
		return false;
	}

	/**从groups表中删除groupId指定的记录中的members中的userName*/
	private boolean deleteMembers4Group(String groupName, String userName) {
		//从user表中查询userName账号的members属性值
		String members=findMembersFromGroup(groupName);
		members=Utils.deleteMember(members, userName);
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.MEMBERS+"=?"
			+" where "+I.Group.NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, members);
			statement.setString(2, groupName);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	/**
	 * 从user表中删除账号是userName的记录中groups属性里面的groupId
	 * @param userName
	 * @param groupName
	 */
	public boolean deleteGroups4User(String userName, String groupName) {
		//从user表中查询userName账号的members属性值
		String groups=findGroupsByUserName(userName);
		groups = Utils.deleteMember(groups,groupName);
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.User.TABLE_NAME
			+" set "+I.User.GROUPS+"=?"
			+" where "+I.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groups);
			statement.setString(2, userName);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	//从user表中查询userName账号的members属性值
	private String findGroupsByUserName(String userName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+I.User.GROUPS
			+" from "+I.User.TABLE_NAME
			+" where "+I.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			if(set!=null){
				String groups=set.getString(I.User.GROUPS);
				return groups;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 根据groupId查询groupName
	 * @param groupId
	 * @return
	 */
	private String findGroupNameByGroupId(String groupId) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+I.Group.NAME
			+" from "+I.Group.TABLE_NAME
			+" where "+I.Group.GROUP_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, groupId);
			set=statement.executeQuery();
			if(set.next()){
				String groupName=set.getString(I.Group.NAME);
				return groupName;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

}
