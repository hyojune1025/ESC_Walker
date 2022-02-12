package com.example.esc_walker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchCity extends AppCompatActivity {

    ImageButton ibt_date;
    private Button search_btn_lookup;
    private ImageButton ibt_bus;
    private ImageButton ibt_train;
    private ImageButton ibt_airplane;
    private ImageView iv_no_result;
    ArrayAdapter<CharSequence> adsp_start_city, adsp_arrive_city, adsp_start_tm, adsp_arrive_tm; // spinner 정보 출력할 adapter

    //api 정보 받아 올 arrayList
    ArrayList<Bus> list_bus = null;
    ArrayList<Train> list_train = null;
    ArrayList<Plane> list_plain = null;

    //api 불러올 때 사용해야 하는 정보
    String start_tm;
    String arrive_tm;
    String start_id;
    String arrive_id;
    String start_date;

    LinearLayout layout7;
    TextView result1,result2,result3,result4;

    private ImageButton search_ibtn_back; // 뒤로가기 버튼

    //달력(날짜) 선택
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();
        }
    };
    private void updateLabel(){
        String date = "";
        String date_format = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(date_format, Locale.KOREA);

        TextView search_tv_date = findViewById(R.id.search_tv_date);
        search_tv_date.setText(date = sdf.format(calendar.getTime()));
        start_date = date.substring(0,4) + date.substring(5,7) + date.substring(8,10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Spinner sp_start_city = (Spinner)findViewById(R.id.sp_start_city);
        final Spinner sp_arrive_city = (Spinner)findViewById(R.id.sp_arrive_city);
        final Spinner sp_start_tm = (Spinner)findViewById(R.id.sp_start_terminal);
        final Spinner sp_arrive_tm = (Spinner)findViewById(R.id.sp_arrive_terminal);

        ibt_bus = findViewById(R.id.ibt_bus);
        ibt_train = findViewById(R.id.ibt_train);
        ibt_airplane = findViewById(R.id.ibt_airplane);

        iv_no_result = findViewById(R.id.iv_no_result);

        search_btn_lookup = (Button)findViewById(R.id.serach_btn_lookup);

        layout7 = (LinearLayout)findViewById(R.id.linearLayout7);
        result1 = findViewById(R.id.tv_result1);
        result2 = findViewById(R.id.tv_result2);
        result3 = findViewById(R.id.tv_result3);
        result4 = findViewById(R.id.tv_result4);
        layout7.setVisibility(View.GONE);
        TextView tvchange = findViewById(R.id.textView5);

        //버스 입력모드
        ibt_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //조회 결과 레이아웃 숨기기
                layout7.setVisibility(View.GONE);
                // 결과창 이미지 초기화
                iv_no_result.setImageResource(R.drawable.walk1);
                tvchange.setText("교통편을 조회하세요!");
                // 버스 선택, 나머지 초기화
                ibt_bus.setImageResource(R.drawable.bus);
                ibt_train.setImageResource(R.drawable.train_icon);
                ibt_airplane.setImageResource(R.drawable.airplane_icon);
                //첫번째 spinner(출발지) 선택
                adsp_start_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_start, android.R.layout.simple_spinner_dropdown_item);
                adsp_start_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_start_city.setAdapter(adsp_start_city);
                //첫 spinner 내용에 따라 두번째 spinner 선택 list 변경
                sp_start_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_start_city.getItem(position).equals("서울")){
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("인천")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("대구")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("대전")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daejeon_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("부산")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("울산")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("광주")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("출발지")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_bus, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_start_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_start_tm.setAdapter(adsp_start_tm);
                        sp_start_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                start_tm = adsp_start_tm.getItem(position).toString(); // start_tm에 출발지(정류장) 저장
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                adsp_arrive_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_arrive, android.R.layout.simple_spinner_dropdown_item);
                adsp_arrive_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_arrive_city.setAdapter(adsp_arrive_city);
                sp_arrive_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_arrive_city.getItem(position).equals("서울")){
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("인천")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("대구")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("대전")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daejeon_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("부산")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("울산")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("광주")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_bus, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("도착지")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_bus, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_arrive_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_arrive_tm.setAdapter(adsp_arrive_tm);
                        sp_arrive_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                arrive_tm = adsp_arrive_tm.getItem(position).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                search_btn_lookup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result1.setText("등급");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                start_id = GetBusInfo.getBusId(start_tm);
                                arrive_id = GetBusInfo.getBusId(arrive_tm);
                                list_bus = GetBusInfo.getBusData(start_id, arrive_id, start_date);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyAdapter_bus adapter = new MyAdapter_bus(getApplicationContext(), list_bus);
                                        RecyclerViewEmptySupport recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.rcv_result);
                                        recyclerView.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);

                                        if(list_bus.isEmpty()){
                                            iv_no_result.setImageResource(R.drawable.walk2); // TODO 결과 없을 때 이미지 새로
                                            TextView tvchange = findViewById(R.id.textView5);
                                            tvchange.setText("검색 결과를 찾지 못했어요..");
                                            layout7.setVisibility(View.GONE);
                                        }
                                        else layout7.setVisibility(View.VISIBLE);
                                        recyclerView.setEmptyView(iv_no_result);
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        });
        
        //기차 입력모드
        ibt_train.setOnClickListener(new View.OnClickListener() {
            int cityCode_dep,cityCode_arr;
            @Override
            public void onClick(View v) {
                //조회 결과 레이아웃 숨기기
                layout7.setVisibility(View.GONE);
                iv_no_result.setImageResource(R.drawable.walk1);
                tvchange.setText("교통편을 조회하세요!");
                ibt_bus.setImageResource(R.drawable.bus_icon);
                ibt_train.setImageResource(R.drawable.train);
                ibt_airplane.setImageResource(R.drawable.airplane_icon);
                adsp_start_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_start, android.R.layout.simple_spinner_dropdown_item);
                adsp_start_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_start_city.setAdapter(adsp_start_city);
                sp_start_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_start_city.getItem(position).equals("서울")){
                            cityCode_dep = 11;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("인천")) {
                            cityCode_dep = 23;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("대구")) {
                            cityCode_dep = 22;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("대전")) {
                            cityCode_dep = 25;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daejeon_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("부산")) {
                            cityCode_dep = 21;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("울산")) {
                            cityCode_dep = 26;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("광주")) {
                            cityCode_dep = 24;
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("출발지")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_train, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_start_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_start_tm.setAdapter(adsp_start_tm);
                        sp_start_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                start_tm = adsp_start_tm.getItem(position).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                adsp_arrive_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_arrive, android.R.layout.simple_spinner_dropdown_item);
                adsp_arrive_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_arrive_city.setAdapter(adsp_arrive_city);
                sp_arrive_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_arrive_city.getItem(position).equals("서울")){
                            cityCode_arr = 11;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("인천")) {
                            cityCode_arr = 23;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("대구")) {
                            cityCode_arr = 22;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("대전")) {
                            cityCode_arr = 25;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daejeon_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("부산")) {
                            cityCode_arr = 21;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("울산")) {
                            cityCode_arr = 26;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("광주")) {
                            cityCode_arr = 24;
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_train, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("도착지")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_train, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_arrive_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_arrive_tm.setAdapter(adsp_arrive_tm);
                        sp_arrive_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                arrive_tm = adsp_arrive_tm.getItem(position).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                search_btn_lookup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result1.setText("열차명");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                start_id = GetTrainInfo.getTrainId(start_tm,cityCode_dep);
                                arrive_id = GetTrainInfo.getTrainId(arrive_tm,cityCode_arr);
                                list_train = GetTrainInfo.getTrainData(start_id,arrive_id,start_date);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyAdapter_train adapter = new MyAdapter_train(getApplicationContext(),list_train);
                                        RecyclerViewEmptySupport recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.rcv_result);
                                        recyclerView.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);

                                        if(list_train.isEmpty()){
                                            iv_no_result.setImageResource(R.drawable.walk2); // TODO 결과 없을 때 이미지 새로
                                            TextView tvchange = findViewById(R.id.textView5);
                                            tvchange.setText("검색 결과를 찾지 못했어요..");
                                            layout7.setVisibility(View.GONE);
                                        }
                                        else layout7.setVisibility(View.VISIBLE);
                                        recyclerView.setEmptyView(iv_no_result);
                                    }
                                });
                            }
                        }).start();

                    }

                });
            }
        });

        //비행기 입력모드
        ibt_airplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //조회 결과 레이아웃 숨기기
                layout7.setVisibility(View.GONE);
                iv_no_result.setImageResource(R.drawable.walk1);
                tvchange.setText("교통편을 조회하세요!");
                ibt_bus.setImageResource(R.drawable.bus_icon);
                ibt_train.setImageResource(R.drawable.train_icon);
                ibt_airplane.setImageResource(R.drawable.airplane);
                adsp_start_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_start_airplane, android.R.layout.simple_spinner_dropdown_item);
                adsp_start_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_start_city.setAdapter(adsp_start_city);
                sp_start_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_start_city.getItem(position).equals("서울")){
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("인천")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("대구")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("제주")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_jeju_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("부산")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("울산")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("광주")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("전남")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gn_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("전북")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gb_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("강원")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kw_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("경남")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kn_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("경북")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kb_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("충북")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ch_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_start_city.getItem(position).equals("출발지")) {
                            adsp_start_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_start_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_start_tm.setAdapter(adsp_start_tm);
                        sp_start_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                start_tm = adsp_start_tm.getItem(position).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                adsp_arrive_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_arrive_airplane, android.R.layout.simple_spinner_dropdown_item);
                adsp_arrive_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_arrive_city.setAdapter(adsp_arrive_city);
                sp_arrive_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(adsp_arrive_city.getItem(position).equals("서울")){
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_tm_seoul_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("인천")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_incheon_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("대구")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_daegu_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("제주")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_jeju_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("부산")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_busan_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("울산")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ulsan_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("광주")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gwangju_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("전남")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gn_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("전북")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_gb_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("강원")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kw_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("경남")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kn_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("경북")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_kb_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("충북")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_tm_ch_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }else if(adsp_arrive_city.getItem(position).equals("도착지")) {
                            adsp_arrive_tm = ArrayAdapter.createFromResource(SearchCity.this, R.array.spinner_null_airplane, android.R.layout.simple_spinner_dropdown_item);
                        }
                        adsp_arrive_tm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_arrive_tm.setAdapter(adsp_arrive_tm);
                        sp_arrive_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                arrive_tm = adsp_arrive_tm.getItem(position).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                search_btn_lookup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result1.setText("항공사");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                start_id = GetPlaneInfo.getPlaneId(start_tm);
                                arrive_id = GetPlaneInfo.getPlaneId(arrive_tm);
                                list_plain = GetPlaneInfo.getPlaneData(start_id,arrive_id,start_date);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyAdapter_plane adapter = new MyAdapter_plane(getApplicationContext(),list_plain);
                                        RecyclerViewEmptySupport recyclerView = (RecyclerViewEmptySupport)findViewById(R.id.rcv_result);
                                        recyclerView.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);

                                        if(list_plain.isEmpty()){
                                            iv_no_result.setImageResource(R.drawable.walk2); //TODO 결과 없을 때 띄울 이미지 새로
                                            TextView tvchange = findViewById(R.id.textView5);
                                            tvchange.setText("검색 결과를 찾지 못했어요..");
                                            layout7.setVisibility(View.GONE);
                                        }
                                        else layout7.setVisibility(View.VISIBLE);
                                        recyclerView.setEmptyView(iv_no_result);
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        });

        search_ibtn_back = findViewById(R.id.search_ibtn_back);
        search_btn_lookup = findViewById(R.id.serach_btn_lookup);
        ibt_date = findViewById(R.id.ibt_calendar);

        //날짜 입력
        ibt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchCity.this,datePicker,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //뒤로가기(시작화면으로)
        search_ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchCity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}