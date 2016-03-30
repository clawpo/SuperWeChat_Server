package cn.ucai.superqq.dao;

import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.UserBean;
import cn.ucai.superqq.bean.GroupBean;

/**
 * 业务逻辑层与数据访问层之间的接口
 * 规定了两层之间的方法调用协议
 * @author chen
 *
 */
public interface ISuperQQDao {
	/**
	 * 根据账号查询用户
	 * @param userName
	 * @return
	 */
	UserBean findUserByUserName(String userName);

	/**
	 * 查找所有包含userName的用户
	 * @param userName：查找的用户userName
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);

	/**
	 * 查找所有昵称中包含nick的用户
	 * @param nick：昵称
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);

	/**
	 * 查找所有允许获取位置信息的用户，除了当前用户之外
	 * @param userName：当前用户的账号
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);

	/**
	 * 添加用户，注册用户
	 * @param user：用户信息
	 * @return
	 */
	boolean addUser(UserBean user);

	/**
	 * 删除用户，取消注册使用
	 * @param userName：用户信息
	 * @return
	 */
	boolean deleteUser(String userName);

	/**
	 * 修改更新用户，用于修改昵称，头像，所属群等信息
	 * @param user：用户信息
	 * @return
	 */
	boolean updateUser(UserBean user);

	/**
	 * 检查userName的好友name在contact表中是否存在
	 * @param userName：当前用户的账号
	 * @param name：好友的账号
	 * @return
	 */
	boolean isExistsContact(String userName,String name);

	/**
	 * 查找当前用户的好友
	 * @param myuid：当前用户在数据表中的id
	 * @param cuid：好友的id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);

	/**
	 * 查找账号为userName的用户的所有好友
	 * @param userName：当前用户账号
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);

	/**
	 * 查找id为myuid的所有好友
	 * @param myuid：查找的uid
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);

	/**
	 * 添加好友
	 * @param userName：当前用户账号
	 * @param name：添加的好友账号
	 * @return
	 */
	ContactBean addContact(String userName,String name);

	/**
	 * 删除联系人
	 * @param myuid：当前用户id
	 * @param cuid：要删除的联系人id
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);

	/**
	 * 修改更新联系人
	 * @param contact：联系人信息
	 * @return
	 */
	boolean updateContact(ContactBean contact);

	/**
	 * 创建群，想group表插入一条表示群的记录
	 * @param group：群信息
	 * @return
	 */
	boolean createGroup(GroupBean group);
	
	/**
	 * 修改群信息
	 * @param group：群信息
	 * @param newGroupName：要修改的新群名
	 * @return
	 */
	boolean updateGroupName(GroupBean group,String newGroupName);

	/**
	 * 删除指定群
	 * @param groupName：群名
	 * @return
	 */
	boolean deleteGroup(String groupName);

	/**
	 * 查询账号为userName的所属群
	 * @param userName：查询的账号
	 * @return
	 */
	GroupBean[] findAllGroup(String userName);

	/**
	 * 查询账号为userName的所有的公开群
	 * @param userName：查询的账号
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	GroupBean[] findPublicGroup(String userName,int pageId,int pageSize);

	/**
	 * 查询指定群
	 * @param groupName：查询的群名
	 * @return
	 */
	GroupBean findGroupByGroupName(String groupName);

	/**
	 * 查询指定群
	 * @param groupId：查询的群ID
	 * @return
	 */
	GroupBean findGroupByGroupId(String groupId);

	/**
	 * 添加群成员
	 * @param groupName：群名
	 * @param memeberName：添加的成员账号
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);

	/**
	 * 删除群成员，T群
	 * @param groupName：群名
	 * @param memberName：被T成员的账号
	 * @return
	 */
	boolean deleteGroupMember(String groupName,String memberName);

	/**
	 * 查找指定群的所有用户
	 * @param groupName：指定群名称
	 * @return
	 */
	String[] findGroupMembersByGroupName(String groupName);

	/**
	 * 修改用户的群信息
	 * @param uId：修改的用户
	 * @param groups：更新的群信息
	 * @return
	 */
	public boolean updateUser4Group(int uId,String groups);

	/**
	 * 修改群图片
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name,String avatar);






}
