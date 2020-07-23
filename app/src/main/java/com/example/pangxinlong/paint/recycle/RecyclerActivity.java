package com.example.pangxinlong.paint.recycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pangxinlong.paint.R;

/**
 * Date: 2020/7/22
 * author: pangxinlong
 * Description:
 */
public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rootview, new RecyclerFragment());
        fragmentTransaction.commit();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RecyclerActivity.class);
        context.startActivity(intent);
    }
}
