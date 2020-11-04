package model;

public class Player {

	private String nickName;
	private int score;
	private Player father;
	private Player sonR;
	private Player sonL;
	
	public Player(String n, int s) {
		nickName = n;
		score = s;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Player getFather() {
		return father;
	}
	public void setFather(Player father) {
		this.father = father;
	}
	public Player getSonR() {
		return sonR;
	}
	public void setSonR(Player sonR) {
		this.sonR = sonR;
	}
	public Player getSonL() {
		return sonL;
	}
	public void setSonL(Player sonL) {
		this.sonL = sonL;
	}
}
