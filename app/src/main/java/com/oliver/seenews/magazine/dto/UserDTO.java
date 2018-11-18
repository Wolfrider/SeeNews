package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class UserDTO {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "username")
    public String userName;

    @JSONField(name = "image")
    public String avatarUrl;
}
