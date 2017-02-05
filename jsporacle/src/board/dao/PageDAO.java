package board.dao;

public class PageDAO {
	public static final int PAGE_SCALE = 2;	//페이지당 게시물수
	public static final int BLOCK_SCALE = 5;	//한 화면에 출력할 페이지 수
	private int curPage;		//현재 페이지
	private int prevPage;		//이전 페이지
	private int nextPage;		//다음 페이지
	private int totPage;		//전체 페이지 갯수
	private int totBlock;		//전체 블록 갯수
	private int curBlock;		//현재 페이지 블록
	private int prevBlock;		//이전 페이지 블록
	private int nextBlock;		//다음 페이지 블록
	private int pageBegin;		//현재 페이지의 시작 번호
	private int pageEnd;		//현재 페이지의 끝 번호
	private int blockStart;		//현재 블록의 시작 번호
	private int blockEnd;		//현재 블록의 끝 번호
	
	//생성자(레코드 갯수, 현재 페이지 번호)
	public PageDAO(int count, int curPage){
		curBlock=1;				//현재 페이지 블록 번호
		this.curPage = curPage;	//현재 페이지 설정
		setTotPage(count);		//전체 페이지 갯수 계산
		setPageRange();			//페이지의 식작, 끝 번호 계산
		setTotBlock();			//전체 페이지 블록 갯수
		setBlockRange();		//블록의 시작, 끝 번호 계산
	}
	
	private void setBlockRange() {
		//현재 페이지가 몇번째 페이지 블록에 속하는지 계산
		curBlock = (int)Math.ceil((curPage-1)/BLOCK_SCALE)+1;
		//현재 페이지 블록의 시작, 끝번호 계산
		blockStart = (curBlock-1)*BLOCK_SCALE+1;
		blockEnd = blockStart+BLOCK_SCALE-1;
		//마지막 블록이 범위를 초과하지 않도록 처리
		if( blockEnd> totPage){
			blockEnd = totPage;
		}
		// [이전]을 눌렀을 때 이동할 페이지 번호
		prevPage = curBlock==1? 1 : (curBlock-1) * BLOCK_SCALE;
		// [다음]을 눌렀을 때 이동할 페이지 번호
		nextPage = curBlock > totBlock ? (curBlock*BLOCK_SCALE) : (curBlock*BLOCK_SCALE)+1;
		//마지막 페이지가 범위를 초과하지 않도록 처리
		if(nextPage >= totPage){
			nextPage = totPage;
		}
		
	}

	private void setPageRange() {
		//where rn between #{start} and #{end}
		//#{start}에 입력될 값
		pageBegin = (curPage-1) * PAGE_SCALE +1;
		//#{end}에 입력될 값
		pageEnd = pageBegin + PAGE_SCALE -1;
		
	}

	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getTotPage() {
		return totPage;
	}
	//전체 페이지 갯수 계싼
	public void setTotPage(int count) {
		//Math.ceil() 올림,  Math.floor() 버림, Math.round() 반올림
		totPage = (int)Math.ceil(count*1.0 / PAGE_SCALE);
	}
	public int getTotBlock() {
		return totBlock;
	}
	//전체 블록 갯수 설정
	public void setTotBlock() {
		totBlock = (int)Math.ceil(totPage / BLOCK_SCALE);
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getPrevBlock() {
		return prevBlock;
	}
	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}
	public int getNextBlock() {
		return nextBlock;
	}
	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}
	public int getPageBegin() {
		return pageBegin;
	}
	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getBlockStart() {
		return blockStart;
	}
	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}
	public int getBlockEnd() {
		return blockEnd;
	}
	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}
	
}
