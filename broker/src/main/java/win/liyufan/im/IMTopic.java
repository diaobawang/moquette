package win.liyufan.im;

public interface IMTopic {
	String SendMessageTopic = "MS";
	String PullMessageTopic = "MP";
	String NotifyMessageTopic = "MN";
	
	String CreateGroupTopic = "GC";
	String AddGroupMemberTopic = "GAM";
	String KickoffGroupMemberTopic = "GKM";
	String QuitGroupTopic = "GQ";
	String DismissGroupTopic = "GD";
	String ModifyGroupInfoTopic = "GMI";
	String GetGroupInfoTopic = "GPGI";
    String GetUserInfoTopic = "UPUI";
	String GetGroupMemberTopic = "GPGM";
    String GetMyGroupsTopic = "GMG";
    String TransferGroupTopic = "GTG";
	String GetQiniuUploadTokenTopic = "GQNUT";
    String ModifyMyInfoTopic = "MMI";

    String AddFriendRequestTopic = "FAR";
    String HandleFriendRequestTopic = "FHR";
    String DeleteFriendTopic = "FDL";
}
