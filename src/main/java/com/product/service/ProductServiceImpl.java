package com.product.service;

import com.product.dao.ProductDAO;
import com.product.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public String addPro(ProductVO productVO,String suffixName) {
        return productDAO.insert(productVO,suffixName);
    }

    @Override
    public void updatePro(ProductVO productVO) {
            productDAO.update(productVO);
    }

    @Override
    public void deletePro(Integer productNo) {
        productDAO.delete(productNo);
    }

    @Override
    public ProductVO getOnePro(Integer productNo) {
        return productDAO.findByPrimaryKey(productNo);
    }

    @Override
    public List<ProductVO> getAll() {
        return productDAO.getAll();
    }
}
