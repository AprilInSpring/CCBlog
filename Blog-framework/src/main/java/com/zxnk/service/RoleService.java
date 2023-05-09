package com.zxnk.service;

import java.util.List;

public interface RoleService {
    List<String> getRoleKeysByUserId(Long id);
}
