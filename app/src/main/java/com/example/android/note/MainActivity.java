package com.example.android.note;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.note.database.NoteUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class MainActivity extends BaseActivity implements OnThemeChangeListener{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MaterialSearchView mSearchView;
    private FloatingActionButton mFloatingActionButton;
    private List<Note> mNoteList;
    private NoteListAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseTheme();
        setContentView(R.layout.activity_main);
        initData();
        initView();



    }


    private void initData(){
        mNoteList = NoteUtil.getsNoteUtil(this).getNotes();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_button);
        Log.i("test","current size "+ mNoteList.size());
        mAdapter = new NoteListAdapter(this,mNoteList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(mToolbar);
        mToolbar.setLogo(getResources().getDrawable(R.drawable.app_notes));
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
        initSearchView();

    }

    /**
     * 初始化搜索框
     */
    private void initSearchView(){
        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Note> queryList = NoteUtil.getsNoteUtil(MainActivity.this).search(newText);
                mAdapter.setNoteList(queryList);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                updateUI();
            }
        });
    }




    private void reStartActivity() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    /**
     * 筛选逻辑
     */
//    private List<Note> filter(List<Note> notes,String query){
//
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        List<Note> noteList = NoteUtil.getsNoteUtil(this).getNotes();
        mNoteList = noteList;
        if(mAdapter == null){
            mAdapter = new NoteListAdapter(this,noteList);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            if(mAdapter.getItemCount() != noteList.size()){
                mAdapter = new NoteListAdapter(this,noteList);
                mRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.setNoteList(noteList);
                mAdapter.notifyDataSetChanged();
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.write_p:
//                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
//                startActivity(intent);
//                return true;
            case R.id.change_skin:

//                setBaseTheme();
                //reStartActivity();
                ColorDialogFragment fragment = new ColorDialogFragment();
                fragment.setOnThemeChangeListener(this);
                fragment.show(getSupportFragmentManager(),"theme");
                return true;
            case R.id.action_backup:
                //数据备份
                try {
                    File dbFile = new File("/data/data/com.example.android.note/databases/"+"noteDB.db");
                    File file = new File(Environment.getExternalStorageDirectory(), "noteDB.db");
                    Log.i("test","1 " + dbFile.getAbsolutePath());
                    Log.i("test","2 " + file.getAbsolutePath());
                    if (!file.exists()) {
                        try {
                            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                                    PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                            }
                            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                                    PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                            }
                            file.createNewFile();
                            Log.i("test","no exites");
                        } catch (IOException e) {
                            Log.i("test",e.toString());
                            e.printStackTrace();
                        }
                    }else {
                        Log.i("test"," exites");
                    }
                    FileInputStream is = new FileInputStream(dbFile);
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buff = new byte[1024];
                    int n = 0;
                    while ((n = is.read(buff)) > 0) {
                        Log.e("tag", "len=" + n);
                        out.write(buff, 0, n);
                    }
                    is.close();
                    out.close();
                }catch (Exception e){
                    Log.i("test","catch " + e.toString());
                }

                return true;
            case R.id.action_backdata:
                //数据恢复
                NoteUtil.getsNoteUtil(this).returnData();
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onThemeChange() {
        setBaseTheme();
        reStartActivity();
    }
}
