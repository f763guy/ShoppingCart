package com.admin.service;

import com.admin.dao.AdminDAO;
import com.admin.vo.AdminVO;
import com.util.MD5Util;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public AdminVO login(String admAcc, String admPsw) {
        String admPswMd5 = MD5Util.MD5Encode(admPsw, "UTF-8");
        return adminDAO.login(admAcc,admPswMd5);
    }
}
