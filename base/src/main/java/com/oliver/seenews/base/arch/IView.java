package com.oliver.seenews.base.arch;

public interface IView <T extends IPresenter> {

    void setPresenter(T presenter);

}
