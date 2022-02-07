package com.member.service;


import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDAO memberDAO;

    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public MemberVO getOneMember(Integer memNo) {
        return memberDAO.findByPrimaryKey(memNo);
    }
}
