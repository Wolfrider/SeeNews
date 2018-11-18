package com.oliver.seenews.news.domain.detail;

import io.reactivex.Completable;

public class DetailPage {

    private int mId;
    private DetailRepository mDetailRepository;

    public DetailPage(int id) {
        mId = id;
        mDetailRepository = new DetailRepository(mId);
    }

    public Completable refresh() {
        return mDetailRepository.refresh();
    }

    public Detail getDetail() {
        return mDetailRepository.getDetail();
    }

    public String getDetailContent() {
        Detail detail = getDetail();
        String content = detail.getContent();
        content = changeImg(new StringBuilder(content));
        return content;
    }

    private String changeImg(StringBuilder content) {
        String imgTag = "<img ";
        String imgStyle = "style=\"width=100%;height=auto;\" ";
        for (int index = content.indexOf(imgTag); index > -1; index = content.indexOf(imgTag, index + 1)) {
            content = content.insert(index, imgStyle);
        }
        return content.toString();
    }
}
