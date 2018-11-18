package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineItemDTO {

    @JSONField(name = "comment_count")
    public int commentCount;

    @JSONField(name = "cover")
    public String coverUrl;

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "is_buy")
    public boolean isBuy;

    @JSONField(name = "is_free")
    public boolean isFree;

    @JSONField(name = "is_recommend")
    public boolean isRecommend;

    @JSONField(name = "mag_package")
    public MagazinePackageDTO packageDTO;

    @JSONField(name = "point")
    public int point;

    @JSONField(name = "point_number")
    public int pointNumber;

    @JSONField(name = "price")
    public float price;

    @JSONField(name = "pub_time")
    public String publishTime;

    @JSONField(name = "thumb")
    public String thumbUrl;

    @JSONField(name = "title")
    public String title;

    @JSONField(name = "update_time")
    public String updateTime;

    @JSONField(name = "vol")
    public int vol;

    @JSONField(name = "vol_year")
    public int volYear;

}
