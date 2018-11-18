package com.oliver.seenews.common;

import com.oliver.seenews.base.util.FileUtils;

public class Constant {

    public static final class ARouter {

        public static final String WELCOME_PATH = "/ui/welcome";
        public static final String HOME_PATH = "/ui/home";
        public static final String DETAIL_PATH = "/ui/detail";
        public static final String MAGAZINE_DETAIL_PATH = "/ui/magazine_detail";
        public static final String MAGAZINE_READ_PATH = "/ui/magazine_read";

        public static final String PARAM_ID = "id";
        public static final String PARAM_PATH = "dmm_path";
        public static final String PARAM_TITLE = "dmm_title";

    }

    public static final class ReqType {

        public static final int TITLE = 1;
        public static final int BANNER = 2;
        public static final int FEEDS = 3;
        public static final int DETAIL = 4;
        public static final int MAGAZINE = 5;
        public static final int MAGAZINE_DETAIL_CONTENT = 6;
        public static final int MAGAZINE_DETAIL_COMMENTS = 7;

    }

    public static final class DetailPage {
        public static final String TEMPLATE_PATH = "webpage/template.html";

        public static final String TITLE_TAG = "<!--title-->";
        public static final String TIME_TAG = "<!--datetime-->";
        public static final String AUTHOR_TAG = "<!--author-->";
        public static final String DESC_TAG = "<!--desc-->";
    }

    public static final String LOCAL_DMM_DIR = FileUtils.joinPath(FileUtils.getFilesDirPath(), "local_dmm");
}
