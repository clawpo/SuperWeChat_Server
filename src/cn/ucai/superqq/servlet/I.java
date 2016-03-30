package cn.ucai.superqq.servlet;

public interface I {

	String AVATAR_PATH = "//Users/clawpo/work/ucai/work/projects/SuperWeChat/superqq/";
	String PAGE_ID = "pageId";
	String PAGE_SIZE = "pageSize";
	
	public static interface User {
		String TABLE_NAME = "user";
		String ID = "id";
		String USER_NAME = "userName";
		String NICK = "nick";
		String AVATAR = "avatar";
		String HEADER = "header";
		String PASSWORD = "password";
		String LATITUDE = "latitude";
		String LONGITUDE = "longitude";
		String GROUPS = "groups";
		String UN_READ_MSG_COUNT = "unreadMsgCount";
	}
	
	public static interface Contact extends User {
		String TABLE_NAME = "contact";
		String NAME = "name";
		String MYUID = "myuid";
		String CUID = "cuid";
		String IS_GET_MY_LOCATION = "isGetMyLocation";
		String IS_SHOW_MY_LOCATION = "isShowMyLocation";
	}
	
	public static interface Group {
		String TABLE_NAME = "groups";
		String ID = "id";// 主键
		String GROUP_ID = "groupId";// 主键
		String NAME = "name";// 群名
		String GROUP_NAME = "groupName";// 群名，url中使用
		String NEW_NAME = "new_name";// 群新名称
		String AVATAR = "avatar";// 群图标
		String INTRO = "intro";// 群简介
		String OWNER = "owner";// 群主账号
		String IS_PUBLIC = "isPublic";// 是否公开
		String MODIFIED_TIME = "modifiedTime";// 群信息修改的时间，单位：毫秒
		String MEMBERS = "members";// 群成员的账号
		String IS_EXAME = "isExame";// 群成员的账号
	}

	/** 上传图片的类型：user_avatar或group_icon */
	String AVATAR_TYPE = "avatarType";
	String KEY_REQUEST = "request";
	String REQUEST_SERVERSTATUS = "server_status";
	/**
	 * 客户端发送的注册请求
	 */
	String REQUEST_REGISTER = "register";

	/**
	 * 发送取消注册的请求
	 */
	String REQUEST_UNREGISTER = "unregister";

	/**
	 * 客户端上传头像的请求
	 */
	String REQUEST_UPLOAD_AVATAR = "upload_avatar";
	/**
	 * 客户端发送的登陆请求
	 */
	String REQUEST_LOGIN = "login";
	String REQUEST_DOWNLOAD_AVATAR = "download_avatar";
	String REQUEST_DOWNLOAD_GROUP_AVATAR = "download_group_avatar";
	String ISON8859_1 = "iso8859-1";
	String UTF_8 = "utf-8";
	String REQUEST_DOWNLOAD_CONTACTS = "download_contacts";
	String REQUEST_DOWNLOAD_CONTACT_LIST = "download_contact_list";
	String REQUEST_DELETE_CONTACT = "delete_contact";
	String REQUEST_ADD_CONTACT = "add_contact";
	String REQUEST_FIND_USER = "find_user";
	String REQUEST_DOWNLOAD_CONTACT = "download_contacts";
	String REQUEST_UPLOAD_LOCATION = "upload_location";
	String REQUEST_DOWNLOAD_LOCATION = "download_location";
	String REQUEST_CREATE_GROUP = "create_group";
	String REQUEST_ADD_GROUP_MEMBER = "add_group_member";
	String REQUEST_ADD_GROUP_MEMBERS = "add_group_members";
	String REQUEST_UPDATE_GROUP_NAME = "update_group_name";
	String REQUEST_DOWNLOAD_GROUP_MEMBERS = "download_group_members";
	String REQUEST_DELETE_GROUP_MEMBER = "delete_group_member";
	String REQUEST_DELETE_GROUP = "delete_group";
	String REQUEST_DOWNLOAD_GROUPS = "download_groups";
	String REQUEST_FIND_PUBLIC_GROUPS = "download_public_groups";
	String REQUEST_FIND_GROUP = "find_group_by_group_name";
}
