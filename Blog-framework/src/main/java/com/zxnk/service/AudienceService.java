package com.zxnk.service;

import com.zxnk.entity.Audience;

import java.util.List;

//读者接口
public interface AudienceService {

    List<Audience> findAll();

    void updateAudience(List<Audience> audiences);
}
