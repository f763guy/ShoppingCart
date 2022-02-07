package com.shopping.controller;
import com.common.TokenForm;
import com.member.service.MemberService;
import com.member.vo.MemberVO;
import com.product.service.ProductService;
import com.product.vo.ProductVO;
import com.shopping.vo.CartVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ShoppingController {

    private ProductService productService;
    private MemberService  memberService;

    public ShoppingController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping({"/product/shop"})
    public String eShopPage (HttpServletRequest request, HttpSession session){
        List<ProductVO> products = productService.getAll();
        request.setAttribute("products",products);
        MemberVO memberVO =memberService.getOneMember(1);
        session.setAttribute("memNo", memberVO.getMemNo());
        session.setAttribute("memName",memberVO.getMemName());
        List<CartVO> buylist = (List<CartVO>) session.getAttribute("shoppingcart");
        request.setAttribute("shoppingcart", buylist);
        return "product/shop";
    }

    @PostMapping({"/product/shop"})
    public String addCart (HttpServletRequest request, HttpSession session){
        List<ProductVO> products = productService.getAll();
        request.setAttribute("products",products);
        MemberVO memberVO =memberService.getOneMember(1);
        session.setAttribute("memNo", memberVO.getMemNo());
        session.setAttribute("memName",memberVO.getMemName());

        Integer memNo = Integer.valueOf(session.getAttribute("memNo").toString());
        Integer productNo = Integer.valueOf(request.getParameter("productNo"));
        String prodName = request.getParameter("prodName");
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));
        Integer prodPrice = Integer.valueOf(request.getParameter("prodPrice"));
        String prodPic =request.getParameter("prodPic");

        CartVO cartVO = new CartVO();
        cartVO.setMemNo(memNo);
        cartVO.setProductNo(productNo);
        cartVO.setProdName(prodName);
        cartVO.setQuantity(quantity);
        cartVO.setProdPrice(prodPrice);
        cartVO.setProdPic(prodPic);

        List<CartVO> buylist = (List<CartVO>) session.getAttribute("shoppingcart");

        if (buylist == null) {
            buylist = new ArrayList<CartVO>();
            buylist.add(cartVO);
        } else {
            if(buylist.contains(cartVO)){
                CartVO innerCarVO = buylist.get(buylist.indexOf(cartVO));
                innerCarVO.setQuantity(innerCarVO.getQuantity() + cartVO.getQuantity());
                System.out.println(innerCarVO.getQuantity());
            }else{
                buylist.add(cartVO);
            }
        }

        session.setAttribute("shoppingcart", buylist);
        request.setAttribute("shoppingcart", buylist);

        return "product/shop";
    }


    @GetMapping({"/product/cart"})
    public String cartPage(HttpServletRequest request, HttpSession session){
        List<CartVO> buylist = (List<CartVO>) session.getAttribute("shoppingcart");
        request.setAttribute("shoppingcart", buylist);
        return "product/cart";
    }

    @PostMapping({"/product/cart"})
    public String delete(HttpServletRequest request, HttpSession session) {

        String del = request.getParameter("del");
        int d = Integer.parseInt(del);
        List<CartVO> buylist = (List<CartVO>) session.getAttribute("shoppingcart");
        buylist.remove(d);
        session.setAttribute("shoppingcart", buylist);
        request.setAttribute("shoppingcart", buylist);

        return "product/cart";
    }

    @PostMapping({"/product/checkout"})
    @TokenForm(create=true)
    public String checkOutPage(HttpServletRequest request, HttpSession session){

        List<CartVO> buylist = (List<CartVO>) session.getAttribute("shoppingcart");

        int total = 0;
        for(CartVO cartVO : buylist){
            int quantity = Integer.parseInt(request.getParameter("quantity"+cartVO.getProductNo()));
            cartVO.setQuantity(quantity);
            int price = cartVO.getProdPrice();
            total += (price * quantity);
        }

        request.setAttribute("amount", String.valueOf(total));
        request.setAttribute("shoppingcart", buylist);

        return "product/checkout";
    }


}
