package com.example.esc_walker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    ArrayAdapter<CharSequence> adsp_start_city, adsp_arrive_city, adsp_start_tm, adsp_arrive_tm; //spinner 정보 출력할 adapter

    //test for api TODO
    ArrayList<Bus> list_bus = null;
    ArrayList<Train> list_train = null;
    ArrayList<Plane> list_plane = null;
    //RecyclerView recyclerView;

    //api 불러올 때 사용해야 하는 정보
    String start_tm;
    String arrive_tm;
    String start_id;
    String arrive_id;
    String start_date;
    
    private ImageButton search_ibtn_back;

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

        //RecyclerView TODO
//        recyclerView = (RecyclerView)findViewById(R.id.rcv_result);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);

        final Spinner sp_start_city = (Spinner)findViewById(R.id.sp_start_city);
        final Spinner sp_arrive_city = (Spinner)findViewById(R.id.sp_arrive_city);
        final Spinner sp_start_tm = (Spinner)findViewById(R.id.sp_start_terminal);
        final Spinner sp_arrive_tm = (Spinner)findViewById(R.id.sp_arrive_terminal);

        ibt_bus = findViewById(R.id.ibt_bus);
        ibt_train = findViewById(R.id.ibt_train);
        ibt_airplane = findViewById(R.id.ibt_airplane);

        search_btn_lookup = (Button)findViewById(R.id.serach_btn_lookup);

        //버스 입력모드
        ibt_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adsp_start_city = ArrayAdapter.createFromResource(SearchCity.this,R.array.spinner_city_start, android.R.layout.simple_spinner_dropdown_item);
                adsp_start_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_start_city.setAdapter(adsp_start_city);
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
                    //TODO recycler view 와 연동
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                start_id = GetBusInfo.getBusId(start_tm);
                                arrive_id = GetBusInfo.getBusId(arrive_tm);
                                list_bus = GetBusInfo.getBusData(start_id,arrive_id,start_date);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyAdapter_bus adapter = new MyAdapter_bus(getApplicationContext(),list_bus);
                                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_result);
                                        recyclerView.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);
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
                    //TODO recycler view 와 연동
                    @Override
                    public void onClick(View v) {
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
                                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_result);
                                        recyclerView.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);
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
            }
        });

        search_ibtn_back = findViewById(R.id.search_ibtn_back);
        search_btn_lookup = findViewById(R.id.serach_btn_lookup);
        ibt_date = findViewById(R.id.ibt_calendar);

        //날짜 입력
        ibt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        start_id = GetPlaneInfo.GetPlaneId(start_tm);
                        arrive_id = GetPlaneInfo.GetPlaneId(arrive_tm);
                        list_plane = GetPlaneInfo.getPlaneData(start_id,arrive_id,start_date);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyAdapter_train adapter = new MyAdapter_train(getApplicationContext(),list_train);
                                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_result);
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchCity.this);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }).start();

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