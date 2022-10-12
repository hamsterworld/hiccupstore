package hiccup.hiccupstore.product.dto.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;


/*
@Data:
Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value
 */
@Data
public class PageCriteria {
    private int pageNum ; // 보여줄 페이지 번호
    private int amountInOnePage ; // x개씩 보기
    private Integer type; // Type Parameter
    private Integer p; // priceRange Parameter
    private String sort; // 정렬기준


    public PageCriteria() {
        this.pageNum = 1 ;
        this.amountInOnePage = 16;
    }
/*
    // liquor 조회 때 사용될 생성자
    public PageCriteria(int pageNum, int amountInOnePage, String type, String sort) {
        this.pageNum = pageNum;
        this.amountInOnePage = amountInOnePage;
        this.category = type;
        this.sort = sort ;
    }
*/
/*
    // price 조회 때 사용될 생성자
    public PageCriteria(int pageNum, int amountInOnePage, int p, String sort){
        this.pageNum = pageNum;
        this.amountInOnePage = amountInOnePage;
        this.p = p;
        this.sort = sort ;
    }
*/
/*
    // 상세 검색 조건에 대한 입력값 split 기준은 개발할때 정하기(2022.08.04 기준 - 개발 X )
    public String[] getSearchTypeArr(){
        return searchType == null ? new String[] {} : searchType.split();
    }
*/

    // 아래 메서드는 수정/삭제 작업 이후 다시 원상태로 돌려놓을 때, 사용하는 메서드
    public String getPagingListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
        if(this.p != null) { // category List
            builder.queryParam("pageNum", pageNum)
                    .queryParam("viewCnt", amountInOnePage)
                    .queryParam("type", type)
                    .queryParam("sort", sort);
        }else { // priceRange List
            builder.queryParam("pageNum", pageNum)
                    .queryParam("viewCnt", amountInOnePage)
                    .queryParam("p", p)
                    .queryParam("sort", sort);
        }
        System.out.println("Build 된 URI : " + builder.toUriString());
        return builder.toUriString();
    }
}
