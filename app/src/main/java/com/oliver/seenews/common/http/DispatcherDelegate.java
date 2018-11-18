package com.oliver.seenews.common.http;

import java.util.HashMap;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.oliver.seenews.base.Constant.Http.Method;
import com.oliver.seenews.base.http.HttpRequest;
import com.oliver.seenews.base.http.HttpRequest.Builder;
import com.oliver.seenews.common.Constant.ReqType;

class DispatcherDelegate {

    private static final String KEY_CODE = "code";
    private static final String KEY_METHOD = "method";
    private static final String KEY_FORMAT = "format";
    private static final String KEY_FIELDS = "fields";
    private static final String KEY_PARENT_ID = "parent_id";

    private static final String CODE_VALUE = "visand001";
    private static final String FORMAT_JSON = "json";

    private static final String TITLE_FIELDS = "id,name";
    private static final String TITLE_METHOD = "mag.detail.cats.get";

    private static final String KEY_IS_TOP = "istop";
    private static final String KEY_PAGE_NO = "page_no";
    private static final String KEY_PAGE_SIZE = "page_size";
    private static final String KEY_CAT_ID = "cat_id";
    private static final String KEY_DETAIL_ID = "detail_id";
    private static final String KEY_TYPE = "type";
    private static final String PHONE = "phone";

    private static final String KEY_MAG_ID = "mag_id";

    private static final String BANNER_FIELDS = "id,title,icon,author,pub_time,comment_count,intro,adtype,link";
    private static final String BANNER_METHOD = "mag.hot.details.get";

    private static final String DETAIL_FIELDS = "id,title,thumb,desc,createtime,author,article_id,menu_id,pub_time,comment_count,adtype,link,intro,commentCount,sharelink";
    private static final String DETAIL_METHOD = "mag.hot.detail.get";

    private static final String MAGAZINE_FIELDS = "id,title,pub_time,is_free,cover,point,update_time,point_number,vol,vol_year,comment_count,price,iapid,is_buy,is_recommend,mag_package,addr,name,size,thumb";
    private static final String MAGAZINE_METHOD = "mags.get";

    private static final String MAGAZINE_DETAIL_CONTENT_METHOD = "mag.get";
    private static final String MAGAZINE_DETAIL_CONTENT_FIELDS = "id,title,desc,pub_time,is_free,cover,point,update_time,point_number,vol,vol_year,comment_count,price,iapid,is_buy,is_recommend,mag_package,addr,name,size,thumb";

    private static final String MAGAZINE_DETAIL_COMMENTS_METHOD = "mag.detail.comments.get";
    private static final String MAGAZINE_DETAIL_COMMENTS_FIELDS = "id,createtime,username,nick,desc,user_id,mark,user,comment_id";

    private static final String URL = "http://open.vistastory.com/mobilecms/interface.action?";


    HttpRequest createHttpRequest(@NonNull RequestParams requestParams) {
        HttpRequest.Builder builder = new Builder();
        builder.setUrl(URL);
        switch (requestParams.getType()) {
            case ReqType.TITLE:
                setTitleRequest(requestParams, builder);
                break;
            case ReqType.BANNER:
                setBannerRequest(requestParams, builder);
                break;
            case ReqType.FEEDS:
                setFeedsRequest(requestParams, builder);
                break;
            case ReqType.DETAIL:
                setDetailRequest(requestParams, builder);
                break;
            case ReqType.MAGAZINE:
                setMagazineRequest(requestParams, builder);
                break;
            case ReqType.MAGAZINE_DETAIL_CONTENT:
                setMagazineDetailContentRequest(requestParams, builder);
                break;
            case ReqType.MAGAZINE_DETAIL_COMMENTS:
                setMagazineDetailCommentsRequest(requestParams, builder);
                break;
            default:
                break;
        }
        return builder.build();
    }

    private void setTitleRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.POST);
        HashMap<String, String> forms = new HashMap<>(16);
        forms.put(KEY_CODE, CODE_VALUE);
        forms.put(KEY_METHOD, TITLE_METHOD);
        forms.put(KEY_PARENT_ID, "0");
        forms.put(KEY_FORMAT, FORMAT_JSON);
        forms.put(KEY_FIELDS, TITLE_FIELDS);
        builder.addForms(forms);
    }

    private void setBannerRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.GET);
        HashMap<String, String> paths = new HashMap<>(16);
        paths.put(KEY_CODE, CODE_VALUE);
        paths.put(KEY_METHOD, BANNER_METHOD);
        paths.put(KEY_IS_TOP, "1");
        paths.put(KEY_FORMAT, FORMAT_JSON);
        paths.put(KEY_FIELDS, BANNER_FIELDS);
        paths.put(KEY_CAT_ID, String.valueOf(requestParams.getTitleId()));
        paths.put(KEY_PAGE_NO, "1");
        paths.put(KEY_PAGE_SIZE, "200");
        builder.addQueryPaths(paths);
    }

    private void setFeedsRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.GET);
        HashMap<String, String> paths = new HashMap<>(16);
        paths.put(KEY_CODE, CODE_VALUE);
        paths.put(KEY_METHOD, BANNER_METHOD);
        paths.put(KEY_IS_TOP, "0");
        paths.put(KEY_FORMAT, FORMAT_JSON);
        paths.put(KEY_FIELDS, BANNER_FIELDS);
        paths.put(KEY_CAT_ID, String.valueOf(requestParams.getTitleId()));
        paths.put(KEY_PAGE_NO, String.valueOf(requestParams.getPageNo()));
        paths.put(KEY_PAGE_SIZE, "9");
        builder.addQueryPaths(paths);
    }

    private void setDetailRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.POST);
        HashMap<String, String> forms = new HashMap<>(16);
        forms.put(KEY_CODE, CODE_VALUE);
        forms.put(KEY_METHOD, DETAIL_METHOD);
        forms.put(KEY_FORMAT, FORMAT_JSON);
        forms.put(KEY_FIELDS, DETAIL_FIELDS);
        forms.put(KEY_DETAIL_ID, String.valueOf(requestParams.getDetailId()));
        builder.addForms(forms);
    }

    private void setMagazineRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.POST);
        HashMap<String, String> forms = new HashMap<>(16);
        forms.put(KEY_CODE, CODE_VALUE);
        forms.put(KEY_METHOD, MAGAZINE_METHOD);
        forms.put(KEY_FORMAT, FORMAT_JSON);
        forms.put(KEY_FIELDS, MAGAZINE_FIELDS);
        forms.put(KEY_PAGE_NO, String.valueOf(requestParams.getPageNo()));
        forms.put(KEY_TYPE, PHONE);
        forms.put(KEY_PAGE_SIZE, "9");
        builder.addForms(forms);
    }

    private void setMagazineDetailContentRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.POST);
        ArrayMap<String, String> forms = new ArrayMap<>(16);
        forms.put(KEY_MAG_ID, String.valueOf(requestParams.getMagazineId()));
        forms.put(KEY_CODE, CODE_VALUE);
        forms.put(KEY_METHOD, MAGAZINE_DETAIL_CONTENT_METHOD);
        forms.put(KEY_TYPE, PHONE);
        forms.put(KEY_FORMAT, FORMAT_JSON);
        forms.put(KEY_FIELDS, MAGAZINE_DETAIL_CONTENT_FIELDS);
        builder.addForms(forms);
    }

    private void setMagazineDetailCommentsRequest(@NonNull RequestParams requestParams, HttpRequest.Builder builder) {
        builder.setMethod(Method.POST);
        ArrayMap<String, String> forms = new ArrayMap<>(16);
        forms.put(KEY_MAG_ID, String.valueOf(requestParams.getMagazineId()));
        forms.put(KEY_CODE, CODE_VALUE);
        forms.put(KEY_METHOD, MAGAZINE_DETAIL_COMMENTS_METHOD);
        forms.put(KEY_TYPE, "1");
        forms.put(KEY_FORMAT, FORMAT_JSON);
        forms.put(KEY_FIELDS, MAGAZINE_DETAIL_COMMENTS_FIELDS);
        forms.put(KEY_PAGE_NO, String.valueOf(requestParams.getPageNo()));
        forms.put(KEY_PAGE_SIZE, "9");
        forms.put(KEY_DETAIL_ID, String.valueOf(requestParams.getDetailId()));
        builder.addForms(forms);
    }

}
