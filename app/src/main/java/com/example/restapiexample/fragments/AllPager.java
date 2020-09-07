package com.example.restapiexample.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapiexample.R;
import com.google.android.material.snackbar.Snackbar;

public class AllPager extends Fragment {
    LinearLayout linearLayout;
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private TextView text;
    private ImageView donut;
    private ImageView ice_cream;
    private ImageView froyo;

    public AllPager() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_all_pager, container, false);
        findViews();
        return rootView;

    }

    private void findViews(){
        donut = rootView.findViewById(R.id.donut);
        ice_cream = rootView.findViewById(R.id.ice_cream);
        froyo = rootView.findViewById(R.id.froyo);

        donut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonutOrder(v);
            }
        });
        ice_cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIcecreamOrder(v);
            }
        });
        froyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFroyoOrder(v);
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void displaySnack(final String message) {
        Toast.makeText(getActivity().getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showDonutOrder(View view){
        displaySnack(getString(R.string.donut_order_message));
    }
    public void showIcecreamOrder(View view){
        displaySnack(getString(R.string.ice_cream_order_message));
    }
    public void showFroyoOrder(View view){
        displaySnack(getString(R.string.froyo_order_message));
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
