package com.aps.user.apssolutions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signatures extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView signature;
    String userid;
    List<SignatureDetail> list;
    GridLayoutManager manager;
    SignAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatures);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Signature");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signature=(RecyclerView) findViewById(R.id.signature);
        userid = getIntent().getStringExtra("user_type");
        list=new ArrayList<>();
        manager = new GridLayoutManager(this , 2);

        adapter = new SignAdapter(this , list);

        signature.setLayoutManager(manager);
        signature.setAdapter(adapter);



        Toast.makeText(Signatures.this,""+userid,
                Toast.LENGTH_LONG).show();



    }


    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<SignatureBean> call = request.getsignature(userid);

        call.enqueue(new Callback<SignatureBean>() {
            @Override
            public void onResponse(Call<SignatureBean> call, Response<SignatureBean> response) {

                list = response.body().getSignatureDetail();

                adapter.setGridData(list);

            }

            @Override
            public   void onFailure(Call<SignatureBean> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void doThis(MenuItem item){
       Intent intent=new Intent(Signatures.this,AddSignature.class);
        startActivity(intent);
    }
}
