package com.product.dao;

import com.product.vo.ProductVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductVO> {
    @Override
    public ProductVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductVO productVO = new ProductVO();
        productVO.setProductNo(rs.getInt("PRODUCT_NO"));
        productVO.setProdName(rs.getString("PROD_NAME"));
        productVO.setProdPrice(rs.getInt("PROD_PRICE"));
        productVO.setProdIntroduce(rs.getString("PROD_INTRODUCE"));
        productVO.setProdPic(rs.getString("PROD_PIC"));
        return productVO;
    }
}
