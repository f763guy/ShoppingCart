package com.product.dao;

import com.product.vo.ProductVO;

import java.util.List;

public interface ProductDAO {
    String insert(ProductVO productVO,String suffixName);
    void update(ProductVO productVO);
    void delete(Integer productNo);
    ProductVO findByPrimaryKey(Integer productNo);
    List<ProductVO> getAll();
}
