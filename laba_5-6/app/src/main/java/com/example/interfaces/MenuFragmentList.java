package com.example.interfaces;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.interfaces.R;
import com.google.android.material.navigation.NavigationView;

public class MenuFragmentList extends Fragment {
    private NavItemSelectedListener NavItemSelectedListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);

        NavigationView vNavigation = view.findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                NavItemSelectedListener.onNavItemSelectedListener(menuItem);
                return false;
            }
        }) ;

        return  view ;
    }

    public void setNavItemSelectedListener(com.example.interfaces.NavItemSelectedListener navItemSelectedListener) {
        NavItemSelectedListener = navItemSelectedListener;
    }
}