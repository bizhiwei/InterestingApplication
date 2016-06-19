package com.gentlemanb.shinelon.interestingifapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView content_main_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarAndFloatBtn();
        findViewsById();
        folder = initFolder();

    }

    private FileOutputStream fileOutputStream;

    /**
     * 文件夹
     */
    private static final String FOLDER_NAME = "AInterestingif";
    private static final String ENDNAME = ".gif";

    private File folder;

    @NonNull
    private File initFolder() {
        File folder = new File(Environment.getExternalStorageDirectory()
                + File.separator+FOLDER_NAME);//创建文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }


    private String[] names = {"皇上", "一个寡妇", "书呆子", "太医", "夜壶", "御前带刀侍卫", "皇后", "宫女", "太监总管", "太监"};

    private void initBitmap() {
        Canvas canvas;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg).copy(Bitmap.Config.ARGB_8888, true);
        canvas = new Canvas(bitmap);
        File file = new File(folder, ((int)(Math.random()*200000))+"" + ENDNAME);
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.i("aaaaaaaa",fileOutputStream+file.getAbsolutePath());
            e.printStackTrace();
        }
        AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
        animatedGifEncoder.start(fileOutputStream);
        animatedGifEncoder.setSize(256, 256);
        animatedGifEncoder.setDelay(32);
        for (int i = 0; i < names.length; i++) {
            canvas.drawColor(0xffffffff);
            canvas.drawText(names[i], 128-textPaint.measureText(names[i])/2, 128, textPaint);
            animatedGifEncoder.addFrame(bitmap);
        }
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        animatedGifEncoder.finish();
        bitmap.recycle();
    }

    Bitmap bitmap;
    private TextPaint textPaint;

    private void findViewsById() {

        textPaint = new TextPaint();
        textPaint.setTextSize(16);
        content_main_rv = (RecyclerView) findViewById(R.id.content_main_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        content_main_rv.setLayoutManager(layoutManager);
        List list = new ArrayList();
        for (int i = 0; i < 56; i++) {
            list.add("" + i);
        }
        CardAdapter adapter = new CardAdapter(list);
        content_main_rv.setAdapter(adapter);
    }

    private void setToolbarAndFloatBtn() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBitmap();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
