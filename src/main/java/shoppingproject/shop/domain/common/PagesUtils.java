package shoppingproject.shop.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagesUtils {

    private int totalPage; // 전체페이지수
    private int currentPage; // 사용자가 클릭한 페이지 넘버
    private int endPage; // 사용자가 클릭한 페이지 기준으로 마지막 넘버
    private int startPage; // 사용자가 클릭한 페이지 기준으로 처음 넘버
    private int displayPageNum =10; // 한번에 표시할 갯수
    private int startItemNum ; // 처음가져올 아이템 순서
    private boolean next = false; // 다음 버튼 visible 유무
    private boolean previous = false; // 이전 버튼 visible 유무


    public PagesUtils(int totalItems, int currentPage){
       // PagesUtils pagesUtils = new PagesUtils();
        int tPage = (totalItems -1) / this.displayPageNum +1 ;
        this.totalPage =tPage;
        this.currentPage= currentPage;
        // 현재 페이지 기준 마지막 번호
        int ePage = (int)( (this.currentPage - 1) / this.displayPageNum +1) * this.displayPageNum;
        if(ePage ==this.totalPage) this.next =true;
        if(this.totalPage <ePage) ePage = this.totalPage;
        this.endPage =ePage;
        // 현재 페이지 기준 처음 번호
        int sPage = ((this.currentPage -1 ) /10) * this.displayPageNum +1;
        this.startPage =sPage;
        if(this.startPage !=1) this.previous =true;

        this.startItemNum = 10 * (this.currentPage -1);
    }



}
