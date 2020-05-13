package com.example.xiaoshixun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoshixun.bean.DataBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShipinFragment extends Fragment {

    private RecyclerView mSprv;
    private RvAdapter rvAdapter;
    private ArrayList<DataBean.ListBean> listBeans;

    public ShipinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_shipin, container, false);
        initView(view);
        return view;
    }

    private void initView( View itemView) {
        mSprv = (RecyclerView) itemView.findViewById(R.id.sprv);
        mSprv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mSprv.setLayoutManager(new LinearLayoutManager(getActivity()));
        listBeans = new ArrayList<>();
        rvAdapter = new RvAdapter(getActivity(), listBeans);
        mSprv.setAdapter(rvAdapter);
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
    }
    @Subscribe(sticky = true)
    public void getdata(ArrayList<DataBean.ListBean> datas){
        listBeans.addAll(datas);
        rvAdapter.notifyDataSetChanged();
    }

}
