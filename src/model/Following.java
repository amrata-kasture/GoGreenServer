package model;

public class Following {
	
	private int userId;
	private int followId;
	private String status;
	
	public Following(){
		
	}
	
	public Following(int follower, int follow){
		this.userId=follower;
		this.followId=follow;
	}
	
	public Following(int follower, int follow, String stat){
		this.userId=follower;
		this.followId=follow;
		this.status=stat;
	}
	
	public void setStatusFollow(){
		this.status="Y";
	}
	
	public void setStatusUnFollow(){
		this.status="N";
	}
	
	public void setStatusFollowFailed(){
		this.status="X";
	}
	
	public boolean isFollowing(){
		if (status.equalsIgnoreCase("Y")){
			return true;
		}
		else if (status.equalsIgnoreCase("N")){
			return false;
		}
		return false;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int followerId) {
		this.userId = followerId;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
