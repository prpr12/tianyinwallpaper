package com.zeaze.tianyinwallpaper.ui.commom;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.zeaze.tianyinwallpaper.R;
import com.zeaze.tianyinwallpaper.utils.FileUtil;

import java.util.List;

public class CommomSave {
    public View view;
    public AlertDialog alertDialog;
    public SaveAdapter adapter;

    public void saveDialog(Context context,String path,String[] btnStrings,CommomSave.onClickListener listener ){
        view = LayoutInflater.from(context).inflate(R.layout.save_list, null);
        alertDialog=new AlertDialog.Builder(context).setView(view)
                .create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                alertDialog=null;
                view=null;
                adapter=null;
            }
        });
        RecyclerView recyclerView=view.findViewById(R.id.rv);
        adapter=new SaveAdapter(context, path, new SaveAdapter.onClick() {
            @Override
            public void onClick(String s,String name) {
                listener.onListClick(s,name,CommomSave.this);
            }

            @Override
            public void onChange(int i) {
                FileUtil.save(context, JSON.toJSONString(SaveAdapter.getSaveDataList()),path, new FileUtil.onSave() {
                    @Override
                    public void onSave() {
                        listener.onListChange();
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        TextView btn0=view.findViewById(R.id.btn0);
        TextView btn1 = view.findViewById(R.id.btn1);
        TextView btn2 = view.findViewById(R.id.btn2);
        if (btnStrings.length>0) {
            btn0.setVisibility(View.VISIBLE);
            btn0.setText(btnStrings[0]);
            btn0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBtnClick(CommomSave.this,0);
                }
            });
        }
        else {
            btn0.setVisibility(View.GONE);
        }
        if (btnStrings.length>1){
            btn1.setVisibility(View.VISIBLE);
            btn1.setText(btnStrings[1]);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBtnClick(CommomSave.this,1);
                }
            });
        }
        else {
            btn1.setVisibility(View.GONE);
        }
        if (btnStrings.length>2){
            btn2.setVisibility(View.VISIBLE);
            btn2.setText(btnStrings[2]);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBtnClick(CommomSave.this,2);
                }
            });
        }
        else {
            btn2.setVisibility(View.GONE);
        }
        alertDialog.show();
    }


    public interface onClickListener{
        void onBtnClick(CommomSave save,int i);
        void onListClick(String s,String name,CommomSave save);
        void onListChange();
    }

}
