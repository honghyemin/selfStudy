package board;

import java.util.Date;

public class BoardVO {
	private int level;
	private int articleNum;
	private int parentNum;
	private String title;
	private String content;
	private String imageFileName;
	private String id;
	private Date writeDate;
	
	
	public BoardVO() {
		super();
	}
	
	


	@Override
	public String toString() {
		return "BoardVO [level=" + level + ", articleNum=" + articleNum + ", parentNum=" + parentNum + ", title="
				+ title + ", content=" + content + ", imageFileName=" + imageFileName + ", id=" + id + ", writeDate="
				+ writeDate + "]";
	}




	public BoardVO(int level, int articleNum, int parentNum, String title, String content, String imageFileName,
			String id) {
		super();
		this.level = level;
		this.articleNum = articleNum;
		this.parentNum = parentNum;
		this.title = title;
		this.content = content;
		this.imageFileName = imageFileName;
		this.id = id;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getArticleNum() {
		return articleNum;
	}


	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}


	public int getParentNum() {
		return parentNum;
	}


	public void setParentNum(int parentNum) {
		this.parentNum = parentNum;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getImageFileName() {
		return imageFileName;
	}


	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getWriteDate() {
		return writeDate;
	}


	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	
	
	

}
