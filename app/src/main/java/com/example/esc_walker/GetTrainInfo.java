package com.example.esc_walker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class GetTrainInfo {
    public static String getTrainId(String tm,int cityCode){
        int code = cityCode;
        String cityId = null;
        String numOfRows = "20";
        String pageNo = "1";
        String depPlaceId = tm;
        String serviceKey = "Fs01Vn3RHQ8uHoNuww6iprSsVRznk9sNFrvnoaOzslDiis2KPyeeYSwebpzVc8Tp5w2VyRjLvrgBVW5AR4iyJw%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyAcctoTrainSttnList" +
                "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&cityCode="+cityCode;

        try{
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) cityId = null;
                        else if (tag.equals("nodeid")) {
                            xpp.next();
                            cityId = xpp.getText();
                        }
                        else if(tag.equals("nodename")){
                            xpp.next();
                            if(tm.equals(xpp.getText())){
                                return cityId;
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item"));
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            //TODO Auto-generated catch block.printStackTrace();
        }
        return cityId;
    }

    public static ArrayList<Train> getTrainData(String dep, String arr, String date){
        ArrayList<Train> list = null;
        Train train = null;

        String numOfRows = "20";
        String pageNo = "1";
        //TODO dep, arr -> terminal Id
        String depPlaceId = dep;
        String arrPlaceId = arr;
        String depPlandTime = date;
        String serviceKey = "Fs01Vn3RHQ8uHoNuww6iprSsVRznk9sNFrvnoaOzslDiis2KPyeeYSwebpzVc8Tp5w2VyRjLvrgBVW5AR4iyJw%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo" +
                "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&depPlaceId=" + depPlaceId +
                "&arrPlaceId=" + arrPlaceId + "&depPlandTime=" + depPlandTime;

        try{
            boolean charge = false;
            boolean depTime = false;
            boolean name = false;
            boolean arrTime = false;
            String time;

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<Train>();
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")){
                            train = new Train();
                        }
                        else if (tag.equals("adultcharge")) {
                            charge = true;
                        }
                        else if (tag.equals("traingradename")) {
                            name = true;
                        }
                        else if (tag.equals("arrplandtime")) {
                            arrTime = true;
                        }
                        else if (tag.equals("depplandtime")) {
                            depTime = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(charge){
                            train.setCharge(xpp.getText());
                            charge = false;
                        }else if(name){
                            train.settName(xpp.getText());
                            name = false;
                        }else if(arrTime){
                            time = xpp.getText().substring(8);
                            String temp = time.substring(0,2);
                            String temp2 = time.substring(2,4);
                            time = temp+":"+temp2;
                            train.setArrTime(time);
                            arrTime = false;
                        }else if(depTime){
                            time = xpp.getText().substring(8);
                            String temp = time.substring(0,2);
                            String temp2 = time.substring(2,4);
                            time = temp+":"+temp2;
                            train.setDepTime(time);
                            depTime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item") && train != null) list.add(train);
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            //TODO Auto-generated catch block.printStackTrace();
        }
        return list;
    }
}
