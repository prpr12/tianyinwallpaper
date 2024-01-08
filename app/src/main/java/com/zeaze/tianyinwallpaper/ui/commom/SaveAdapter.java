package com.zeaze.tianyinwallpaper.ui.commom;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.zeaze.tianyinwallpaper.R;
import com.zeaze.tianyinwallpaper.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {
    private Context context;
    private onClick onClick;
    static List<SaveData> saveDataList=new ArrayList<>();

    public SaveAdapter(final Context context,String dataPath, onClick onClick){
        this.context=context;
        this.onClick=onClick;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s= FileUtil.loadData(context,dataPath);
                saveDataList= JSON.parseArray(s,SaveData.class);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_save, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int ii) {
        final int i =ii;
        viewHolder.tv.setText(saveDataList.get(i).name);
        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(saveDataList.get(i).getS(),saveDataList.get(i).name);
            }
        });
        viewHolder.tvde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("选择操作")
                        .setPositiveButton("上移一格", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                up(i);
                            }
                        })
                        .setNegativeButton("下移一格", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                down(i);
                            }
                        })
                        .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(i);
                            }
                        })
                        .create()
                        .show();

            }
        });
    }

    private void up(int i){
        if (i==0) return;
        SaveData saveData=saveDataList.get(i-1);
        saveDataList.set(i-1,saveDataList.get(i));
        saveDataList.set(i,saveData);
        notifyDataSetChanged();
        onClick.onChange(i);
    }

    private void down(int i){
        if (i==saveDataList.size()-1) return;
        SaveData saveData=saveDataList.get(i+1);
        saveDataList.set(i+1,saveDataList.get(i));
        saveDataList.set(i,saveData);
        notifyDataSetChanged();
        onClick.onChange(i);
    }

    private void delete(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认删除")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveDataList.remove(i);
                        notifyDataSetChanged();
                        onClick.onChange(i);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .create()
                .show();
    }

    @Override
    public int getItemCount() {
        return saveDataList==null?0 :saveDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv,tvde;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tvde=itemView.findViewById(R.id.tv_de);
        }
    }

    public interface onClick{
        void onClick(String s,String name);
        void onChange(int i);
    }

    public static List<SaveData> getSaveDataList() {
        if (saveDataList==null)saveDataList=new ArrayList<>();
        return saveDataList;
    }
}
