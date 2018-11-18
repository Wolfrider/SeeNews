package com.oliver.seenews.magazine.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MagazineDetail {

    private MagazineDetailRepository mRepository;

    public MagazineDetail(int id) {
        mRepository = new MagazineDetailRepository(id);
    }

    public Completable refresh() {
        return mRepository.refresh();
    }

    public MagazineDetailItem getDetail() {
        return mRepository.getDetail();
    }
}
