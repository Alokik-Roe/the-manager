package com.bringtheaction.manager.bottomNavigationComponent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.tabs.ViewStateAdapter;
import com.google.android.material.tabs.TabLayout;


public class taskList extends Fragment {

    //
    ViewPager2 viewPager2;
    ViewStateAdapter viewStateAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);


        setTabs(view);
        setFilter(view);

        return view;
    }

    private void setTabs(View view) {

        // Find the view
        FragmentManager fragmentManager = getChildFragmentManager();

        viewStateAdapter = new ViewStateAdapter(fragmentManager, getLifecycle());
        viewPager2 = view.findViewById(R.id.pager);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(viewStateAdapter);

        // Up to here, we have working scrollable pages
        final TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(),false);
            }
        });

    }

    private void setFilter(View view) {
        final ImageButton filter = view.findViewById(R.id.filter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), filter, Gravity.END|Gravity.BOTTOM,0, R.style.CustomBackground);

                popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int s = menuItem.getItemId();

                        Bundle result = new Bundle();

                        // TODO: Set the functionality of filter
                        switch (s) {
                            case R.id.incomplete:
                                result.putInt("ID", 0);
                                break;
                            case R.id.complete:
                                result.putInt("ID", 1);
                                break;
                            case R.id.allTasks:
                                result.putInt("ID", 2);
                                break;
                        }
                        //todo: see if its working perfectly
                        getChildFragmentManager().setFragmentResult("sendId", result);

                        return true;
                    }
                });

                popupMenu.show();

            }
        });

    }

}