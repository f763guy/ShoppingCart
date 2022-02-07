package com.details.dao;

import com.details.vo.DetailsVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsRowMapper implements RowMapper<DetailsVO> {
    @Override
    public DetailsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DetailsVO detailsVO = new DetailsVO();
        detailsVO.setOrdNo(rs.getInt("ORD_NO"));
        detailsVO.setProductNo(rs.getInt("PRODUCT_NO"));
        detailsVO.setQuantity(rs.getInt("QUANTITY"));
        detailsVO.setProdPrice(rs.getInt("PROD_PRICE"));
        detailsVO.setProdPic(rs.getString("PROD_PIC"));
        detailsVO.setProdName(rs.getString("PROD_NAME"));
        return detailsVO;
    }
}
