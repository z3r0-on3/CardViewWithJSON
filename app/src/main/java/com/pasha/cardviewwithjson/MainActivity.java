package com.pasha.cardviewwithjson;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<JSONInfo> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPointer();

    }

    private void setPointer(){

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        result = new ArrayList<>();


        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://a4da5d6e-8226-49f4-b9dd-c55932d76aed-bluemix.cloudant.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetInterface getInterface = retro.create(GetInterface.class);
        Call<List<JSONInfo>> call = getInterface.getJSON();
        call.enqueue(new Callback<List<JSONInfo>>() {
            @Override
            public void onResponse(Call<List<JSONInfo>> call, Response<List<JSONInfo>> response) {
                List<JSONInfo> list = response.body();
                JSONInfo info = null;
                for(int i=0 ; i<list.size() ; i++){

                    info = new JSONInfo();
                    String name = list.get(i).getName();
                    String url = list.get(i).getUrl();
                    String image = list.get(i).getImage();
                    info.setName(name);
                    info.setUrl(url);
                    info.setImage(image);
                    result.add(info);

                }

                DataAdapter dataAdapter = new DataAdapter(result, ImageLoader.getInstance());
                RecyclerView.LayoutManager recManager = new GridLayoutManager(MainActivity.this, 2);
                recyclerView.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
                recyclerView.setLayoutManager(recManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(dataAdapter);

            }

            @Override
            public void onFailure(Call<List<JSONInfo>> call, Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });

    }

    public class GridSpacingdecoration extends RecyclerView.ItemDecoration {

        private int span;
        private int space;
        private boolean include;

        public GridSpacingdecoration(int span, int space, boolean include) {
            this.span = span;
            this.space = space;
            this.include = include;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % span;

            if (include) {
                outRect.left = space - column * space / span;
                outRect.right = (column + 1) * space / span;

                if (position < span) {
                    outRect.top = space;
                }
                outRect.bottom = space;
            } else {
                outRect.left = column * space / span;
                outRect.right = space - (column + 1) * space / span;
                if (position >= span) {
                    outRect.top = space;
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




}


