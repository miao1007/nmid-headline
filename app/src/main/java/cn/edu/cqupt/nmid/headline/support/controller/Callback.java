package cn.edu.cqupt.nmid.headline.support.controller;

/**
 * Created by leon on 1/14/15.
 */
public interface Callback<T> {
    void success(T t);

    void failed(Exception e);
}
