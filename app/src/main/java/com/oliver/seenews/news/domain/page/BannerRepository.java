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
import com.oliver.seenews.news.dto.BannerDTO;
import com.oliver.seenews.news.dto.BannerItemDTO;
import com.oliver.seenews.news.domain.title.TitleVO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class BannerRepository {

    private static final String TAG = "BannerRepository";

    private TitleVO mTitleVO;
    private BannerCard mBannerCard;

    public BannerRepository(@NonNull TitleVO titleVO) {
        mTitleVO = titleVO;
    }

    public Completable refresh() {
        LogUtils.debug(TAG, "refresh");
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.BANNER)
            .setResClass(BannerDTO.class)
            .setTitleId(mTitleVO.getId());
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMapCompletable(
            new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            LogUtils.debug(TAG, "flatMapCompletable");
                            if (responseData.isSuccessful()) {
                                mBannerCard = convert((BannerDTO)responseData.getData());
                                LogUtils.debug(TAG, "refresh complete");
                                emitter.onComplete();
                            } else {
                                LogUtils.debug(TAG, "refresh error, " + responseData);
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    public BannerCard get() {
        return mBannerCard;
    }

    private BannerCard convert(BannerDTO bannerDTO) {
        List<BannerCardItem> items = new LinkedList<>();
        if (null != bannerDTO.items) {
            for (BannerItemDTO itemDTO: bannerDTO.items) {
                BannerCardItem item = new BannerCardItem();
                item.setId(itemDTO.id);
                item.setImageUrl(itemDTO.icon);
                item.setTitle(itemDTO.title);
                items.add(item);
                //LogUtils.debug(TAG, "convert " + item);
            }
        }
        return new BannerCard(items);
    }
}
