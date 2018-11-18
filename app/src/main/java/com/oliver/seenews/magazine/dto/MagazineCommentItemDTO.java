package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineCommentItemDTO {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "createtime")
    public String publishTime;

    @JSONField(name = "desc")
    public String desc;

    @JSONField(name = "user")
    public UserDTO userDTO;

    @JSONField(name = "mark")
    public int point;
}
