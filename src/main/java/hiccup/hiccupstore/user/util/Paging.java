package hiccup.hiccupstore.user.util;

import lombok.Data;

@Data
public class Paging {

    private int totalCnt; //총 게시물 개수
    private int pageSize; //한페이지의 크기
    private int navSize = 5; //페이지 네비게이션의 크기
    private int totalPage; //전체 페이지의 개수
    private int page; //현재 페이지
    int beginPage;
    int endPage;
    boolean showPrev;
    boolean showNext;


    public Paging(int totalCnt, int page, int pageSize) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        totalPage = (int)Math.ceil((double)totalCnt/pageSize);
        beginPage = (page / navSize) * navSize + 1;
        endPage = Math.min(beginPage + navSize - 1,totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;

    }

    void print(){
        System.out.println("page = " + page);
        System.out.print(showPrev ? "[prev]" : "");
        for(int i = beginPage; i <= endPage; i++){
            System.out.print(i + " ");
        }
        System.out.print(showNext ? "[Next]" : "");
    }

}
