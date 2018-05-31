package com.example.android.note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 *
 */

public class ColorDialogFragment extends DialogFragment {


    private RecyclerView mRecyclerView;
    private OnThemeChangeListener mOnThemeChangeListener;

    public void setOnThemeChangeListener(OnThemeChangeListener onThemeChangeListener) {
        mOnThemeChangeListener = onThemeChangeListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_color_theme,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setAdapter(new GridViewAdapter());

        return view;

    }



    public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridHolder>{

        private int[] colors = new int[]{
                R.color.green,R.color.blue,R.color.red,R.color.yellow,R.color.pink,R.color.purple
        };

        @Override
        public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.color_item,parent,false);
            return new GridHolder(view);
        }

        @Override
        public void onBindViewHolder(GridHolder holder, int position) {
            holder.bind(colors[position],position);
        }

        @Override
        public int getItemCount() {
            return colors.length;
        }

        public  class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private ImageView mImageView;
            private int mId;
            public GridHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView;
                mImageView.setOnClickListener(this);
            }

            public void bind(int resColorId,int id){
                mImageView.setBackgroundColor(getResources().getColor(resColorId));
                this.mId = id;
            }

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                        "theme", MODE_PRIVATE);
                sharedPreferences.edit().putInt("theme_type",mId).commit();
                Log.i("test","click");

                if(mOnThemeChangeListener != null)
                {
                    mOnThemeChangeListener.onThemeChange();
                }
                getActivity().onBackPressed();

            }
        }
    }

    private void reStartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
