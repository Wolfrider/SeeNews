package com.oliver.seenews.magazine.domain;

import java.util.LinkedList;
import java.util.List;

import com.oliver.seenews.common.Constant.ReqType;
import com.oliver.seenews.common.http.HttpDispatcher;
import com.oliver.seenews.common.http.HttpDispatcherException;
import com.oliver.seenews.common.http.RequestParams;
import com.oliver.seenews.common.http.RequestParams.Builder;
import com.oliver.seenews.common.http.ResponseData;
import com.oliver.seenews.magazine.dto.MagazineCommentItemDTO;
import com.oliver.seenews.magazine.dto.MagazineCommentsDTO;
import com.oliver.seenews.magazine.dto.MagazineDetailContentDTO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class MagazineDetailRepository {

    private int mId;
    private MagazineDetailItem mDetailItem;
    private int mPageNo;

    public MagazineDetailRepository(int id) {
        mId = id;
        mDetailItem = new MagazineDetailItem();
        mPageNo = 1;
    }

    public MagazineDetailItem getDetail() {
        return mDetailItem;
    }

    public Completable refresh() {
        return getContent();
    }

    private Completable getContent() {
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.MAGAZINE_DETAIL_CONTENT)
            .setResClass(MagazineDetailContentDTO.class)
            .setMagazineId(mId);
        return HttpDispatcher.getInstance().sendRequest(builder.build())
            .flatMapCompletable(new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                convert((MagazineDetailContentDTO)responseData.getData());
                                emitter.onComplete();
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    private void convert(MagazineDetailContentDTO contentDTO) {
        mDetailItem.setId(contentDTO.magazine.id);
        mDetailItem.setCoverImageUrl(contentDTO.magazine.coverImageUrl);
        mDetailItem.setTitle(contentDTO.magazine.title);
        mDetailItem.setPublishDate(contentDTO.magazine.pubTime);
        mDetailItem.setSize(contentDTO.magazine.packageDTO.size);
        mDetailItem.setScorePoint(contentDTO.magazine.point);
        mDetailItem.setDmmUrl(contentDTO.magazine.packageDTO.dmmUrl);
        mDetailItem.setDesc(contentDTO.magazine.desc);
    }

}
