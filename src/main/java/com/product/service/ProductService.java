package com.product.service;

import com.product.vo.ProductVO;

import java.util.List;

public interface ProductService {
    String addPro(ProductVO product,String suffixName);
    void updatePro(ProductVO product);
    void deletePro(Integer productNo);
    ProductVO getOnePro(Integer productNo);
    List<ProductVO> getAll();
}
