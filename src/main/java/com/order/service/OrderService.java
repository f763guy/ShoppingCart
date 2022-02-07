package com.order.service;

import com.details.vo.DetailsVO;
import com.order.vo.OrderVO;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderVO insertWithOrdDetails(OrderVO orderVO , List<DetailsVO> list);
    List<OrderVO> getAll();
    List<OrderVO> getMemberOrders(Integer memNo);
    Set<DetailsVO> getOrderDetails_ByOrdno(Integer ordNo);
}
