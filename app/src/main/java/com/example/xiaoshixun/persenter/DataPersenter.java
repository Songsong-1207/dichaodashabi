package com.example.xiaoshixun.persenter;

import com.example.xiaoshixun.bean.DataBean;
import com.example.xiaoshixun.module.DataModule;
import com.example.xiaoshixun.view.Dataview;

public class DataPersenter {
    private Dataview dataview;
    private DataModule module = null;

    public DataPersenter(Dataview dataview) {
        this.dataview = dataview;
        module = new DataModule();
    }
    public void gatdata(){
        module.getdata(new CallBack() {
            @Override
            public void getdata(DataBean dataBean) {
                dataview.getdata(dataBean);
            }
        });
    }
}
