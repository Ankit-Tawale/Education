package com.example.asus.afinal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.afinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class News_Bookmark extends Fragment {


    public News_Bookmark() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news__bookmark, container, false);
    }

    public interface OnFragmentInteractionListener {
    }
}
