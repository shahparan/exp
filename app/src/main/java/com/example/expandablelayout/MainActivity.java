package com.example.expandablelayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.expandablelayout.NetworkRequest.ApiInterface;
import com.example.expandablelayout.NetworkRequest.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ProgressBar progress_circular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        progress_circular = (ProgressBar) findViewById(R.id.progress_circular);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // preparing list data
        prepareListData();

    }

    /*
     * Preparing the list data
     */

    private void prepareListData() {

        progress_circular.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<GetCategories> call = apiInterface.getCategories();
        call.enqueue(new Callback<GetCategories>() {
            @Override
            public void onResponse(Call<GetCategories> call, Response<GetCategories> response) {

                for (int i = 0; i < response.body().getCategories().size() - 1; i++) {

                    List<String> comingSoon = new ArrayList<String>();

                    for (int j = 0; j < response.body().getCategories().get(i).getSubcatg().size() - 1; j++) {
                        comingSoon.add(response.body().getCategories().get(i).getSubcatg().get(j).getSubCategoryName());
                        Log.i("dfdfadf4444",response.body().getCategories().get(i).getSubcatg().get(j).getSubCategoryName());
                    }

                    Log.i("dfdfadf444466",response.body().getCategories().get(i).getCategoryName());

                    listDataHeader.add(response.body().getCategories().get(i).getCategoryName());

                    listDataChild.put(response.body().getCategories().get(i).getCategoryName(), comingSoon); // Header, Child data


                }

                progress_circular.setVisibility(View.GONE);

                listAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<GetCategories> call, Throwable t) {

            }
        });

    }
}
