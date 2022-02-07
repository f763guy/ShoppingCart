package com.member.dao;

import com.member.vo.MemberVO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDAOImpl implements MemberDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberVO findByPrimaryKey(Integer memNo) {
        final String GET_ONE_STMT = "SELECT mem_no,mem_acc,mem_psw,mem_name,mem_id,mem_phone,mem_zip,mem_address,card_no,card_yy,card_mm,card_id,to_char(create_time,'yyyy-mm-dd') create_time,mem_email,mem_status FROM member where mem_no = :memNo";
        Map<String,Object> map = new HashMap<>();
        map.put("memNo",memNo);
        List<MemberVO> list = namedParameterJdbcTemplate.query(GET_ONE_STMT,map,new MemberRowMapper());
        if( list.size() > 0 ){
            MemberVO memberVO = list.get(0);
            return memberVO;
        } else {
            return null;
        }
    }
}
