package com.example.interfaces;

import com.example.interfaces.adapter.DataAdapter;
import com.example.interfaces.adapter.ListItem;
import com.example.interfaces.adapter.RecOnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavItemSelectedListener{
    private RecOnClickListener recOnClickListener;
    private DataAdapter adapter;
    private List<ListItem> listData;
    private RecyclerView rcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMenu();
        setRecOnClickListener();
        init();
    }
    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragmentList mMenuFragment = (MenuFragmentList) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuFragmentList();
            mMenuFragment.setNavItemSelectedListener(this);
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    @Override
    public void onNavItemSelectedListener(MenuItem item) {
        //Toast.makeText(this, item.getTitle(),Toast.LENGTH_SHORT).show();
        switch(item.getItemId()){
            case R.id.id_favorite:
                break;
            case R.id.id_topic1_1:
                updateMainList(getResources().getStringArray(R.array.topic1_1));
                break;
            case R.id.id_topic1_2:
                updateMainList(getResources().getStringArray(R.array.topic1_2));
                break;
            case R.id.id_topic1_3:
                updateMainList(getResources().getStringArray(R.array.topic1_3));
                break;
            case R.id.id_topic2_1:
                updateMainList(getResources().getStringArray(R.array.topic2_1));
                break;
            case R.id.id_topic2_2:
                updateMainList(getResources().getStringArray(R.array.topic2_2));
                break;
            case R.id.id_topic2_3:
                updateMainList(getResources().getStringArray(R.array.topic2_3));
                break;
            case R.id.id_topic3_1:
                updateMainList(getResources().getStringArray(R.array.topic3_1));
                break;
            case R.id.id_topic3_2:
                updateMainList(getResources().getStringArray(R.array.topic3_2));
                break;
            case R.id.id_topic3_3:
                updateMainList(getResources().getStringArray(R.array.topic3_3));
                break;
        }
    }
    private void updateMainList(String[] array){
        List<ListItem> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            ListItem item = new ListItem();
            item.setText(array[i]);
            item.setFavorite(false);
            list.add(item);
        }
        adapter.updateList(list);
    }

    private void init(){
        rcView = findViewById(R.id.rcView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();
        String[] topic1_1Array = getResources().getStringArray(R.array.topic1_1);
        for(int i = 0; i < topic1_1Array.length; i++){
            ListItem item = new ListItem();
            item.setText(topic1_1Array[i]);
            item.setFavorite(false);
            listData.add(item);
        }
        adapter = new DataAdapter(this, recOnClickListener, listData);
        rcView.setAdapter(adapter);
    }

    private void setRecOnClickListener(){
        recOnClickListener = new RecOnClickListener() {
            @Override
            public void onItemClicked(int pos) {
                Toast.makeText(MainActivity.this, "Position =" + pos, Toast.LENGTH_SHORT).show();
            }
        };
    }

}