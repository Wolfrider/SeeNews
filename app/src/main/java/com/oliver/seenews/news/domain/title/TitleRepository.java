package com.oliver.seenews.news.domain.title;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant.ReqType;
import com.oliver.seenews.common.http.HttpDispatcher;
import com.oliver.seenews.common.http.RequestParams;
import com.oliver.seenews.common.http.RequestParams.Builder;
import com.oliver.seenews.common.http.ResponseData;
import com.oliver.seenews.news.dto.TitleDTO;
import com.oliver.seenews.news.dto.TitleItemDTO;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class TitleRepository {

    private static final String TAG = "TitleRepository";

    private List<TitleVO> mTitles;

    public TitleRepository() {
        mTitles = new LinkedList<>();
    }

    public List<TitleVO> get() {
        return mTitles;
    }

    public Completable refresh() {
        LogUtils.debug(TAG, "refresh");
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.TITLE).setResClass(TitleDTO.class);
        return HttpDispatcher.getInstance().sendRequest(builder.build()).flatMapCompletable(
            new Function<ResponseData, CompletableSource>() {
                @Override
                public CompletableSource apply(final ResponseData responseData) throws Exception {
                    return Completable.create(new CompletableOnSubscribe() {
                        @Override
                        public void subscribe(CompletableEmitter emitter) throws Exception {
                            mTitles.clear();
                            if (responseData.isSuccessful()) {
                                List<TitleVO> titleList = convert((TitleDTO)responseData.getData());
                                filter(titleList);
                                mTitles.addAll(titleList);
                                LogUtils.debug(TAG, "refresh complete");
                                emitter.onComplete();
                            } else {
                                LogUtils.debug(TAG, "refresh error, " + responseData);
                                emitter.onComplete();
                            }
                        }
                    });
                }
            });
    }

    public void save() {

    }

    private List<TitleVO> convert(TitleDTO titleDTO) {
        List<TitleVO> titleList = new LinkedList<>();
        if (null != titleDTO.detail_cats) {
            for (TitleItemDTO itemDTO : titleDTO.detail_cats) {
                TitleVO titleVO = new TitleVO();
                titleVO.setId(itemDTO.id);
                titleVO.setName(itemDTO.name);
                titleList.add(titleVO);
            }
        }
        return titleList;
    }

    private void filter(List<TitleVO> titleList) {
        for (Iterator<TitleVO> iter = titleList.iterator(); iter.hasNext();) {
            TitleVO titleVO = iter.next();
            if (titleVO.getName().equals("时政")) {
                iter.remove();
            }
        }
    }

}
