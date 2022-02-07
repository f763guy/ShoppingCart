package com.admin.dao;

import com.admin.vo.AdminVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements RowMapper<AdminVO> {
    @Override
    public AdminVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminVO adminVO = new AdminVO();
        adminVO.setAdmNo(rs.getInt("ADM_NO"));
        adminVO.setAdmAcc(rs.getString("ADM_ACC"));
        adminVO.setAdmPsw(rs.getString("ADM_PSW"));
        adminVO.setAdmName(rs.getString("ADM_NAME"));
        return adminVO;
    }
}
