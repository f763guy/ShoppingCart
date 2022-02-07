package com.admin.dao;

import com.admin.vo.AdminVO;

public interface AdminDAO {
    AdminVO login(String admAcc, String admPsw);
}
