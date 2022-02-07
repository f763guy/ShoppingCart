package com.member.dao;

import com.member.vo.MemberVO;

public interface MemberDAO {
    MemberVO findByPrimaryKey(Integer memNo);
}
