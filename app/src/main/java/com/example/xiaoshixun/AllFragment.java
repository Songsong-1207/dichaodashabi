package com.example.xiaoshixun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoshixun.bean.DataBean;
import com.example.xiaoshixun.persenter.DataPersenter;
import com.example.xiaoshixun.view.Dataview;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment implements Dataview {

    private RecyclerView mRecycle;
    private RvAdapter rvAdapter;
    private ArrayList<DataBean.ListBean> listBeans;
    private DataPersenter dataPersenter;

    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentn
        dataPersenter = new DataPersenter(this);
        View inflate = inflater.inflate(R.layout.fragment_all, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View itemView) {
        mRecycle = (RecyclerView) itemView.findViewById(R.id.recycle);
        mRecycle.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        listBeans = new ArrayList<>();
        rvAdapter = new RvAdapter(getActivity(), listBeans);
        mRecycle.setAdapter(rvAdapter);
        rvAdapter.setOnLongClickListener(new RvAdapter.OnLongClickListener() {
            @Override
            public void onLongClickListener(int posi) {
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
                normalDialog.setIcon(R.mipmap.ic_launcher);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("是否删除");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBean.ListBean listBean = listBeans.get(posi);
                                listBeans.remove(listBean);
                                rvAdapter.notifyDataSetChanged();
                            }
                        });
                normalDialog.setNegativeButton("关闭", null);
                // 显示
                normalDialog.show();
            }
        });
        initdata();
    }

    private void initdata() {
        dataPersenter.gatdata();
    }

    @Override
    public void getdata(DataBean dataBean) {
        List<DataBean.ListBean> list = dataBean.getList();
        listBeans.addAll(list);
        rvAdapter.notifyDataSetChanged();
        ArrayList<DataBean.ListBean> datas= new ArrayList<>();
        for (DataBean.ListBean listBean : list) {
            boolean bool = listBean.getType() == 1 ? true:false;
            if (bool){
                datas.add(listBean);
            }
        }
        EventBus.getDefault().postSticky(datas);
    }
}
