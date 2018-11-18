package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineDetailContentDTO {

    @JSONField(name = "mag")
    public MagazineDetailContentItemDTO magazine;
}
