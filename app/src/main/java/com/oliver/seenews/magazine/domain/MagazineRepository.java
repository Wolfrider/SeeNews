package com.oliver.seenews.magazine.domain;

import java.util.LinkedList;
import java.util.List;

import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant.ReqType;
import com.oliver.seenews.common.http.HttpDispatcher;
import com.oliver.seenews.common.http.HttpDispatcherException;
import com.oliver.seenews.common.http.RequestParams;
import com.oliver.seenews.common.http.RequestParams.Builder;
import com.oliver.seenews.common.http.ResponseData;
import com.oliver.seenews.magazine.dto.MagazineDTO;
import com.oliver.seenews.magazine.dto.MagazineItemDTO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class MagazineRepository {

    private static final String TAG = "MagazineRepository";

    private int mPageNo;
    private List<MagazineItem> mMagazineItems;

    public MagazineRepository() {
        mPageNo = 1;
        mMagazineItems = new LinkedList<>();
    }

    public List<MagazineItem> get() {
        return mMagazineItems;
    }

    public Completable refresh() {
        mPageNo = 1;
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.MAGAZINE)
            .setResClass(MagazineDTO.class)
            .setPageNo(mPageNo);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMapCompletable(
            new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                List<MagazineItem> items = convert((MagazineDTO)responseData.getData());
                                mMagazineItems.addAll(items);
                                LogUtils.debug(TAG, "mMagazineItems size = " + mMagazineItems.size());
                                emitter.onComplete();
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    public Single<List<MagazineItem>> load() {
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.MAGAZINE)
            .setResClass(MagazineDTO.class)
            .setPageNo(++mPageNo);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMap(
            new Function<ResponseData, SingleSource<? extends List<MagazineItem>>>() {
                @Override
                public SingleSource<? extends List<MagazineItem>> apply(final ResponseData responseData) throws Exception {
                    return Single.create(new SingleOnSubscribe<List<MagazineItem>>() {
                        @Override
                        public void subscribe(SingleEmitter<List<MagazineItem>> emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                List<MagazineItem> items = convert((MagazineDTO)responseData.getData());
                                mMagazineItems.addAll(items);
                                emitter.onSuccess(items);
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    private List<MagazineItem> convert(MagazineDTO magazineDTO) {
        List<MagazineItem> items = new LinkedList<>();
        if (null != magazineDTO.magazines) {
            for (MagazineItemDTO itemDTO: magazineDTO.magazines) {
                MagazineItem item = new MagazineItem();
                item.setId(itemDTO.id);
                item.setCoverImgUrl(itemDTO.coverUrl);
                item.setTitle(itemDTO.title);
                item.setPublishTime(itemDTO.publishTime);
                item.setVol(itemDTO.vol);
                items.add(item);
            }
        }
        return items;
    }

}
