package win.liyufan.im;

public interface IMTopic {
	public static final String SendMessageTopic = "MS";
	public static final String PullMessageTopic = "MP";
	public static final String NotifyMessageTopic = "MN";
	
	public static final String CreateGroupTopic = "GC";
	public static final String AddGroupMemberTopic = "GAM";
	public static final String KickoffGroupMemberTopic = "GKM";
	public static final String QuitGroupTopic = "GQ";
	public static final String DismissGroupTopic = "GD";
	public static final String ModifyGroupInfoTopic = "GMI";
	
	public static final String GetGroupInfoTopic = "GPGI";
	public static final String GetGroupMemberTopic = "GPGM";
	
	public static final String GetQiniuUploadTokenTopic = "GQNUT";
}
