package hiccup.hiccupstore.order.service;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.order.dao.OrderMapper;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.order.dto.OrderProductInfo;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    public int insertOrder(Order order){
        return orderMapper.insertOrder(order);
    }
    public void deleteOrder(int orderId){
        orderMapper.deleteOrder(orderId);
    }
    public Order getOrder(int orderId){
        return orderMapper.getOrder(orderId);
    }

    public void insertOrderProducts(List<OrderProduct> orderProducts){
        orderMapper.insertOrderProducts(orderProducts);
    }

    public void deleteOrderProduct(int orderProductId){
        orderMapper.deleteOrderProduct(orderProductId);
    }
    public List<OrderProduct> getOrderProduct(int orderProductId){
        List<OrderProduct> orderProduct = orderMapper.getOrderProduct(orderProductId);
        return orderProduct;
    }

    public List<Cart> getCarts(int userId){
        return orderMapper.getCarts(userId);
    }

    public Product getProduct(int productId){
        return orderMapper.getProduct(productId);
    }

    public void deleteCart(int userId){
        orderMapper.deleteCart(userId);
    }

    public List<OrderProductInfo> getOrderProductList(List<Integer> orderProducts){
        return orderMapper.getOrderProductList(orderProducts);
    }

    public void updateProductQuantity(List<OrderProduct> orderProducts){
        orderMapper.updateProductQuantity(orderProducts);
    }




}
