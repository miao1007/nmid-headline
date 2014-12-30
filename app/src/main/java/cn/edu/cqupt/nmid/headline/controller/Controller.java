package cn.edu.cqupt.nmid.headline.controller;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;

import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.controller.bean.Datum;
import cn.edu.cqupt.nmid.headline.controller.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;
import cn.edu.cqupt.nmid.headline.dao.HttpHelper;
import cn.edu.cqupt.nmid.headline.dao.MyDataBaseHelper;
import cn.edu.cqupt.nmid.headline.support.Constant;

public class Controller {
	private MyDataBaseHelper dataBaseHelper;

	public Controller(Context context) {
		dataBaseHelper = MyDataBaseHelper.getInstance(context, "headline.db", 1);
	}

	public void InitData(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit) {
		new InitDataThread(handler, tempList, category, limit, this).start();
	}

	public void RefreshData(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit) {
		new RefreshDataThread(handler, tempList, category, limit, this).start();
	}

	public void OldData(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit) {
		new OldDataThread(handler, tempList, category, limit, this).start();
	}
	public HeadJson getHeadJson(int id, int category, int limit, String type){
		String jsonStr = HttpHelper.getJosn(id, category, limit, type);
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, HeadJson.class);
	}
	/**
	 *
	 * @param category
	 *            分类
	 * @param limit
	 *            数量
	 * @return
	 * @throws java.io.IOException
	 *             1.查询最新limit条，如果数量=limit，返回LinkedList。
	 *             2.否则将数据库中的条数加入LinkedList，向服务器请求剩余条数。
	 */

	public LinkedList<NewsBean> getInitData(LinkedList<NewsBean> listBeans, int category,
			int limit) {
		Cursor cursor = dataBaseHelper.getData("select * from "
				+ Constant.TYPE[category] + " order by _id desc limit " + limit);
		int id = 0;
		if (cursor.getCount() == limit) {
			for (int i = 0; i < limit && cursor.moveToNext(); i++) {
				NewsBean newsBean = getBean(cursor);
				listBeans.add(newsBean);
			}
			cursor.close();
			return listBeans;
		} else {
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					NewsBean newsBean = getBean(cursor);
					listBeans.add(newsBean);
				}
				cursor.moveToLast();
				id = cursor.getInt(1);
			}
			HeadJson bean = getHeadJson(id, category,limit - cursor.getCount(), "freshnews");
			if(bean.getStatus()==0)
				return listBeans;
			for (int i = 0; i < bean.getData().size(); i++) {
				Datum data = bean.getData().get(i);
				NewsBean newsBean = getBean(data);
				listBeans.addFirst(newsBean);
				dataBaseHelper.insertData(newsBean,
						Constant.TYPE[data.getCategory()]);
			}
			cursor.close();
			return listBeans;
		}
	}

	/**
	 * 下拉刷新函数
	 *
	 * @param category
	 *            分类
	 * @return
	 * @throws java.io.IOException
	 *
	 */
	public void getRefreshData(LinkedList<NewsBean> listBeans, int category,
			int limit){
		HeadJson bean = getHeadJson(listBeans.get(0).get_id(), category,limit, "freshnews");
		if (bean.getData() == null) {
			return;
		}
		else if (bean.getData().size() < limit) {
			for (int i = 0; i < bean.getData().size(); i++) {
				Datum data = bean.getData().get(i);
				NewsBean newsBean = getBean(data);
				listBeans.addFirst(newsBean);
				dataBaseHelper.insertData(newsBean,
						Constant.TYPE[data.getCategory()]);
			}
			return;
		} else {
			listBeans.clear();
			for (int i = 0; i < bean.getData().size(); i++) {
				Datum data = bean.getData().get(i);
				NewsBean newsBean = getBean(data);
				listBeans.addFirst(newsBean);
				dataBaseHelper.insertData(newsBean,
						Constant.TYPE[data.getCategory()]);
			}
			return;
		}
	}

	/**
	 * 上拉加载
	 *
	 * @param listBeans
	 * @param category
	 * @param limit
	 * @return
	 * @throws java.io.IOException
	 *             1.查询小于页面最小id-1的所有数据 2.如果为0
	 *             如果id小于limit，发送请求?id=id+1&category=category&limit=id;
	 *             如果id大于limit，?id=id+1&category=category&limit=limit; 如果不为0
	 *             取结果集第一个数据 如果数据库id=id 取出加入list 如果不等于 num=id-数据库id;请求数量
	 *             num<limit ?id=id+1&category=category&limit=num; num>limit
	 *             num=limit; ?id=id+1&category=category&limit=limit;
	 *             如果数量达到，返回list，否则把数据库id加入。
	 *
	 *
	 */
	public void getOldData(LinkedList<NewsBean> listBeans,
			int category, int limit){
		int id = listBeans.getLast().get_id() - 1;
		Cursor cursor = dataBaseHelper.getData("select * from "
				+ Constant.TYPE[category] + " WHERE _id<=" + id
				+ " order by _id desc limit " + limit);
		if (cursor.getCount() == 0) {
			if (id < limit) {
				HeadJson bean = getHeadJson(id + 1, category,id, "oldnews");
				if (bean.getData() == null) {
					cursor.close();
					return;
				}
				for (int i = 0; i < bean.getData().size(); i++) {
					Datum data = bean.getData().get(i);
					NewsBean newsBean = getBean(data);
					listBeans.addLast(newsBean);
					dataBaseHelper.insertData(newsBean,
							Constant.TYPE[data.getCategory()]);
				}
				cursor.close();
				return;
			} else {
				HeadJson bean = getHeadJson(id + 1, category,limit, "oldnews");
				if (bean.getData() == null) {
					return;
				}
				for (int i = 0; i < bean.getData().size(); i++) {
					Datum data = bean.getData().get(i);
					NewsBean newsBean = getBean(data);
					listBeans.addLast(newsBean);
					dataBaseHelper.insertData(newsBean,
							Constant.TYPE[data.getCategory()]);
				}
				cursor.close();
				return;
			}
		} else {
			int sum = 0;
			while (cursor.moveToNext()) {
				if (cursor.getInt(0) == id) {
					listBeans.addLast(getBean(cursor));
					sum++;
					id--;
					if (sum == limit) {
						cursor.close();
						return;
					}
				} else {
					int num = id - cursor.getInt(0);
					if (num > (limit - sum)) {
						num = limit;
					}
					HeadJson bean = getHeadJson(id + 1, category,num, "oldnews");
					if (bean.getData() == null) {
						return;
					}
					for (int i = 0; i < bean.getData().size(); i++) {
						Datum data = bean.getData().get(i);
						NewsBean newsBean = getBean(data);
						listBeans.addLast(newsBean);
						dataBaseHelper.insertData(newsBean,
								Constant.TYPE[data.getCategory()]);
						sum++;
						id--;
					}
					if (sum == limit) {
						cursor.close();
						return;
					} else {
						listBeans.addLast(getBean(cursor));
						sum++;
						id--;
						if (sum == limit) {
							cursor.close();
							return;
						}
					}
				}
			}
		}
		return;
	}

	/**
	 * 根据结果集返回Bean对象
	 *
	 * @param cursor
	 * @return
	 */
	public NewsBean getBean(Cursor cursor) {
		NewsBean newsBean = new NewsBean();
		newsBean.set_id(cursor.getInt(0));
		newsBean.setCategory((cursor.getInt(1)));
		newsBean.setTitle(cursor.getString(2));
		newsBean.setContent((cursor.getString(4)));
		newsBean.setSimpleContent(cursor.getString(3));
		newsBean.setImage1((cursor.getString(5)));
		newsBean.setImage2(cursor.getString(6));
		newsBean.setImage3((cursor.getString(7)));
		newsBean.setTimeRelease(cursor.getString(8));
		return newsBean;
	}

	/**
	 * 根据Datum对象数据返回Bean对象
	 *
	 * @param data
	 * @return
	 */
	public NewsBean getBean(Datum data) {
		NewsBean newsBean = new NewsBean();
		newsBean.set_id(data.getId());
		newsBean.setCategory(data.getCategory());
		newsBean.setTitle(data.getTitle());
		newsBean.setSimpleContent(data.getSimple_content());
		newsBean.setContent(data.getContent());
		newsBean.setImage1(data.getImage1());
		newsBean.setImage2(data.getImage2());
		newsBean.setImage3(data.getImage3());
		newsBean.setTimeRelease(data.getTime_release());
		return newsBean;
	}
}
