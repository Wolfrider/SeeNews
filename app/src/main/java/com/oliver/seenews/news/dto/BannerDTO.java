package com.oliver.seenews.news.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class BannerDTO implements Serializable {

    @JSONField(name = "mag_details")
    public List<BannerItemDTO> items;

    public BannerDTO() {}
}
