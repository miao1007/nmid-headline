package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;

/**
 * Created by leon on 1/11/15.
 */
public abstract class GenericAdapter<T> extends BaseAdapter {

    // the main data list to save loaded data
    protected LinkedList<T> dataList;

    protected Context context;

    // the serverListSize is the total number of items on the server side,
    // which should be returned from the web request results
    protected int serverListSize = -1;

    // Two view types which will be used to determine whether a row should be displaying
    // data or a Progressbar
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    public static  enum ViewType{
        LOADING,
        FEEDS
    };


    public GenericAdapter(LinkedList<T> list, Context activity) {
        context = activity;
        dataList = list;
    }


    public void setServerListSize(int serverListSize) {
        this.serverListSize = serverListSize;
    }


    /**
     * disable click events on indicating rows
     */
    @Override
    public boolean isEnabled(int position) {

        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
    }

    /**
     * One type is normal data row, the other type is Progressbar
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    /**
     * the size of the List plus one, the one is the last row, which displays a Progressbar
     */
    @Override
    public int getCount() {
        return dataList.size() + 1;
    }


    /**
     * return the type of the row,
     * the last row indicates the user that the ListView is loading more data
     */
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return (position >= dataList.size()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? dataList
                .get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position
                : -1;
    }

    /**
     * returns the correct view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            // display the last row
            return getFooterView(position, convertView, parent);
        } else {
            return getDataRow(position, convertView, parent);
        }

    }

    public abstract View getDataRow(int position, View convertView, ViewGroup parent);


    public abstract View getFooterView(int position, View convertView,
                                       ViewGroup parent);

}