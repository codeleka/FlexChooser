package com.codeleka.lib.choosergallery;
/***
 * This file Helper Library Created By Codeleka team to Select Media images and Video easily
 * Contact us for High Quality App development Services
 *
 *
 * Thanks for developing your App by Us .
 * Team : Codeleka
 * Website : https://codeleka.com
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codeleka.lib.choosergallery.adapter.TabAdapter;
import com.codeleka.lib.choosergallery.fragments.AlbumFragment;
import com.codeleka.lib.choosergallery.fragments.AllFragment;
import com.codeleka.lib.choosergallery.model.FilesModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ChooserActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabAdapter mMyAdapter;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);

        mMyAdapter = new TabAdapter(getSupportFragmentManager());


        mMyAdapter.addFragment(new AllFragment(this::selectImage,this), "Recent");
        mMyAdapter.addFragment(new AlbumFragment(this::selectImage,this), "Folder");


        viewPager.setAdapter(mMyAdapter);
        tabLayout.setupWithViewPager(viewPager);


        findViewById(R.id.tool_done).setOnClickListener(v -> {
            try {
                if (FlexChooser.onResult != null) {
                    FlexChooser.onResult.onResult(FlexChooser.paths);
                }
            } catch (Exception ignored) {

            }
            finish();
        });


        setTool("Select File");

    }

    private void selectImage(int position, FilesModel filesModel) {
        if (filesModel.isSelected()) {
            if (FlexChooser.isNotExist(filesModel.getPath())) {
                FlexChooser.paths.add(filesModel.getPath());
            }
        } else {
            FlexChooser.paths.remove(filesModel.getPath());
        }
        selectChange();
    }

    private void selectChange() {
        String selected = "Selected : " + FlexChooser.paths.size() + " / " + FlexChooser.MAX_SELECT;
        setTool(selected);

        if (FlexChooser.paths.size() > 0) {
            findViewById(R.id.tool_done).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tool_done).setVisibility(View.GONE);
        }

    }


    private void setTool(String title) {
        ((TextView) findViewById(R.id.tool_title)).setText(title);
        findViewById(R.id.tool_back).setOnClickListener(v -> {
            super.onBackPressed();
        });


    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof AlbumFragment) {
                if (!((AlbumFragment) fragment).onBackPressed()) {
                    goBack();
                }
            }
        }
    }

    private void goBack() {
        super.onBackPressed();
    }
}