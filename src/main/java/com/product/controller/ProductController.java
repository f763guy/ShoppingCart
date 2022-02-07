package com.product.controller;

import com.common.Constants;
import com.common.TokenForm;
import com.product.service.ProductService;
import com.product.vo.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/admin/listAll"})
    public String listAllProduct(HttpServletRequest request){
        List<ProductVO> products = productService.getAll();
        request.setAttribute("products",products);
        return "admin/listAll";
    }

    @GetMapping({"/admin/add"})
    @TokenForm(create=true)
    public String addPage(HttpServletRequest request){
        return "admin/add";
    }

    @PostMapping({"/admin/add"})
    @TokenForm(remove=true)
    public String add(HttpServletRequest request,@RequestParam("prod_pic") MultipartFile file){

        String prodName = request.getParameter("prodName");
        if (!StringUtils.hasText(prodName)) {
            request.setAttribute("errorMsg", "商品名稱:請勿空白");
            return "admin/add";
        }

        String prodIntroduce = request.getParameter("prodIntroduce");
        if (!StringUtils.hasText(prodIntroduce)) {
            request.setAttribute("errorMsg", "商品介紹:請勿空白");
            return "admin/add";
        }

        Integer prodPrice = null;
        try {
            prodPrice = new Integer(request.getParameter("prodPrice").trim());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg","商品價格:請填數字");
            return "admin/add";
        }

        if (file.isEmpty()) {
            request.setAttribute("errorMsg", "商品圖片:請上傳照片");
            return "admin/add";
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        ProductVO productVO = new ProductVO();
        productVO.setProdName(prodName);
        productVO.setProdIntroduce(prodIntroduce);
        productVO.setProdPrice(prodPrice);
        productVO.setProdPic("/DB_photos1/");

        String uploadFileName = productService.addPro(productVO,suffixName);

        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
        File destFile = new File(Constants.FILE_UPLOAD_DIC + uploadFileName);

        try {
            if(!fileDirectory.exists()) {
                if(!fileDirectory.mkdir()) {
                    throw new IOException("資料夾建立失敗,路徑為:" + fileDirectory);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/listAll";
    }

    @PostMapping({"/admin/update_one"})
    @TokenForm(create=true)
    public String updatePage(HttpServletRequest request){
        Integer productNo = Integer.valueOf(request.getParameter("productNo"));
        ProductVO productVO = productService.getOnePro(productNo);
        request.setAttribute("productVO",productVO);
        return "admin/update_one";
    }

    @PostMapping({"/admin/update"})
    @TokenForm(remove=true)
    public String update(HttpServletRequest request,@RequestParam("prod_pic") MultipartFile file){

        ProductVO productVO = new ProductVO();

        Integer productNo = null;
        try {
            productNo = new Integer(request.getParameter("productNo").trim());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg","商品編號遺失:請重新操作");
            return "admin/update_one";
        }

        String prodName = request.getParameter("prodName");
        if (!StringUtils.hasText(prodName)) {
            request.setAttribute("errorMsg", "商品名稱:請勿空白");
            return "admin/update_one";
        }

        String prodIntroduce = request.getParameter("prodIntroduce");
        if (!StringUtils.hasText(prodIntroduce)) {
            request.setAttribute("errorMsg", "商品介紹:請勿空白");
            return "admin/update_one";
        }

        Integer prodPrice = null;
        try {
            prodPrice = new Integer(request.getParameter("prodPrice").trim());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg","商品價格:請填數字");
            return "admin/update_one";
        }

        if (file.isEmpty()) {
            ProductVO serviceOnePro = productService.getOnePro(productNo);
            productVO.setProdPic(serviceOnePro.getProdPic());
        } else {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
            File destFile = new File(Constants.FILE_UPLOAD_DIC + productNo + suffixName);

            try {
                if(!fileDirectory.exists()) {
                    if(!fileDirectory.mkdir()) {
                        throw new IOException("資料夾建立失敗,路徑為:" + fileDirectory);
                    }
                }
                file.transferTo(destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            productVO.setProdPic("/DB_photos1/" + productNo + suffixName);
        }

        productVO.setProductNo(productNo);
        productVO.setProdName(prodName);
        productVO.setProdIntroduce(prodIntroduce);
        productVO.setProdPrice(prodPrice);

        productService.updatePro(productVO);

        return "redirect:/admin/listAll";
    }


    @PostMapping({"/admin/delete"})
    public String delete(HttpServletRequest request){
        Integer productNo = Integer.valueOf(request.getParameter("productNo"));
        productService.deletePro(productNo);
        return "redirect:/admin/listAll";
    }




}
