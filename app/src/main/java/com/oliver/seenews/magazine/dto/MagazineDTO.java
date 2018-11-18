package com.oliver.seenews.magazine.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineDTO {

    @JSONField(name = "mags")
    public List<MagazineItemDTO> magazines;
}
