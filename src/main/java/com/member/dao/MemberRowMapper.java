package com.member.dao;

import com.member.vo.MemberVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<MemberVO> {
    @Override
    public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberVO memberVO = new MemberVO();
        memberVO.setMemNo(rs.getInt("MEM_NO"));
        memberVO.setMemAcc(rs.getString("MEM_ACC"));
        memberVO.setMemPsw(rs.getString("MEM_PSW"));
        memberVO.setMemName(rs.getString("MEM_NAME"));
        memberVO.setMemId(rs.getString("MEM_ID"));
        memberVO.setMemPhone(rs.getString("MEM_PHONE"));
        memberVO.setMemZip(rs.getString("MEM_ZIP"));
        memberVO.setMemAddress(rs.getString("MEM_ADDRESS"));
        memberVO.setCardNo(rs.getString("CARD_NO"));
        memberVO.setCardYy(rs.getString("CARD_YY"));
        memberVO.setCardMm(rs.getString("CARD_MM"));
        memberVO.setCardId(rs.getString("CARD_ID"));
        memberVO.setCreateTime(rs.getDate("CREATE_TIME"));
        memberVO.setMemEmail(rs.getString("MEM_EMAIL"));
        memberVO.setMemStatus(rs.getString("MEM_STATUS"));
        return memberVO;
    }
}
