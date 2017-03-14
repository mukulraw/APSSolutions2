package com.aps.user.apssolutions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CustomerDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private ArrayList<OrderBean> orderList;
    String username,userid;
    TextView tv_username;
    ProgressDialog pd;
    CardView cv;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cv=(CardView)findViewById(R.id.cv);
        pref = getSharedPreferences("pree" , MODE_PRIVATE);
        edit = pref.edit();
        session = new SessionManager(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("userid");
        /*HashMap<String, String> user = session.getUserDetails();
        userid=user.get(SessionManager.KEY_PASSWORD);
        username=user.get(SessionManager.KEY_NAME);*/


        orderList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(CustomerDashboard.this);
        pd.setMessage("");
        pd.show();
        loadJSON();

        Toast.makeText(CustomerDashboard.this,"Height:"+userid,
                Toast.LENGTH_LONG).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        tv_username=(TextView)header.findViewById(R.id.username);
        tv_username.setText("WELCOME: "+username);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order) {
            Intent intent = new Intent(CustomerDashboard.this, CustomerOrders.class);
            intent.putExtra("user_type", userid);
            startActivity(intent);

        } else if (id == R.id.logout) {

                Intent i = new Intent(CustomerDashboard.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                edit.remove("username");
                edit.apply();

                startActivity(i);
                finish();

            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


    private void loadJSON(){
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<OrderBean> call = request.orders(userid);

        call.enqueue(new Callback<OrderBean>() {
            @Override
            public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {



                response.body().getOrderDetail();
             /*  adapter = new AlbumsAdapter(CustomerDashboard.this,response.body().getOrderDetail());*/
                recyclerView.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<OrderBean> call, Throwable t) {

            }
        });
    }



}

