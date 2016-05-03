package model;

import java.sql.Blob;
import java.sql.Date;

public class GreenEntry {
	
	private int postId;
	private int postedByUserId; // or user object
	private String postType;
	private String postMessage;
	private String datePosted;
	private String postImageURL;
	private int numOfShares;
	private int numOfStars;
	
	public GreenEntry(){
		
	}
	
	public GreenEntry(int usr, String postTyp, String postMsg, String pic) {
		this.postedByUserId=usr;
		this.postType=postTyp;
		this.postMessage=postMsg;
		this.postImageURL=pic;
	}
	
	
	public GreenEntry(int postidInt,int userid_posted, String postTypeString, String postMsg, String blob, Date date) {
		this.postId=postidInt;
		this.postedByUserId = userid_posted;
		this.postType = postTypeString;
		this.postMessage = postMsg;
		this.postImageURL=blob.toString();
		this.datePosted = date.toString();
		// TODO Auto-generated constructor stub
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getPostedByUserId() {
		return postedByUserId;
	}
	public void setPostedByUserId(int postedByUserId) {
		this.postedByUserId = postedByUserId;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getPostMessage() {
		return postMessage;
	}
	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}
	public String getPostImageURL() {
		return postImageURL;
	}
	public void setPostImageURL(String postImageURL) {
		this.postImageURL = postImageURL;
	}
	public int getNumOfShares() {
		return numOfShares;
	}
	public void setNumOfShares(int numOfShares) {
		this.numOfShares = numOfShares;
	}
	public int getNumOfStars() {
		return numOfStars;
	}
	public void setNumOfStars(int numOfStars) {
		this.numOfStars = numOfStars;
	}


	@Override
	public String toString() {
		return "GreenEntry [postId=" + postId + ", postedByUserId=" + postedByUserId + ", postType=" + postType
				+ ", postMessage=" + postMessage + ", datePosted=" + datePosted + ", postImageURL=" + postImageURL
				+ ", numOfShares=" + numOfShares + ", numOfStars=" + numOfStars + "]";
	}
	
	
}
