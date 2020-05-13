package com.example.xiaoshixun;

import com.example.xiaoshixun.bean.DataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String URL = "http://yun918.cn";
    @GET("/live/xsxcjfsj.json")
    Observable<DataBean> getdata();
}
