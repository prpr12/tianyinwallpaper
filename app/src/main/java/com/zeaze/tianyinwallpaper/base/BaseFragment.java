package com.zeaze.tianyinwallpaper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {
    protected View view;
    private CompositeDisposable compositeDisposable;

    public BaseFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(getLayout(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
        init();
    }

    protected void toast(String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected abstract void init();
    protected abstract int getLayout();

    protected void addDisposable(Disposable disposable){
        if (compositeDisposable==null)compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable!=null) compositeDisposable.dispose();
        super.onDestroy();
    }

    public  boolean canBack(){
        return false;
    }
}

