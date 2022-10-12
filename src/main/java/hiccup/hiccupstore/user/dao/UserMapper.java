package hiccup.hiccupstore.user.dao;

import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dto.board.*;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /** Homepage 관련 SQL문*/
    @Select("select * from product as p inner join product_image as i on p.productid = i.productid where i.imageType like '%PRODUCT%' order by rand() limit 0,4")
    public List<ProductDto> getProductDtoList();
    @Select("select * from product as p inner join product_image as i on p.productid = i.productid where i.imageType like '%PRODUCT%' order by rand() limit 0,12")
    public List<ProductDto> getProductDtoList2();

    /** 회원 관련 SQL문 */

    public Integer save(UserDto userDto);

    @Select("select * from user where userName = #{userName}")
    public UserDto getUser(@Param("userName") String userName);

    @Select("select username from user where username = #{username}")
    public String searchUserName(@Param("username") String username);

    @Select("select email from user where email = #{email}")
    public String searchEmail(@Param("email") String username);

    @Select("select phone from user where phone = #{phone}")
    public String searchMobile(@Param("phone") String phone);

    @Select("select userName from user where nickname = #{nickname} and email = #{email}")
    public String searchUserNameByEmail(@Param("nickname") String nickname,@Param("email") String email);

    @Select("select userName from user where nickname = #{nickname} and phone = #{phone}")
    public String searchUserNameByPhone(@Param("nickname") String nickname,@Param("phone") String phone);

    @Select("select password from user where username = #{username} and email = #{email}")
    public String searchPasswordByEmail(@Param("username") String username,@Param("email") String email);

    @Select("select password from user where username = #{username} and phone = #{phone}")
    public String searchPasswordByPhone(@Param("username") String username,@Param("phone") String phone);

    /** 주문목록 관련 SQL문 */

    public Integer saveOrder(OrderDto order);

    @Select("select * from user_order where userId = #{userId}")
    public OrderDto getOrder(Integer userid);

    @Select("select * from user_order where userId = #{userId} order by orderid desc limit 0,5")
    public List<OrderDto> getOrderList(Integer userid);

    @Select("select * from user_order where userId = #{userId} order by orderid desc limit #{page},#{pagesize}")
    public List<OrderDto> getOrderListPage(Integer userId,Integer page,Integer pagesize);

    @Select("select count(*) from user_order where userid = #{userid}")
    public Integer getOrderListCount(Integer userId);

    public List<OrderLatelyProductDto> getOrderLatelyProductList(Integer userid);


    /** 최근본상품 관련 SQL문 */
    public List<ProductDto> getProductList(String[] LatelyProductSee);


    /** mypageorderlist */

    @Select("select * from test where testdate between \"2022-07-20\" and \"2022-07-31\"")
    public List<ProductDto> getproductlist();

    public List<OrderLatelyProductDto> getOrderLatelyProductListPageByDate(String startdate,String lastdate,Integer userid,
                                                                      Integer page,Integer pagesize);
    @Select("select count(*) from user_order")
    public Integer getOrderManagerListCount();

    @Select("select * from user_order where orderdate between #{startdate} and #{lastdate} order by orderid desc limit #{page},#{pagesize}")
    public List<OrderDto> getOrderListManagerPage(String startdate, String lastdate,Integer page,Integer pagesize);

    /** mypage 유저정보 변경 SQL문 */
    @Update("update user set email = #{email},phone = #{phone},address = #{address} where username = #{username}")
    public Integer updateuserinformation(String email,String phone,String address,String username);

    @Delete("delete from user where username = #{username}")
    public Integer deleteuser(String username);

    /** mypage 1:1문의 SQL문 */

    @Insert("insert into board (userid,boardtypeid,boardtitle,boardcontent,boardcategory)" +
            "values(#{userid},1,#{boardtitle},#{boardcontent},#{boardcategory})")
    public Integer saveBoard(Integer userid,String boardtitle,String boardcontent,String boardcategory);

    public Integer saveBoardImage(List<UploadFile> item);

    public Integer deleteBoardImage(List<String> item);

    @Select("select count(*) from board where userid = #{userid} and boardtypeid = #{boardtypeid}")
    public Integer FindBoardCountByUserId(Integer userid,Integer boardtypeid);

    @Select("select * from (select * from board where userid = #{userid} and boardtypeid = #{boardtypeid}) as b left join comment c on b.boardid = c.boardid order by b.boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> FindBoardByUserId(Integer userid, Integer page, Integer pagesize, Integer boardtypeid);

    @Select("select boardid from board where userid = #{userid} order by boardid desc limit 0,1")
    public Integer FindOneBoardByUserId(Integer userid);


    @Select("select board.boardid,userid,boardtitle,boardcontent,createdate,imagename from board inner join image on board.boardid = image.boardid where board.boardid = #{boardid}")
    public List<BoardDto2> FindOneBoardByBoardid(Integer boardid);

    @Select("select boardid,userid,boardtitle,boardcontent,createdate from board where boardid = #{boardid}")
    public List<BoardDto> FindOneBoardByBoardidNotimage(Integer boardid);


    /** mypage review comment SQL문*/
    @Select("select * from (select * from comment where boardid = #{boardid}) c inner join user u on u.userid = c.userid order by commentid desc ")
    List<CommentDto> getComments(Integer boardid);

    @Select("select * from board where userid = #{userid} and boardtypeid = #{boardtypeid} order by boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> FindReviewByUserId(Integer userid,Integer page,Integer pagesize,Integer boardtypeid);

    /** 관리자페이지 OrderList쪽 SQL문 */
    public List<OrderLatelyProductDto> getOrderLatelyProductListtManagerPage(Integer page,Integer pagesize);

    @Select("select count(*) from user_order")
    public Integer getOrderListAllCount();

    @Select("select * from user_order order by orderid desc limit #{page},#{pagesize}")
    public List<OrderDto> getOrderListAllPage(Integer page,Integer pagesize);

    @Update("update user_order set status = #{status} where orderid = #{orderid}")
    public Integer updateOrderStatus(Integer orderid,String status);

    public List<OrderLatelyProductDto> getOrderLatelyProductListManagerPage(String startdate,String lastdate,Integer page,Integer pagesize);


    /** 관리자페이지 1vs1쪽 SQL문 */

    @Select("select count(*) from board where boardtypeid = 1")
    public Integer getUser1vs1AllCount();

    @Select("select * from (select * from board where boardtypeid = 1 ) as b" +
            " left join comment c on b.boardid = c.boardid " +
            " left join user u on u.userid = b.userid" +
            " order by b.boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> getUser1vs1boardall(Integer page,Integer pagesize);

    public List<User1vs1BoardDto> getUser1vs1BoardOne(Integer boardid);

    @Insert("insert into comment (userid,boardid,commentcontent)" +
            "values(0,#{boardId},#{BoardContent})")
    public Integer Save1vs1UserAnswer(String BoardContent,Integer boardId);

    public TestDto getTest(Integer userid);

    @Delete("delete from board where boardid = #{boardid}")
    Integer deleteProductBoard(Integer boardid);

    /** 관리자페이지 product 답변 SQL문 */
    @Select("select count(*) from board where boardtypeid = 2")
    public Integer getUserProductAllCount();

    @Select("select * from (select * from board where boardtypeid = 2 ) as b" +
            " left join comment c on b.boardid = c.boardid" +
            " left join user u on u.userid = b.userid " +
            " order by b.boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> getUserProductboardall(Integer page,Integer pagesize);

    public List<User1vs1BoardDto> getUserProductBoardOne(Integer boardid);

    @Insert("insert into comment (userid,boardid,commentcontent)" +
            "values(0,#{boardid},#{BoardContent})")
    public Integer SaveProductUserAnswer(String BoardContent,Integer boardid);

    @Update("update user set address = #{address},nickname = #{nickname},phone = #{phone} where username = #{username} ")
    Integer updateuser(String username,String address,String nickname,String phone);

    @Update("UPDATE board SET boardtitle = #{boardtitle} , boardcontent = #{boardcontent}, boardcategory = #{boardcategory} " +
            "where boardid = #{boardid}")
    void update1vs1Board(String boardtitle,String boardcontent,String boardcategory,Integer boardid);


    /** notice 관련 SQL문*/
    @Select("select * from NoticeBoard order by noticeid desc limit 5")
    List<NoticeDto> getNoticeDtoList();
    @Select("select * from NoticeBoard where noticeid = #{noticeBoardId}")
    NoticeDto getnoticeBoardOne(Integer noticeBoardId);
    @Select("select * from NoticeBoard order by noticeid desc limit #{page},#{pageSize}")
    List<NoticeDto> getNoticeDtoListPaging(Integer page, Integer pageSize);
    @Select("select count(*) from NoticeBoard ")
    Integer getTotalNoticeCount();
    @Insert("insert into NoticeBoard (boardtitle,boardcontent,category,imagename) values (#{boardtitle},#{boardcontent},#{category},#{imagename})")
    void saveNotice(String boardtitle, String boardcontent, String category,String imagename);
    @Delete("delete from NoticeBoard where noticeid = #{noticeid}")
    void deleteNotice(Integer noticeid);
    @Update("update NoticeBoard set boardtitle = #{boardtitle} ,boardcontent = #{boardcontent} ,imagename = #{imagename} where noticeid = #{noticeid}")
    void updateNotice(Integer noticeid,String boardtitle,String boardcontent,String imagename);
    @Update("update NoticeBoard set boardtitle = #{boardtitle} ,boardcontent = #{boardcontent} where noticeid = #{noticeid}")
    void updateNoticeNotImageUpdate(Integer noticeid, String boardtitle, String boardcontent);
    @Update("update NoticeBoard set boardtitle = #{boardtitle} ,boardcontent = #{boardcontent} ,imagename = #{Null} where noticeid = #{noticeid}")
    void updateNoticeDeleteImageUpdate(Integer noticeid, String boardtitle, String boardcontent, Object Null);
    @Select("select count(*) from NoticeBoard where ${searchNoticeCategory} like concat('%',#{searchNoticeContent},'%')")
    Integer searchNoticeBoardCountBySearchNoticeContent(String searchNoticeCategory, String searchNoticeContent);
    @Select("select * from NoticeBoard where (${searchNoticeCategory} like concat('%',#{searchNoticeContent},'%')) order by noticeid desc limit #{page},#{pagesize}")
    List<NoticeDto> searchNoticeBoardBySearchNoticeContent(String searchNoticeCategory, String searchNoticeContent,Integer page,Integer pagesize);

    /** mypage refund SQL문*/
    List<OrderLatelyProductDto> getOrderLatelyRefundProductListPageByDate(String startdate, String lastdate, Integer userid, Integer page, Integer pagesize);
    @Select("select count(*) from user_order where userid = #{userId} and status in ('환불신청','교환신청','주문취소신청')")
    Integer getRefundOrderListCount(Integer userId);
    @Select("select * from user_order where userId = #{userId} and status in ('환불신청','교환신청','주문취소신청') order by orderid desc limit #{page},#{pageSize}")
    List<OrderDto> getRefundOrderListPage(Integer userId, Integer page, Integer pageSize);

    List<OrderLatelyProductDto> getOrderLatelyRefundResultProductListPageByDate(String startdate, String lastdate, Integer userid, Integer page, Integer pagesize);
    @Select("select count(*) from user_order where userid = #{userId} and status in ('환불완료','교환완료','주문취소')")
    Integer getRefundResultOrderListCount(Integer userId);
    @Select("select * from user_order where userId = #{userId} and status in ('환불완료','교환완료','주문취소') order by orderid desc limit #{page},#{pageSize}")
    List<OrderDto> getRefundResultOrderListPage(Integer userId, Integer page, Integer pageSize);

    /** managerpage order 검색 ByUserId*/
    List<OrderLatelyProductDto> getOrderLatelyProductListManagerPageByUserId(Integer searchUserId, Integer page, Integer pageSize);
    @Select("select count(*) from user_order where userid = #{searcUserId}")
    Integer getOrderManagerListCountbyUserId(Integer searchUserId);
    @Select("select * from user_order where userid = #{searchUserId}")
    List<OrderDto> getOrderListManagerPageByUserId(Integer searchUserId, Integer page, Integer pageSize);

}
