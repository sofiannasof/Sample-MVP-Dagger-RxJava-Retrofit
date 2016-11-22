package com.sample.mvp.dagger_rxjava_retrofit.base;

/**
 * Created by smenesid on 21/11/2016.
 */

public interface BaseView <T> {
    void setPresenter(T presenter);
}
