package com.simple_android_project_template.activity;

import android.support.v7.app.AppCompatActivity;

import com.simple_android_project_template.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {
}
