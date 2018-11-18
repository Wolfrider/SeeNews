package com.oliver.seenews.news.domain.page;

import java.util.LinkedList;
import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant.ReqType;
import com.oliver.seenews.common.http.HttpDispatcher;
import com.oliver.seenews.common.http.HttpDispatcherException;
import com.oliver.seenews.common.http.RequestParams;
import com.oliver.seenews.common.http.RequestParams.Builder;
import com.oliver.seenews.common.http.ResponseData;
import com.oliver.seenews.news.dto.FeedsDTO;
import com.oliver.seenews.news.dto.FeedsItemDTO;
import com.oliver.seenews.news.domain.title.TitleVO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class FeedsRepository {

    private static final String TAG = "FeedsRepository";

    private TitleVO mTitleVO;
    private List<FeedsCard> mFeedsCards;
    private int mPageNo;

    public FeedsRepository(@NonNull TitleVO titleVO) {
        mTitleVO = titleVO;
        mFeedsCards = new LinkedList<>();
    }

    public List<FeedsCard> get() {
        return mFeedsCards;
    }

    public Completable refresh() {
        mFeedsCards.clear();
        mPageNo = 1;
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.FEEDS)
            .setResClass(FeedsDTO.class)
            .setTitleId(mTitleVO.getId())
            .setPageNo(mPageNo);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMapCompletable(
            new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                mFeedsCards.addAll(convert((FeedsDTO)responseData.getData()));
                                emitter.onComplete();
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    public Single<List<FeedsCard>> load() {
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.FEEDS)
            .setResClass(FeedsDTO.class)
            .setTitleId(mTitleVO.getId())
            .setPageNo(++mPageNo);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMap(
            new Function<ResponseData, SingleSource<? extends List<FeedsCard>>>() {
                @Override
                public SingleSource<? extends List<FeedsCard>> apply(final ResponseData responseData) throws Exception {
                    return Single.create(new SingleOnSubscribe<List<FeedsCard>>() {
                        @Override
                        public void subscribe(SingleEmitter<List<FeedsCard>> emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                List<FeedsCard> feedsCards = convert((FeedsDTO)responseData.getData());
                                mFeedsCards.addAll(feedsCards);
                                emitter.onSuccess(feedsCards);
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    private List<FeedsCard> convert(FeedsDTO feedsDTO) {
        List<FeedsCard> feedsCards = new LinkedList<>();
        if (null != feedsDTO.mag_details) {
            for (FeedsItemDTO itemDTO: feedsDTO.mag_details) {
                FeedsCard card = new FeedsCard();
                card.setId(itemDTO.id);
                card.setImageUrl(itemDTO.icon);
                card.setTitle(itemDTO.title);
                card.setIntro(itemDTO.intro);
                feedsCards.add(card);
                //LogUtils.debug(TAG, "convert " + card);
            }
        }
        return feedsCards;
    }

}
