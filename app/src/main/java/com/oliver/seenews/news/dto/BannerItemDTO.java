package com.oliver.seenews.news.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class BannerItemDTO implements Serializable {

    @JSONField(name = "adtype")
    public int adType;

    public String author;

    public int comment_count;

    public String icon;

    public int id;

    public String intro;

    public String link;

    public String pub_time;

    public String title;

    public BannerItemDTO() {}
}
