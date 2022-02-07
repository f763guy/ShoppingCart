package com.order.dao;

import com.order.vo.OrderVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<OrderVO> {
    @Override
    public OrderVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrdNo(rs.getInt("ORD_NO"));
        orderVO.setMemNo(rs.getInt("MEM_NO"));
        orderVO.setOrdStatus(rs.getInt("ORD_STATUS"));
        orderVO.setReceiver(rs.getString("RECEIVER"));
        orderVO.setRecPhone(rs.getString("REC_PHONE"));
        orderVO.setRecZip(rs.getString("REC_ZIP"));
        orderVO.setRecAddress(rs.getString("REC_ADDRESS"));
        orderVO.setOrdTotal(rs.getInt("ORD_TOTAL"));
        orderVO.setOrdDate(rs.getDate("ORD_DATE"));
        return orderVO;
    }
}
