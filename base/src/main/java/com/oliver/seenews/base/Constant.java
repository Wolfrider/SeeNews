package com.oliver.seenews.base;

public class Constant {

    public static final class Http {

        public static final class Header {
            public static final String CONTENT_LENGTH = "Content-Length";
        }

        public static final class Method {
            public static final int GET = 0;

            public static final int POST = 1;
        }

        public static final class Request {
            public static final int TIME_OUT = 10;

            public static final int RETRY_COUNT = 2;
        }

        public static final class ErrorCode {
            public static final int UNKNOWN = -1000;

            public static final int NO_ROUTE_HOST = -1001;

            public static final int CONNECT_EXCEPTION = -1002;

            public static final int SOCKET_EXCEPTION = -1003;

            public static final int SOCKET_TIME_OUT = -1004;

            public static final int UNKNOWN_SERVICE = -1005;

            public static final int UNKNOWN_HOST = -1006;

            public static final int SSL_PEER_UNVERIFIED = -1007;

            public static final int NO_RESPONSE_BODY = -1008;
        }
    }

    public static final class Network {

        public static final int NET_NO_CONNECT = 0;

        public static final int NET_WIFI = 1;

        public static final int NET_MOBILE = 2;

        public static final int NET_OTHER = 3;

    }

    public static final class Download {

        public static final int DEFAULT_CONCURRENT_SIZE = 3;
        public static final int MAX_CONCURRENT_SIZE = 10;
        public static final String CACHE_DIR_NAME = "download_cache";

        public static final class Priority {

            public static final int LOW = -1;

            public static final int NORMAL = 0;

            public static final int HIGH = 1;
        }

        public static final class Status {

            public static final int NONE = 0;

            public static final int START = 1;

            public static final int FAIL = 2;

            public static final int FINISH = 3;

            public static final int CANCEL = 4;
        }

        public static final class ErrorCode {

            public static final int NO_SIZE = -2000;

            public static final int SAVE_ERROR = -2001;

            public static final int INVALID_MD5 = -2002;
        }
    }

    public static final int BUFFER_SIZE = 100 * 1024;

}
