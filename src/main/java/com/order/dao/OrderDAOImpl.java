package com.order.dao;

import com.details.dao.DetailsDAO;
import com.details.dao.DetailsRowMapper;
import com.details.vo.DetailsVO;
import com.order.vo.OrderVO;
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
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class OrderDAOImpl implements OrderDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DetailsDAO detailsDAO;

    public OrderDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DetailsDAO detailsDAO) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.detailsDAO = detailsDAO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderVO insertWithOrdDetails(OrderVO orderVO, List<DetailsVO> list) {
        final String INSERT_STMT = "INSERT INTO ord (ord_no,mem_no,ord_status,receiver,rec_phone,rec_zip,rec_address,ord_total,ord_date) VALUES (ord_seq.NEXTVAL, :memNo, :ordStatus, :receiver, :recPhone, :recZip, :recAddress, :ordTotal, :ordDate)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("memNo",orderVO.getMemNo());
        parameters.addValue("ordStatus",orderVO.getOrdStatus());
        parameters.addValue("receiver",orderVO.getReceiver());
        parameters.addValue("recPhone",orderVO.getRecPhone());
        parameters.addValue("recZip",orderVO.getRecZip());
        parameters.addValue("recAddress",orderVO.getRecAddress());
        parameters.addValue("ordTotal",orderVO.getOrdTotal());
        parameters.addValue("ordDate",orderVO.getOrdDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_STMT,parameters,keyHolder,new String[]{"ord_no"});
        orderVO.setOrdNo(keyHolder.getKey().intValue());

        for (DetailsVO detailsVO:list) {
            detailsVO.setOrdNo(keyHolder.getKey().intValue());
            detailsDAO.insert(detailsVO);
        }

        return orderVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderVO> getAll() {
        final String GET_ALL_STMT = "SELECT ord_no, mem_no, ord_status,receiver, rec_phone, rec_zip,rec_address, ord_total,to_char(ord_date,'yyyy-mm-dd') ord_date FROM ord ORDER BY ord_no DESC";
        Map<String,Object> map = new HashMap<>();
        List<OrderVO> list = namedParameterJdbcTemplate.query(GET_ALL_STMT,map,new OrderRowMapper());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderVO> getMemberOrders(Integer memNo) {
        final String GET_MEM_ORDS_STMT  = "SELECT ord_no, mem_no, ord_status,receiver, rec_phone, rec_zip,rec_address, ord_total,to_char(ord_date,'yyyy-mm-dd') ord_date FROM ord WHERE mem_no = :memNo ORDER BY ord_no DESC";
        Map<String,Object> map = new HashMap<>();
        map.put("memNo",memNo);
        List<OrderVO> list = namedParameterJdbcTemplate.query(GET_MEM_ORDS_STMT,map,new OrderRowMapper());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<DetailsVO> getOrderDetails_ByOrdno(Integer ordNo) {
        final String GET_Details_ByOrdno_STMT = "SELECT ord_no,product_no,quantity,prod_price,prod_pic,prod_name  FROM ord_details WHERE ord_no = :ordNo ORDER BY product_no";
        Map<String,Object> map = new HashMap<>();
        map.put("ordNo",ordNo);
        Set<DetailsVO> set = namedParameterJdbcTemplate.query(GET_Details_ByOrdno_STMT,map,new DetailsRowMapper()).stream().collect(Collectors.toSet());
        return set;
    }
}
