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
import com.oliver.seenews.magazine.dto.MagazineCommentItemDTO;
import com.oliver.seenews.magazine.dto.MagazineCommentsDTO;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class MagazineCommentsRepository {

    private static final String TAG = "MagazineCommentsRepository";

    private int mId;
    private int mPageNo;
    private int mCommentsCount;
    private List<MagazineCommentItem> mComments;

    public MagazineCommentsRepository(int id) {
        mId = id;
        mPageNo = 1;
        mComments = new LinkedList<>();
    }

    public int getCount() {
        return mCommentsCount;
    }

    public Single<List<MagazineCommentItem>> loadComments() {
        RequestParams.Builder builder = new Builder();
        builder.setType(ReqType.MAGAZINE_DETAIL_COMMENTS)
            .setResClass(MagazineCommentsDTO.class)
            .setDetailId(mId)
            .setPageNo(mPageNo++);
        return HttpDispatcher.getInstance().sendRequest(builder.build())
            .flatMap(new Function<ResponseData, SingleSource<? extends List<MagazineCommentItem>>>() {
                @Override
                public SingleSource<? extends List<MagazineCommentItem>> apply(final ResponseData responseData) throws Exception {
                    return Single.create(new SingleOnSubscribe<List<MagazineCommentItem>>() {
                        @Override
                        public void subscribe(SingleEmitter<List<MagazineCommentItem>> emitter) throws Exception {
                            if (responseData.isSuccessful()) {
                                MagazineCommentsDTO commentsDTO = (MagazineCommentsDTO)responseData.getData();
                                mCommentsCount = commentsDTO.count;
                                List<MagazineCommentItem> comments = convert(commentsDTO);
                                mComments.addAll(comments);
                                emitter.onSuccess(comments);
                            } else {
                                emitter.onError(new HttpDispatcherException(responseData));
                            }
                        }
                    });
                }
            });
    }

    private List<MagazineCommentItem> convert(MagazineCommentsDTO commentsDTO) {
        List<MagazineCommentItem> comments = new LinkedList<>();
        for (MagazineCommentItemDTO itemDTO: commentsDTO.comments) {
            MagazineCommentItem item = new MagazineCommentItem();
            item.setAvatarUrl(itemDTO.userDTO.avatarUrl);
            item.setUserName(itemDTO.userDTO.userName);
            item.setPublishTime(itemDTO.publishTime);
            item.setDesc(itemDTO.desc);
            item.setScorePoint(itemDTO.point);
            comments.add(item);
            LogUtils.debug(TAG, item.toString());
        }
        return comments;
    }
}
