package com.example.aff02.secondtask.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.adapters.RecycleAdapter;
import com.example.aff02.secondtask.database.DatabaseHelper;
import com.example.aff02.secondtask.model.DetailModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private List<DetailModel> detailModelslist;
    private RecyclerView recyclerView;
    private RecycleAdapter mAdapter;
    public LinearLayoutManager linearLayoutManager;
    public DetailModel detailModel;
    public DatabaseHelper databaseHelper;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     * @param param1
     * @param param2
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper = new DatabaseHelper(this.getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());

        detailModelslist = new ArrayList<DetailModel>();
        detailModelslist = databaseHelper.getData();

        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecycleAdapter(detailModelslist,getContext());

        if(detailModelslist.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            RecycleAdapter mAdapter = new RecycleAdapter(detailModelslist,this.getActivity());
            recyclerView.setAdapter(mAdapter);

        }else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no data in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
