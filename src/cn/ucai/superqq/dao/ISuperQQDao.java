package cn.ucai.superqq.dao;

import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.UserBean;
import cn.ucai.superqq.bean.GroupBean;

/**
 * ҵ���߼��������ݷ��ʲ�֮��Ľӿ�
 * �涨������֮��ķ�������Э��
 * @author chen
 *
 */
public interface ISuperQQDao {
	/**
	 * �����˺Ų�ѯ�û�
	 * @param userName
	 * @return
	 */
	UserBean findUserByUserName(String userName);

	/**
	 * �������а���userName���û�
	 * @param userName�����ҵ��û�userName
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);

	/**
	 * ���������ǳ��а���nick���û�
	 * @param nick���ǳ�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);

	/**
	 * �������������ȡλ����Ϣ���û������˵�ǰ�û�֮��
	 * @param userName����ǰ�û����˺�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);

	/**
	 * ����û���ע���û�
	 * @param user���û���Ϣ
	 * @return
	 */
	boolean addUser(UserBean user);

	/**
	 * ɾ���û���ȡ��ע��ʹ��
	 * @param userName���û���Ϣ
	 * @return
	 */
	boolean deleteUser(String userName);

	/**
	 * �޸ĸ����û��������޸��ǳƣ�ͷ������Ⱥ����Ϣ
	 * @param user���û���Ϣ
	 * @return
	 */
	boolean updateUser(UserBean user);

	/**
	 * ���userName�ĺ���name��contact�����Ƿ����
	 * @param userName����ǰ�û����˺�
	 * @param name�����ѵ��˺�
	 * @return
	 */
	boolean isExistsContact(String userName,String name);

	/**
	 * ���ҵ�ǰ�û��ĺ���
	 * @param myuid����ǰ�û������ݱ��е�id
	 * @param cuid�����ѵ�id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);

	/**
	 * �����˺�ΪuserName���û������к���
	 * @param userName����ǰ�û��˺�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);

	/**
	 * ����idΪmyuid�����к���
	 * @param myuid�����ҵ�uid
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);

	/**
	 * ��Ӻ���
	 * @param userName����ǰ�û��˺�
	 * @param name����ӵĺ����˺�
	 * @return
	 */
	ContactBean addContact(String userName,String name);

	/**
	 * ɾ����ϵ��
	 * @param myuid����ǰ�û�id
	 * @param cuid��Ҫɾ������ϵ��id
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);

	/**
	 * �޸ĸ�����ϵ��
	 * @param contact����ϵ����Ϣ
	 * @return
	 */
	boolean updateContact(ContactBean contact);

	/**
	 * ����Ⱥ����group�����һ����ʾȺ�ļ�¼
	 * @param group��Ⱥ��Ϣ
	 * @return
	 */
	boolean createGroup(GroupBean group);
	
	/**
	 * �޸�Ⱥ��Ϣ
	 * @param group��Ⱥ��Ϣ
	 * @param newGroupName��Ҫ�޸ĵ���Ⱥ��
	 * @return
	 */
	boolean updateGroupName(GroupBean group,String newGroupName);

	/**
	 * ɾ��ָ��Ⱥ
	 * @param groupName��Ⱥ��
	 * @return
	 */
	boolean deleteGroup(String groupName);

	/**
	 * ��ѯ�˺�ΪuserName������Ⱥ
	 * @param userName����ѯ���˺�
	 * @return
	 */
	GroupBean[] findAllGroup(String userName);

	/**
	 * ��ѯ�˺�ΪuserName�����еĹ���Ⱥ
	 * @param userName����ѯ���˺�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	GroupBean[] findPublicGroup(String userName,int pageId,int pageSize);

	/**
	 * ��ѯָ��Ⱥ
	 * @param groupName����ѯ��Ⱥ��
	 * @return
	 */
	GroupBean findGroupByGroupName(String groupName);

	/**
	 * ��ѯָ��Ⱥ
	 * @param groupId����ѯ��ȺID
	 * @return
	 */
	GroupBean findGroupByGroupId(String groupId);

	/**
	 * ���Ⱥ��Ա
	 * @param groupName��Ⱥ��
	 * @param memeberName����ӵĳ�Ա�˺�
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);

	/**
	 * ɾ��Ⱥ��Ա��TȺ
	 * @param groupName��Ⱥ��
	 * @param memberName����T��Ա���˺�
	 * @return
	 */
	boolean deleteGroupMember(String groupName,String memberName);

	/**
	 * ����ָ��Ⱥ�������û�
	 * @param groupName��ָ��Ⱥ����
	 * @return
	 */
	String[] findGroupMembersByGroupName(String groupName);

	/**
	 * �޸��û���Ⱥ��Ϣ
	 * @param uId���޸ĵ��û�
	 * @param groups�����µ�Ⱥ��Ϣ
	 * @return
	 */
	public boolean updateUser4Group(int uId,String groups);

	/**
	 * �޸�ȺͼƬ
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name,String avatar);






}
