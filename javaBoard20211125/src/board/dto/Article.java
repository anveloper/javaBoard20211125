package board.dto;

public class Article extends Dto {
	public String title; // 게시물 번호
	public String body; // 게시물 번호
	public int hit; // 게시물 조회수
	public int writerId;
	public String writerName;
	public Article(int id, String regDate, String title, String body, int writerId, String writer) {
		this(id, regDate, title, body, writerId, writer, 0);
	}

	public Article(int id, String regDate, String title, String body, int writerId, String writerName, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.writerId = writerId;
		this.writerName = writerName;
		this.hit = hit;
	}

	public void increaseHit() {
		hit++;
	}

}
