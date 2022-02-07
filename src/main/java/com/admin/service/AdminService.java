package com.admin.service;

import com.admin.vo.AdminVO;

public interface AdminService {
    AdminVO login(String admAcc, String admPsw);
}
