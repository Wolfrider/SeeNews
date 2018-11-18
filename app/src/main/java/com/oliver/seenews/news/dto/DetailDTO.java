package com.oliver.seenews.news.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class DetailDTO {

    @JSONField(name = "detail")
    public DetailContentDTO detail;

}
