package cn.edu.cqupt.nmid.headline.support.task.callback;

/**
 * Created by leon on 1/16/15.
 */
public interface WebContentGetTaskCallback<T> {
    void onPreExcute();

    void onSuccess(T t);


}
