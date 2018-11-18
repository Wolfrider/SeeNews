package com.oliver.seenews.magazine.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class Magazines {

    private MagazineRepository mMagazineRepository;

    public Magazines() {
        mMagazineRepository = new MagazineRepository();
    }

    public List<MagazineItem> get() {
        return mMagazineRepository.get();
    }

    public Completable refresh() {
        return mMagazineRepository.refresh();
    }

    public Single<List<MagazineItem>> load() {
        return mMagazineRepository.load();
    }

}
