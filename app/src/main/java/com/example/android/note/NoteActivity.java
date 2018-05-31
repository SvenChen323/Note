package com.example.android.note;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.note.database.NoteUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{

    private Toolbar mToolbar;
    private EditText mTitleET;
    private EditText mContentET;
    private Note mNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseTheme();
        setContentView(R.layout.activity_note);
        initData();
        initView();
    }

    private void initData(){
        mToolbar = (Toolbar) findViewById(R.id.note_toolbar);
        mTitleET = (EditText) findViewById(R.id.title_edit_view);
        mContentET = (EditText) findViewById(R.id.content_edit_view);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null){

            int id = bundle.getInt("id");
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            long time = bundle.getLong("time");

            mNote = new Note(id,title,content,time);

            mTitleET.setText(mNote.getTitle());
            mContentET.setText(mNote.getContent());

        }else {


            mTitleET.setText("");
            mContentET.setText("");


        }

    }

    private void initView(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        mToolbar.setOnMenuItemClickListener(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.makesure:
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){
                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(this).updateNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }

                }else {

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){
                        mNote = new Note();
                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(this).addNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            case R.id.delete:
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){
                    NoteUtil.getsNoteUtil(this).deleteNote(mNote.getId());
                    finish();
                }else {
                    mTitleET.setText("");
                    mContentET.setText("");
                }
                return true;


        }


        return true;
    }
}
