package com.example.esc_walker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragAirplane extends Fragment{
    private View view;
    
    public static FragAirplane newInstance(){
        FragAirplane fragAirplane = new FragAirplane();
        return fragAirplane;
    }//교체할 때에 대해 업데이트

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_airplane, container, false);
        
        return view;
    }
}
