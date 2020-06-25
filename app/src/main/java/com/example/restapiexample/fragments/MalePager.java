package com.example.restapiexample.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.restapiexample.R;
import com.example.restapiexample.activities.CustomAdapter;
import com.example.restapiexample.model.Users;
import com.example.restapiexample.sqlite.UserDBHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MalePager.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MalePager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MalePager extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private ListView lv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MalePager() {
    }

    public static MalePager newInstance(String param1, String param2) {
        MalePager fragment = new MalePager();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_profile_pager, container, false);
        findViews();
        return rootView;
    }

    private void findViews(){
        lv = (ListView) rootView.findViewById(R.id.list);
        UserDBHelper dbHelper = new UserDBHelper(rootView.getContext());
        ArrayList<Users> users = new ArrayList<>();
        users = dbHelper.getUsers();
        CustomAdapter customAdapter = new CustomAdapter(rootView.getContext(), users);
        lv.setAdapter(customAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
