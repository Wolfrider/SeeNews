package com.oliver.seenews.magazine.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineCommentsDTO {

    @JSONField(name = "comments")
    public List<MagazineCommentItemDTO> comments;

    @JSONField(name = "result_total")
    public int count;
}
