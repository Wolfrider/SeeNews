package com.oliver.seenews.magazine.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class MagazinePackageDTO {

    @JSONField(name = "addr")
    public String dmmUrl;

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "name")
    public String name;

    @JSONField(name = "size")
    public String size;
}
