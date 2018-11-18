package com.oliver.seenews.magazine.domain;

import java.util.List;

import io.reactivex.Single;

public class MagazineComments {

    private MagazineCommentsRepository mRepository;

    public MagazineComments(int id) {
        mRepository = new MagazineCommentsRepository(id);
    }

    public int getCount() {
        return mRepository.getCount();
    }

    public Single<List<MagazineCommentItem>> loadComments() {
        return mRepository.loadComments();
    }
}
