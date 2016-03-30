package cn.ucai.superqq.biz;

import java.io.File;

import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.GroupBean;
import cn.ucai.superqq.bean.UserBean;
import cn.ucai.superqq.dao.ISuperQQDao;
import cn.ucai.superqq.dao.SuperQQDao;
import cn.ucai.superqq.servlet.I;

public class SuperQQBiz implements ISuperQQBiz {
	private ISuperQQDao dao;
	public SuperQQBiz(){
		dao = new SuperQQDao();
	}

	@Override
	public boolean register(UserBean user) throws Exception {
		if(dao.findUserByUserName(user.getUserName()) != null){
			throw new Exception("用户名已经存在");
		}
		boolean isSuccess = dao.addUser(user);
		return isSuccess;
	}

	@Override
	public boolean unRegister(String userName) {
		String path = I.AVATAR_PATH + "user_avatar/" + userName +".jpg";
		boolean isDelete = dao.deleteUser(userName);
		if(isDelete){
			File file = new File(path);
			if (file.exists()){
				file.delete();
			}
		}
		return isDelete;
	}

	@Override
	public UserBean login(String userName, String password) throws Exception {
		System.out.println("SuperQQBiz.login,userName="+userName+",password="+password);
		UserBean user = dao.findUserByUserName(userName);
		if (user == null) {
			throw new Exception("请先注册");
		}
		if (!user.getPassword().equals(password)) {
			throw new Exception("密码错误");
		}
		return user;
	}

	@Override
	public UserBean findUserByUserName(String userName) {
		UserBean user = dao.findUserByUserName(userName);
		return user;
	}

	@Override
	public UserBean[] findUsersByUserName(String userName, int pageId, int pageSize) {
		UserBean[] users = dao.findUsersByUserName(userName, pageId, pageSize);
		return users;
	}

	@Override
	public UserBean[] findUsersByNick(String nick, int pageId, int pageSize) {
		UserBean[] users = dao.findUsersByNick(nick, pageId, pageSize);
		return users;
	}

	@Override
	public UserBean[] findUsers4Location(String userName, int pageId, int pageSize) {
		UserBean[] users = dao.findUsers4Location(userName, pageId, pageSize);
		return users;
	}

	@Override
	public boolean updateUser(UserBean user) {
		boolean isSuccess = dao.updateUser(user);
		return isSuccess;
	}

	@Override
	public ContactBean findContactById(int myuid, int cuid) {
		ContactBean contact = dao.findContactById(myuid, cuid);
		return contact;
	}

	@Override
	public ContactBean[] findContactsByUserName(String userName, int pageId, int pageSize) {
		ContactBean[] contacts = dao.findContactsByUserName(userName, pageId, pageSize);
		return contacts;
	}

	@Override
	public UserBean[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		UserBean[] contacts = dao.findContactListByMyuid(myuid, pageId, pageSize);
		return contacts;
	}

	@Override
	public ContactBean addContact(String userName, String name) {
		return dao.addContact(userName, name);
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		boolean isSuccess = dao.deleteContact(myuid, cuid);
		if(isSuccess){
			isSuccess = dao.deleteContact(cuid, myuid);
		}
		return isSuccess;
	}

	@Override
	public boolean updateContact(ContactBean contact) {
		return dao.updateContact(contact);
	}

	@Override
	public boolean createGroup(GroupBean group) {
		//在groups表中添加一条群记录
		boolean isSuccess = dao.createGroup(group);
		if(isSuccess){
			//取出群所有成员的账号
			String members = group.getMembers();
			//转换为字符串数组
			String[] userNames = members.split(",");
			for (String userName : userNames) {
				//按账号找到每个用户
				UserBean user = dao.findUserByUserName(userName);
				//取出用户的groups属性
				String groups = user.getGroups();
				if(groups!=null && !groups.equals("")){
					//若非空，则将群名称添加至该用户的groups属性中
					groups+=","+group.getName();
				}else{
					//若该用户没有所属群，则直接赋值为群名称
					groups=group.getName();
				}
				//保存到user的groups属性中
				user.setGroups(groups);
				dao.updateUser(user);//修改user表记录
			}
		}
		return isSuccess;
	}

	@Override
	public boolean updateGroupName(GroupBean group, String newGroupName) {
		updateUserGroupName(group.getName(),newGroupName);
		boolean isUpdate = dao.updateGroupName(group, newGroupName);
		if(isUpdate){
			isUpdate = dao.updateGroupAvatar(newGroupName, "group_icon/"+newGroupName+".jpg");
		}
		return isUpdate;
	}
	
	/**
	 * 将所有用户的group字段中包含oldGroupName的字符串改为newGroupName
	 * @param groupName
	 * @return
	 */
	private boolean updateUserGroupName(String oldGroupName,String newGroupName) {
		String[] members = dao.findGroupMembersByGroupName(oldGroupName);
		if(members == null){
			return false;
		}
		boolean isUpdate=false;
		//修改user表中所有groups属性中包含groupName的记录，从groups字段中删除groupName
		for (String userName : members) {
			//获取代表记录的user对象
			UserBean user = dao.findUserByUserName(userName);
			String groups = user.getGroups();//取出groups字段值
			groups=groups.replace(oldGroupName, newGroupName);//将groupName删除
			user.setGroups(groups);
			isUpdate = dao.updateUser4Group(user.getId(), groups);
		}
		return isUpdate;
	}

	@Override
	public boolean deleteGroup(String groupName) {
		deleteUserGroupName(groupName);
		//删除groups表中的群名为groupName的记录
		return dao.deleteGroup(groupName);
	}

	/**
	 * 将所有用户的group字段中包含gropuName的字符串删除
	 * @param groupName
	 * @return
	 */
	private boolean deleteUserGroupName(String groupName) {
		String[] members = dao.findGroupMembersByGroupName(groupName);
		if(members==null){
			return false;
		}
		boolean isUpdate=false;
		//修改user表中所有groups属性中包含groupName的记录，从groups字段中删除groupName
		for (String userName : members) {
			//获取代表记录的user对象
			UserBean user = dao.findUserByUserName(userName);
			String groups = user.getGroups();//取出groups字段值
			String regex=","+groupName;
			groups=groups.replace(regex, "");//将groupName删除
			regex=groupName;
			groups=groups.replace(regex, "");//若groupName是头一个字符串，则本命令删除该字符串
			user.setGroups(groups);
			isUpdate=dao.updateUser(user);//修改user表的记录
		}
		return isUpdate;
	}

	@Override
	public GroupBean[] findAllGroup(String userName) {
		return dao.findAllGroup(userName);
	}


	@Override
	public GroupBean findGroupByGroupName(String groupName) {
		return dao.findGroupByGroupName(groupName);
	}

	@Override
	public GroupBean findGroupByGroupId(String groupId) {
		return dao.findGroupByGroupId(groupId);
	}


	@Override
	public GroupBean addGroupMember(String groupName, String memberName) {
		GroupBean group = dao.addGroupMember(groupName, memberName);
		if(group!=null){
			//将群名添加至账号为memberName的用户的groups字段
			UserBean user = dao.findUserByUserName(memberName);
			String groups = user.getGroups();
			if(groups==null || groups.isEmpty()){
				groups=groupName;
			}else{
				groups+=","+groupName;
			}
			user.setGroups(groups);	
			boolean isSuccess = dao.updateUser(user);
		}
		return group;
	}
	@Override
	public boolean addGroupMembers(String groupName, String memberName) {
		GroupBean group = null;
		boolean isSuccess = false;
		String[] memberArray = memberName.split(",");
		for(String member:memberArray){
			group = addGroupMember(groupName,member);
			if(group!=null){
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	@Override
	public boolean deleteGroupMember(String groupName, String memberName) {
		return dao.deleteGroupMember(groupName, memberName);
	}

	@Override
	public GroupBean[] findPublicGroup(String userName, int pageId, int pageSize) {
		return dao.findPublicGroup(userName, pageId, pageSize);
	}
	@Override
	public boolean updateGroupAvatar(String name, String avatar) {
		return dao.updateGroupAvatar(name, avatar);
	}

}
