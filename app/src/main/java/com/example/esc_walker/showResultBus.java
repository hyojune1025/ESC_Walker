package com.example.esc_walker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class showResultBus {
    public class MainActivity extends AppCompatActivity {

        final static String TAG = "walkerProject";

        Button bt_lookup;
        Spinner et_dep;
        Spinner et_arr;
        TextView tv_result;

        String tv_data;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);

            bt_lookup = (Button)findViewById(R.id.serach_btn_lookup);
            et_dep = (Spinner)findViewById(R.id.search_et_start);
            et_arr = (Spinner)findViewById(R.id.search_et_arrive);
            tv_result = (TextView)findViewById(R.id.tv_bus);

            bt_lookup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            tv_data = getBusData(et_dep.toString(), et_arr.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_result.setText(tv_data);
                                }
                            });
                        }
                    }).start();
                }
            });
        }

        String tNametoTid(String name){
            String tName[] = {"서울경부", "센트럴", "동서울",
                    "인천", "인천공항",
                    "대전복합", "대전청사",
                    "동대구", "서대구",
                    "울산",
                    "부산", "사상",
                    "광주", "광주비아" };
            String tId[] = {"NAEK010", "NAEK020", "NAEK030",
                    "NAEK100", "NAEK105",
                    "NAEK300", "NAEK305",
                    "NAEK801", "NAEK801",
                    "NAEK715",
                    "NAEK700", "NAEK703",
                    "NAEK500", "NAEK503" };

            String terminalId = "NAEK010";

            for(int i=0; i<tName.length; i++){
                if(tName.equals(tName[i])){
                    terminalId = tId[i];
                }
            }
            return terminalId;
        }

        String getBusData(String dep, String arr){
            StringBuffer buffer = new StringBuffer();

            String numOfRows = "10";
            String pageNo = "1";
            String depTerminalId = tNametoTid(et_dep.toString());//"NAEK010";
            String arrTerminalId = tNametoTid(et_arr.toString());//"NAEK300";
            String depPlandTime = "20200101";
            String busGradeId = "1";
            String serviceKey = "Fs01Vn3RHQ8uHoNuww6iprSsVRznk9sNFrvnoaOzslDiis2KPyeeYSwebpzVc8Tp5w2VyRjLvrgBVW5AR4iyJw%3D%3D";

            String queryUrl = "http://openapi.tago.go.kr/openapi/service/ExpBusInfoService/getStrtpntAlocFndExpbusInfo" +
                    "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&depTerminalId=" + depTerminalId +
                    "&arrTerminalId=" + arrTerminalId + "&depPlandTime=" + depPlandTime + "&busGradeId=" + busGradeId;

            try{
                URL url = new URL(queryUrl);
                InputStream is = url.openStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                xpp.next();
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();

                            if (tag.equals("item")) ;
                            else if (tag.equals("charge")) {
                                buffer.append("요금 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("depPlaceNm")) {
                                buffer.append("출발지 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("depPlandTime")){
                                buffer.append("출발 시간 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("arrPlaceNm")) {
                                buffer.append("도착지 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("arrPlandTime")) {
                                buffer.append("도착 시간 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("gradeNm")) {
                                buffer.append("버스등급명 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if (tag.equals("routeId")) {
                                buffer.append("노선ID : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();
                            if (tag.equals("item")) buffer.append("\n");
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                //TODO Auto-generated catch block.printStackTrace();
            }
            return buffer.toString();
        }
    }
}
