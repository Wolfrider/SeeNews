package com.oliver.seenews.news.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class DetailContentDTO {

    @JSONField(name = "adtype")
    public int adType;

    @JSONField(name = "author")
    public String author;

    @JSONField(name = "comment_count")
    public int commentCount;

    @JSONField(name = "desc")
    public String desc;

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "intro")
    public String intro;

    @JSONField(name = "link")
    public String link;

    @JSONField(name = "pub_time")
    public String pubTime;

    @JSONField(name = "sharelink")
    public String sharelink;

    @JSONField(name = "thumb")
    public String thumbUrl;

    @JSONField(name = "title")
    public String title;

}
