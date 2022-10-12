package hiccup.hiccupstore.product.dto.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@NoArgsConstructor
public class ViewCriteria {
    private Integer type = null; // Type Parameter
    private Integer p = null; // priceRange Parameter
    private String sort = "default"; // 정렬기준
    private int start,end, cntPerPage;
    private String searchKeyword = null ;

    public String[] getSearchTypeArr(){
        return searchKeyword == null ? new String[] {} : searchKeyword.split("");
    }
    public void calcStartEnd(int currentPage, int cntPerPage){
        this.cntPerPage = cntPerPage ;
        this.end = currentPage * cntPerPage;
        this.start = getEnd() - (cntPerPage) ;
    }

}
