package com.example.mjb.todo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mjb.todo.database.TaskdbSchema;
import com.example.mjb.todo.models.Task;
import com.example.mjb.todo.models.Tasklab;
import com.example.mjb.todo.models.User;
import com.example.mjb.todo.models.Userlab;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {


    private static final String USERNAMEID = "usernameid";
    private ViewPager mViewPager;
   private List<Task> mTasks;
    TabLayout mTabLayout;
    TabItem mTabItemLeft;
    TabItem mTabItemCenter;
    TabItem mTabItemRight;
    FloatingActionButton mFloatingActionButton;
    private Fragment taskListFragment;
    private String username;

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context,ViewPagerActivity.class);
        intent.putExtra(USERNAMEID,username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra(USERNAMEID);
        setContentView(R.layout.activity_view_pager);
        mViewPager = findViewById(R.id.task_view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabItemLeft = findViewById(R.id.tab_left);
        mTabItemCenter = findViewById(R.id.tab_center);
        mTabItemRight = findViewById(R.id.tab_right);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
                return POSITION_NONE;
            }
            @Override
            public Fragment getItem(int position) {
                 taskListFragment = TaskListFragment.newInstance(position,username);
                return taskListFragment;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "ALL";
                    case 1:
                        return "DONE";
                    case 2:
                        return "UNDONE";
                    default:
                        return null;
                }
            }
            @Override
            public int getCount() {
                return 3;
            }
        });

        mTabLayout.setupWithViewPager(mViewPager,true);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
