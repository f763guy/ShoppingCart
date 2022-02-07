package com.product.dao;

import com.product.vo.ProductVO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAOImpl implements ProductDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String insert(ProductVO productVO,String suffixName) {
        final String INSERT_STMT = "INSERT INTO product (product_no,prod_name,prod_price,prod_introduce) VALUES (product_seq.NEXTVAL,:prodName,:prodPrice,:prodIntroduce)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("prodName",productVO.getProdName());
        parameters.addValue("prodPrice",productVO.getProdPrice());
        parameters.addValue("prodIntroduce",productVO.getProdIntroduce());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_STMT,parameters,keyHolder,new String[]{"product_no"});

        String prodPic = productVO.getProdPic();
        String primaryKey = keyHolder.getKey().toString();
        String uploadFileName = primaryKey+suffixName;
        String uploadProdPic = prodPic+uploadFileName;

        final String UPDATE_PROD_PIC  = "UPDATE product set prod_pic=:prodPic WHERE product_no = :productNo";
        Map<String,Object> map = new HashMap<>();
        map.put("prodPic",uploadProdPic);
        map.put("productNo",keyHolder.getKey().intValue());
        namedParameterJdbcTemplate.update(UPDATE_PROD_PIC,map);

        return uploadFileName;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(ProductVO productVO) {
        final String UPDATE = "UPDATE product set prod_name=:prodName, prod_price=:prodPrice, prod_introduce=:prodIntroduce, prod_pic=:prodPic WHERE product_no = :productNo";
        Map<String,Object> map = new HashMap<>();
        map.put("prodName",productVO.getProdName());
        map.put("prodPrice",productVO.getProdPrice());
        map.put("prodIntroduce",productVO.getProdIntroduce());
        map.put("prodPic",productVO.getProdPic());
        map.put("productNo",productVO.getProductNo());
        System.out.println(productVO.getProdName());
        System.out.println(productVO.getProdPic());
        System.out.println(productVO.getProdIntroduce());
        System.out.println(productVO.getProdPic());
        System.out.println(productVO.getProductNo());
        namedParameterJdbcTemplate.update(UPDATE,map);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer productNo) {
        final String DELETE = "DELETE FROM product where product_no = :productNo";
        Map<String,Object> map = new HashMap<>();
        map.put("productNo",productNo);
        namedParameterJdbcTemplate.update(DELETE,map);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVO findByPrimaryKey(Integer productNo) {
        final String GET_ONE_STMT = "SELECT product_no,prod_name,prod_price,prod_introduce,prod_pic FROM product WHERE product_no = :productNo";
        Map<String,Object> map = new HashMap<>();
        map.put("productNo",productNo);
        List<ProductVO> list =  namedParameterJdbcTemplate.query(GET_ONE_STMT,map,new ProductRowMapper());
        if( list.size() > 0 ){
            ProductVO productVO = list.get(0);
            return productVO;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVO> getAll() {
        final String GET_ALL_STMT = "SELECT product_no,prod_name,prod_price,prod_introduce,prod_pic FROM product ORDER BY product_no";
        Map<String,Object> map = new HashMap<>();
        List<ProductVO> list = namedParameterJdbcTemplate.query(GET_ALL_STMT,map,new ProductRowMapper());
        return list;
    }
}
