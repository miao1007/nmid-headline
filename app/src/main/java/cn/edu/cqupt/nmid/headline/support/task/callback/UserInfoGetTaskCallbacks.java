package cn.edu.cqupt.nmid.headline.support.task.callback;

import cn.edu.cqupt.nmid.headline.support.api.user.User;

/**
 * Created by leon on 1/16/15.
 */
public interface UserInfoGetTaskCallbacks {
    public void onUserInfoGetTaskPreExecute();

    public void onUserInfoGetTaskPostExecute(User user);
}
