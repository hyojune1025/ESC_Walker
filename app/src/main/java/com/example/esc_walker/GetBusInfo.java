package com.example.esc_walker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class GetBusInfo {

    public static String getBusId(String tm){
        StringBuffer buffer = new StringBuffer();

        String numOfRows = "1";
        String pageNo = "1";
        String depPlaceId = tm;
        String serviceKey = "Fs01Vn3RHQ8uHoNuww6iprSsVRznk9sNFrvnoaOzslDiis2KPyeeYSwebpzVc8Tp5w2VyRjLvrgBVW5AR4iyJw%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/ExpBusInfoService/getExpBusTrminlList" +
                "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&terminalNm=" + depPlaceId;

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
                        else if (tag.equals("terminalId")) {
                            xpp.next();
                            buffer.append(xpp.getText());
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
        return buffer.toString();
    }

    public static ArrayList<Bus> getBusData(String dep, String arr, String date){
        ArrayList<Bus> list = null;
        Bus bus = null;

        String numOfRows = "3";
        String pageNo = "1";
        //TODO dep, arr -> terminal Id
        String depTerminalId = dep;
        String arrTerminalId = arr;
        String depPlandTime = date;
        String serviceKey = "Fs01Vn3RHQ8uHoNuww6iprSsVRznk9sNFrvnoaOzslDiis2KPyeeYSwebpzVc8Tp5w2VyRjLvrgBVW5AR4iyJw%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/ExpBusInfoService/getStrtpntAlocFndExpbusInfo" +
                "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&depTerminalId=" + depTerminalId +
                "&arrTerminalId=" + arrTerminalId + "&depPlandTime=" + depPlandTime;

        try{
            boolean charge = false;
            boolean start = false;
            boolean arrive = false;
            boolean arrTime = false;

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
                        list = new ArrayList<Bus>();
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")){
                            bus = new Bus();
                        }
                        else if (tag.equals("charge")) {
                            charge = true;
                        }
                        else if (tag.equals("arrPlaceNm")) {
                            arrive = true;
                        }
                        else if (tag.equals("arrPlandTime")) {
                            arrTime = true;
                        }
                        else if (tag.equals("depPlaceNm")) {
                            start = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(charge){
                            bus.setCharge(xpp.getText());
                            charge = false;
                        }else if(arrive){
                            bus.setArrplaceNm(xpp.getText());
                            arrive = false;
                        }else if(arrTime){
                            bus.setArrPlandTime(xpp.getText());
                            arrTime = false;
                        }else if(start){
                            bus.setDepPlaceNm(xpp.getText());
                            start = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item") && bus != null) list.add(bus);
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
