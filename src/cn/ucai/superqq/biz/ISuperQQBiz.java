package cn.ucai.superqq.biz;

import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.GroupBean;
import cn.ucai.superqq.bean.UserBean;

/**
 * Servlet层与业务逻辑成的接口
 * @author chen
 *
 */
public interface ISuperQQBiz {
	/**
	 * 注册
	 * @param user：用户信息
	 * @return
	 * @throws Exception
	 */
	boolean register(UserBean user) throws Exception;
	/**
	 * 取消注册
	 * @param userName：要取消的账号
	 * @return
	 */
	boolean unRegister(String userName);
	/**
	 * 登陆
	 * @param userName：账号
	 * @param password：密码
	 * @return
	 * @throws Exception
	 */
	UserBean login(String userName,String password) throws Exception;
	/**
	 * 通过账号查询用户
	 * @param userName：要查询的账号
	 * @return
	 */
	UserBean findUserByUserName(String userName);
	/**
	 * 查询符合条件的所有用户
	 * @param userName：查询条件
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	/**
	 * 查询包含nick的所有用户
	 * @param nick：查询条件
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	/**
	 * 查询不包含userName的所有用户
	 * @param userName：查询条件
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);
	/**
	 * 修改用户信息
	 * @param user：更新的用户信息
	 * @return
	 */
	boolean updateUser(UserBean user);
	/**
	 * 擦好像联系人信息
	 * @param myuid：查询条件，当前用户id
	 * @param cuid：查询条件，联系人id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	/**
	 * 查询账号是userName的所有联系人
	 * @param userName：查询条件，让其用户账号
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	/**
	 * 查询指定id用户的所有联系人信息
	 * @param myuid：当前用户id
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);
	/**
	 * 添加联系人信息
	 * @param userName：当前用户账号
	 * @param name：添加的联系人账号
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	/**
	 * 删除联系人
	 * @param myuid：当前用户id
	 * @param cuid：要删除的用户id
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	/**
	 * 修改联系人信息
	 * @param contact：更新的联系人信息
	 * @return
	 */
	boolean updateContact(ContactBean contact);
	/**
	 * 创建群，想groups表插入一条表示群的记录
	 * @param group：群信息
	 * @return
	 */
	boolean createGroup(GroupBean group);
	/**
	 * 修改群信息
	 * @param group：指定群的信息
	 * @param newGroupName
	 * @return
	 */
	boolean updateGroupName(GroupBean group,String newGroupName);
	/**
	 * 删除指定群
	 * @param groupName：要删除的群信息
	 * @return
	 */
	boolean deleteGroup(String groupName);
	/**
	 * 查询所有的群
	 * @param userName：查询条件
	 * @return
	 */
	GroupBean[] findAllGroup(String userName);
	/**
	 * 查找所有公开群
	 * @param userName：查询条件
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	GroupBean[] findPublicGroup(String userName,int pageId,int pageSize);
	/**
	 * 查询指定群
	 * @param groupName：查询条件，群名
	 * @return
	 */
	GroupBean findGroupByGroupName(String groupName);
	/**
	 * 查询指定群
	 * @param groupId：查询条件，groupid
	 * @return
	 */
	GroupBean findGroupByGroupId(String groupId);
	/**
	 * 添加群成员，群组已经创建时使用
	 * @param groupName：群名
	 * @param memberName：群成员
	 * @return
	 */
	boolean addGroupMembers(String groupName,String memberName);
	/**
	 * 添加群成员，创建群组时使用
	 * @param groupName：群名
	 * @param memberName：群成员
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);
	/**
	 * 删除群成员，T群或者群程云自动退出群
	 * @param groupName：群名
	 * @param memberName：群成员账号
	 * @return
	 */
	boolean deleteGroupMember(String groupName,String memberName);
	/**
	 * 修改群图片主文件名
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name,String avatar);
	

}
