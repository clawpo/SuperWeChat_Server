package cn.ucai.superqq.biz;

import cn.ucai.superqq.bean.ContactBean;
import cn.ucai.superqq.bean.GroupBean;
import cn.ucai.superqq.bean.UserBean;

/**
 * Servlet����ҵ���߼��ɵĽӿ�
 * @author chen
 *
 */
public interface ISuperQQBiz {
	/**
	 * ע��
	 * @param user���û���Ϣ
	 * @return
	 * @throws Exception
	 */
	boolean register(UserBean user) throws Exception;
	/**
	 * ȡ��ע��
	 * @param userName��Ҫȡ�����˺�
	 * @return
	 */
	boolean unRegister(String userName);
	/**
	 * ��½
	 * @param userName���˺�
	 * @param password������
	 * @return
	 * @throws Exception
	 */
	UserBean login(String userName,String password) throws Exception;
	/**
	 * ͨ���˺Ų�ѯ�û�
	 * @param userName��Ҫ��ѯ���˺�
	 * @return
	 */
	UserBean findUserByUserName(String userName);
	/**
	 * ��ѯ���������������û�
	 * @param userName����ѯ����
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	/**
	 * ��ѯ����nick�������û�
	 * @param nick����ѯ����
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	/**
	 * ��ѯ������userName�������û�
	 * @param userName����ѯ����
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);
	/**
	 * �޸��û���Ϣ
	 * @param user�����µ��û���Ϣ
	 * @return
	 */
	boolean updateUser(UserBean user);
	/**
	 * ��������ϵ����Ϣ
	 * @param myuid����ѯ��������ǰ�û�id
	 * @param cuid����ѯ��������ϵ��id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	/**
	 * ��ѯ�˺���userName��������ϵ��
	 * @param userName����ѯ�����������û��˺�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	/**
	 * ��ѯָ��id�û���������ϵ����Ϣ
	 * @param myuid����ǰ�û�id
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);
	/**
	 * �����ϵ����Ϣ
	 * @param userName����ǰ�û��˺�
	 * @param name����ӵ���ϵ���˺�
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	/**
	 * ɾ����ϵ��
	 * @param myuid����ǰ�û�id
	 * @param cuid��Ҫɾ�����û�id
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	/**
	 * �޸���ϵ����Ϣ
	 * @param contact�����µ���ϵ����Ϣ
	 * @return
	 */
	boolean updateContact(ContactBean contact);
	/**
	 * ����Ⱥ����groups�����һ����ʾȺ�ļ�¼
	 * @param group��Ⱥ��Ϣ
	 * @return
	 */
	boolean createGroup(GroupBean group);
	/**
	 * �޸�Ⱥ��Ϣ
	 * @param group��ָ��Ⱥ����Ϣ
	 * @param newGroupName
	 * @return
	 */
	boolean updateGroupName(GroupBean group,String newGroupName);
	/**
	 * ɾ��ָ��Ⱥ
	 * @param groupName��Ҫɾ����Ⱥ��Ϣ
	 * @return
	 */
	boolean deleteGroup(String groupName);
	/**
	 * ��ѯ���е�Ⱥ
	 * @param userName����ѯ����
	 * @return
	 */
	GroupBean[] findAllGroup(String userName);
	/**
	 * �������й���Ⱥ
	 * @param userName����ѯ����
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ��ѯ�ļ�¼��
	 * @return
	 */
	GroupBean[] findPublicGroup(String userName,int pageId,int pageSize);
	/**
	 * ��ѯָ��Ⱥ
	 * @param groupName����ѯ������Ⱥ��
	 * @return
	 */
	GroupBean findGroupByGroupName(String groupName);
	/**
	 * ��ѯָ��Ⱥ
	 * @param groupId����ѯ������groupid
	 * @return
	 */
	GroupBean findGroupByGroupId(String groupId);
	/**
	 * ���Ⱥ��Ա��Ⱥ���Ѿ�����ʱʹ��
	 * @param groupName��Ⱥ��
	 * @param memberName��Ⱥ��Ա
	 * @return
	 */
	boolean addGroupMembers(String groupName,String memberName);
	/**
	 * ���Ⱥ��Ա������Ⱥ��ʱʹ��
	 * @param groupName��Ⱥ��
	 * @param memberName��Ⱥ��Ա
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);
	/**
	 * ɾ��Ⱥ��Ա��TȺ����Ⱥ�����Զ��˳�Ⱥ
	 * @param groupName��Ⱥ��
	 * @param memberName��Ⱥ��Ա�˺�
	 * @return
	 */
	boolean deleteGroupMember(String groupName,String memberName);
	/**
	 * �޸�ȺͼƬ���ļ���
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name,String avatar);
	

}
