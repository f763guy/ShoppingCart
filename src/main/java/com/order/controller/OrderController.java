package com.order.controller;

import com.common.TokenForm;
import com.details.vo.DetailsVO;
import com.order.service.OrderService;
import com.order.vo.OrderVO;
import com.shopping.vo.CartVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping({"/order/confirmation"})
    @TokenForm(remove=true)
    public String insertNewOrd (HttpServletRequest request, HttpSession session){

        Integer memNo = new Integer(request.getParameter("memNo").trim());
        String receiver = request.getParameter("receiver").trim();
        String recPhone = request.getParameter("rec_phone").trim();
        String recZip = request.getParameter("rec_zip").trim();
        String recAddress = request.getParameter("rec_address").trim();
        Integer ordTotal = new Integer(request.getParameter("ord_total").trim());
        java.sql.Date ordDate = new java.sql.Date (System.currentTimeMillis());

        List<CartVO> cartlist = (List<CartVO>) session.getAttribute("shoppingcart");

        OrderVO orderVO = new OrderVO();
        orderVO.setMemNo(memNo);
        orderVO.setOrdStatus(1);
        orderVO.setReceiver(receiver);
        orderVO.setRecPhone(recPhone);
        orderVO.setRecZip(recZip);
        orderVO.setRecAddress(recAddress);
        orderVO.setOrdTotal(ordTotal);
        orderVO.setOrdDate(ordDate);

        List<DetailsVO> addList = new ArrayList<DetailsVO>();
        for (CartVO cartVO:cartlist) {
            DetailsVO detailsVO = new DetailsVO();
            detailsVO.setProductNo(cartVO.getProductNo());
            detailsVO.setQuantity(cartVO.getQuantity());
            detailsVO.setProdPrice(cartVO.getProdPrice());
            detailsVO.setProdPic(cartVO.getProdPic());
            detailsVO.setProdName(cartVO.getProdName());
            addList.add(detailsVO);
        }

        OrderVO order = orderService.insertWithOrdDetails(orderVO,addList);
        Set<DetailsVO> details = orderService.getOrderDetails_ByOrdno(order.getOrdNo());
        session.removeAttribute("shoppingcart");
        request.setAttribute("order",order);
        request.setAttribute("details",details);

        return "order/confirmation";
    }

    @GetMapping({"/order/getMemberOrders"})
    public String getMemberOrders(HttpServletRequest request, HttpSession session){
        Integer memNo = Integer.valueOf(session.getAttribute("memNo").toString());
        List<OrderVO> list = orderService.getMemberOrders(memNo);
        request.setAttribute("orders",list);
        return "order/getMemberOrders";
    }

    @PostMapping({"/order/getMemberOrders"})
    public String listDetails_ByOrdno(HttpServletRequest request, HttpSession session){
        Integer memNo = Integer.valueOf(session.getAttribute("memNo").toString());
        Integer ordNo = Integer.valueOf(request.getParameter("ordNo"));
        List<OrderVO> orders = orderService.getMemberOrders(memNo);
        Set<DetailsVO> details = orderService.getOrderDetails_ByOrdno(ordNo);
        request.setAttribute("orders",orders);
        request.setAttribute("details",details);
        return "order/getMemberOrders";
    }

}
