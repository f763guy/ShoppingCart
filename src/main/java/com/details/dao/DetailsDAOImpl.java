package com.details.dao;

import com.details.vo.DetailsVO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DetailsDAOImpl implements DetailsDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DetailsDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void insert(DetailsVO detailsVO) {
        final String INSERT_STMT = "INSERT INTO ord_details (ord_no,product_no,quantity,prod_price,prod_pic,prod_name) VALUES (:ordNo,:productNo,:quantity,:prodPrice,:prodPic,:prodName)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ordNo",detailsVO.getOrdNo());
        parameters.addValue("productNo",detailsVO.getProductNo());
        parameters.addValue("quantity",detailsVO.getQuantity());
        parameters.addValue("prodPrice",detailsVO.getProdPrice());
        parameters.addValue("prodPic",detailsVO.getProdPic());
        parameters.addValue("prodName",detailsVO.getProdName());
        namedParameterJdbcTemplate.update(INSERT_STMT,parameters);
    }
}
