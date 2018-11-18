package com.oliver.seenews.base.download;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class TaskQueue {

    private Config mConfig;
    private PriorityBlockingQueue<Task> mDownloadQueue;
    private ExecutorService mDownloadExecutor;

    private static final int QUEUE_INITIAL_CAPACITY = 20;

    public TaskQueue(Config config) {
        mConfig = config;
        init();
    }

    public boolean add(Task task) {
        if (!mDownloadQueue.contains(task)) {
            return mDownloadQueue.add(task);
        }
        return false;
    }

    private void init() {
        mDownloadQueue = new PriorityBlockingQueue<>(QUEUE_INITIAL_CAPACITY, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.compareTo(o2);
            }
        });
        mDownloadExecutor = new ThreadPoolExecutor(mConfig.getConcurrentSize(), mConfig.getConcurrentSize(), 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < mConfig.getConcurrentSize(); ++i) {
            mDownloadExecutor.execute(new Dispatcher());
        }
    }

    private class Dispatcher implements Runnable {

        private HttpDownloadJob mHttpDownloadJob = new HttpDownloadJob();

        @Override
        public void run() {
            while (true) {
                try {
                    Task task = mDownloadQueue.take();
                    mHttpDownloadJob.exec(mConfig, task);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }
}
