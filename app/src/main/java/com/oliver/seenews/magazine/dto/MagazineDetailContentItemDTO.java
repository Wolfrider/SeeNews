package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazineDetailContentItemDTO {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "cover")
    public String coverImageUrl;

    @JSONField(name = "desc")
    public String desc;

    @JSONField(name = "mag_package")
    public MagazinePackageDTO packageDTO;

    @JSONField(name = "pub_time")
    public String pubTime;

    @JSONField(name = "thumb")
    public String thumbImageUrl;

    @JSONField(name = "title")
    public String title;

    @JSONField(name = "point")
    public int point;

    @JSONField(name = "vol")
    public int vol;

    @JSONField(name = "vol_year")
    public int volYear;
}
