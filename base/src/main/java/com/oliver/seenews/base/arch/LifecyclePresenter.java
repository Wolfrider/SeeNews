package com.oliver.seenews.base.arch;

public abstract class LifecyclePresenter implements IPresenter {

    protected static final int CREATED = 1;
    protected static final int STARTED = 4;
    protected static final int RESUMED = 5;
    protected static final int PAUSED = 3;
    protected static final int STOPPED = 2;
    protected static final int DESTROYED = -1;

    protected int mState;

    public ILifecycleListener getLifecycleListener() {
        return new ILifecycleListener() {
            @Override
            public void onCreate() {
                LifecyclePresenter.this.onCreate();
            }

            @Override
            public void onStart() {
                LifecyclePresenter.this.onStart();
            }

            @Override
            public void onResume() {
                LifecyclePresenter.this.onResume();
            }

            @Override
            public void onPause() {
                LifecyclePresenter.this.onPause();
            }

            @Override
            public void onStop() {
                LifecyclePresenter.this.onStop();
            }

            @Override
            public void onDestroy() {
                LifecyclePresenter.this.onDestroy();
            }
        };
    }

    protected void onCreate() {
        mState = CREATED;
    }

    protected void onStart() {
        mState = STARTED;
    }

    protected void onResume() {
        mState = RESUMED;
    }

    protected void onPause() {
        mState = PAUSED;
    }

    protected void onStop() {
        mState = STOPPED;
    }

    protected void onDestroy() {
        mState = DESTROYED;
    }

    protected boolean isAtLeast(int state) {
        return mState >= state;
    }

}

