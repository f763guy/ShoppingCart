package com.admin.dao;

import com.admin.vo.AdminVO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdminDAOImpl implements AdminDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdminDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminVO login(String admAcc, String admPsw) {
        final String GET_LOGIN_STMT = "SELECT adm_no, adm_acc, adm_psw, adm_name FROM admin WHERE adm_acc = :admAcc AND adm_psw = :admPsw";
        Map<String,Object> map = new HashMap<>();
        map.put("admAcc",admAcc);
        map.put("admPsw",admPsw);
        List<AdminVO> list = namedParameterJdbcTemplate.query(GET_LOGIN_STMT,map,new AdminRowMapper());
        if( list.size() > 0 ){
            AdminVO adminVO = list.get(0);
            return adminVO;
        } else {
            return null;
        }
    }
}
