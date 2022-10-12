package hiccup.hiccupstore.order.dao;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.order.dto.OrderProductInfo;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {

    int insertOrder(Order order);
    void deleteOrder(int orderId);
    Order getOrder(int orderId);
    void insertOrderProducts(List<OrderProduct> orderProducts);
    void deleteOrderProduct(int orderProductId);
    List<OrderProduct> getOrderProduct(int orderProductId);

    List<Cart> getCarts(int userId);

    Product getProduct(int productId);
    void deleteCart(int userId);

    List<OrderProductInfo> getOrderProductList(List<Integer> orderProducts);

    void updateProductQuantity(List<OrderProduct> orderProducts);


}
