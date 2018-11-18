package com.oliver.seenews.news.domain.detail;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant.ReqType;
import com.oliver.seenews.common.http.HttpDispatcher;
import com.oliver.seenews.common.http.HttpDispatcherException;
import com.oliver.seenews.common.http.RequestParams;
import com.oliver.seenews.common.http.RequestParams.Builder;
import com.oliver.seenews.common.http.ResponseData;
import com.oliver.seenews.news.dto.DetailDTO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class DetailRepository {

    private static final String TAG = "DetailRepository";

    private int mId;
    private Detail mDetail;

    public DetailRepository(int id) {
        mId = id;
    }

    public Completable refresh() {
        LogUtils.debug(TAG, "refresh, mId = " + mId);
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.DETAIL)
            .setResClass(DetailDTO.class)
            .setDetailId(mId);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMapCompletable(
            new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                mDetail = convert((DetailDTO)responseData.getData());
                                emitter.onComplete();
                            } else {
                                LogUtils.debug(TAG, "sendRequest failed.");
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    public Detail getDetail() {
        return mDetail;
    }

    private Detail convert(@NonNull DetailDTO detailDTO) {
        Detail detail = new Detail();
        if (null != detailDTO.detail) {
            detail.setId(detailDTO.detail.id);
            detail.setTitle(detailDTO.detail.title);
            detail.setAuthor(detailDTO.detail.author);
            detail.setContent(detailDTO.detail.desc);
            detail.setPublishTime(detailDTO.detail.pubTime);
        }
        //LogUtils.debug(TAG, "convert " + detail);
        return detail;
    }

}
