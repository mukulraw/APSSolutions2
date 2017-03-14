package com.aps.user.apssolutions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddOrder extends AppCompatActivity {

    Spinner country , state , city , projectName;
    private Toolbar toolbar;

    List<Country> countrydata;
    List<State> stateData;
    List<Object> cityData;
    ProgressBar progress;
    List<String> da,stringState,stringCity , projectString;
    String countryid,stateid;

    List<ProjectDetail> projList;

    List<City> cities;

    TextView timePeriod , currency , securityFees;


    String countryName = "" , stateName = "" , cityName = "" , pName = "";


    EditText clientName , companyName , location , mobile , email , currencyRate , total , slotDate , advance , balance , second;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Add Order");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add = (Button)findViewById(R.id.btnsubmit);

        progress = (ProgressBar)findViewById(R.id.progress);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addOrder();

            }
        });
        clientName = (EditText)findViewById(R.id.client_name);
        companyName = (EditText)findViewById(R.id.company_name);
        location = (EditText)findViewById(R.id.location);
        mobile = (EditText)findViewById(R.id.mobile);
        email = (EditText)findViewById(R.id.email);
        currencyRate = (EditText)findViewById(R.id.currency_rate);
        total = (EditText)findViewById(R.id.total_amount);
        slotDate = (EditText)findViewById(R.id.slot_date);
        advance = (EditText)findViewById(R.id.advance_amount);
        balance = (EditText)findViewById(R.id.balance_amount);
        second = (EditText)findViewById(R.id.second_amount);



        timePeriod = (TextView)findViewById(R.id.time_period);
        currency = (TextView)findViewById(R.id.currency);
        securityFees = (TextView)findViewById(R.id.security_fees);

        projList = new ArrayList<>();

        country=(Spinner)findViewById(R.id.country);
        state=(Spinner)findViewById(R.id.state);
        city=(Spinner)findViewById(R.id.city);
        projectName=(Spinner)findViewById(R.id.projectName);

        countrydata=new ArrayList<>();
        stateData=new ArrayList<>();
        cityData=new ArrayList<>();
        da=new ArrayList<>();
        stringState=new ArrayList<>();
        stringCity=new ArrayList<>();
        cities = new ArrayList<>();


        loadCountryJSON();
        loadProjectName();


        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryid=countrydata.get(i).getId();

                countryName = da.get(i);

                loadState(countryid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateid = stateData.get(i).getId();

                stateName = stringState.get(i);

                loadCity(stateid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cityName = stringCity.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        projectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String id = projList.get(i).getId();

                pName = projectString.get(i);

                loadProjDetails(id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void loadProjDetails(String id)
    {
        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);

        Call<projectDetailBean> call = request.getProjDetails(id);

        call.enqueue(new Callback<projectDetailBean>() {
            @Override
            public void onResponse(Call<projectDetailBean> call, Response<projectDetailBean> response) {

                timePeriod.setText(response.body().getTimePeriod());
                securityFees.setText(response.body().getSecurityFees());
                currency.setText(response.body().getCurrency());
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<projectDetailBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }


    private void loadProjectName()
    {
        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);

        Call<projNameBean> call = request.getProjName();

        call.enqueue(new Callback<projNameBean>() {
            @Override
            public void onResponse(Call<projNameBean> call, Response<projNameBean> response) {
                projList = response.body().getProjectDetail();

                projectString = new ArrayList<String>();

                for (int i = 0 ; i < projList.size() ; i++)
                {
                    projectString.add(projList.get(i).getProjectName());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddOrder.this , R.layout.spinnertext , projectString);
                //adapter.setDropDownViewResource(R.layout.listviewspinner);
                projectName.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<projNameBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }


    private void loadCountryJSON(){

        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<CountryBean> call = request.country();

        call.enqueue(new Callback<CountryBean>() {
            @Override
            public void onResponse(Call<CountryBean> call, Response<CountryBean> response) {

                countrydata = response.body().getCountry();

                for (int aa=0 ; aa< countrydata.size() ; aa++)
                    {
                        da.add(countrydata.get(aa).getCountryName());
                    }
                ArrayAdapter adapter = new ArrayAdapter(AddOrder.this , R.layout.spinnertext , da);
                //adapter.setDropDownViewResource(R.layout.listviewspinner);
                country.setAdapter(adapter);
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CountryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }


    private void loadState(String id)
    {
        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<StateBean> call = request.getstate(id);

        call.enqueue(new Callback<StateBean>() {
            @Override
            public void onResponse(Call<StateBean> call , Response<StateBean> response) {

                stateData=response.body().getStates();

                stringState = new ArrayList<String>();

                for (int a=0;a< stateData.size();a++)
                {
                    stringState.add(stateData.get(a).getStateName());
                }

                ArrayAdapter adapter1 = new ArrayAdapter(AddOrder.this , R.layout.spinnertext , stringState);
                //adapter1.setDropDownViewResource(R.layout.listviewspinner);
                state.setAdapter(adapter1);

                progress.setVisibility(View.GONE);

          }

            @Override
            public void onFailure(Call<StateBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    private void loadCity(String id){

        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<CityBean> call = request.getcity(id);

        call.enqueue(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, Response<CityBean> response) {

               cities = response.body().getCities();

                stringCity = new ArrayList<String>();

                for (int b=0;b< cities.size();b++)
                {
                    stringCity.add(cities.get(b).getStateName());
                }

                ArrayAdapter adapter1 = new ArrayAdapter(AddOrder.this,R.layout.spinnertext,stringCity);
                //adapter1.setDropDownViewResource(R.layout.listviewspinner);
                city.setAdapter(adapter1);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }


    private void addOrder()
    {
        progress.setVisibility(View.VISIBLE);

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final FranchiseAPI request = retro.create(FranchiseAPI.class);

        User b = (User)getApplicationContext();

        Call<addOrderBean> call = request.addOrder(b.id , email.getText().toString() , clientName.getText().toString() , companyName.getText().toString() , countryName , stateName , cityName , location.getText().toString() , mobile.getText().toString() , pName , timePeriod.getText().toString() , currency.getText().toString() , securityFees.getText().toString() , currencyRate.getText().toString() , total.getText().toString() , slotDate.getText().toString() , advance.getText().toString() , balance.getText().toString() , second.getText().toString());

        call.enqueue(new Callback<addOrderBean>() {
            @Override
            public void onResponse(Call<addOrderBean> call, Response<addOrderBean> response) {

                Toast.makeText(getApplicationContext() , response.body().getStatus() , Toast.LENGTH_SHORT).show();

                progress.setVisibility(View.GONE);

                finish();
            }

            @Override
            public void onFailure(Call<addOrderBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


}
