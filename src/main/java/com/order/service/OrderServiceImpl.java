package com.order.service;

import com.details.vo.DetailsVO;
import com.order.dao.OrderDAO;
import com.order.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public OrderVO insertWithOrdDetails(OrderVO orderVO, List<DetailsVO> list) {
        return orderDAO.insertWithOrdDetails(orderVO,list);
    }

    @Override
    public List<OrderVO> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public List<OrderVO> getMemberOrders(Integer memNo) {
        return orderDAO.getMemberOrders(memNo);
    }

    @Override
    public Set<DetailsVO> getOrderDetails_ByOrdno(Integer ordNo) {
        return orderDAO.getOrderDetails_ByOrdno(ordNo);
    }
}
