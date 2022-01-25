package com.example.esc_walker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class GetPlaneInfo {

    public static String getPlaneId(String pt){
        StringBuffer buffer = new StringBuffer();
        String buf = "null";
        String depPlaceId = pt;
        String serviceKey = "BJgPByzRIVjyd286CSF1ySqATMF%2BaQXFH2c3FD8kXKzJXcGoiX9mIA5l9i1sifZmdTBOKIpeGkb3F7iaIG%2BtPg%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getArprtList" +
                "?serviceKey=" + serviceKey;

        try{
            boolean aid = false;
            boolean nm = false;
            boolean foundid = false;
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
                        else if (tag.equals("airportId")) {
                            if(!foundid)aid = true;
                        }
                        else if (tag.equals("airportNm")) {
                            nm = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(aid){
                            buf = xpp.getText();
                            aid = false;
                        }
                        else if(nm){
                            if(depPlaceId.equals(xpp.getText())) {
                                foundid = true;
                            }
                            nm = false;
                        }
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
        return buf;
    }

    public static ArrayList<Plane> getPlaneData(String dep, String arr, String date){
        ArrayList<Plane> list = null;
        Plane plane = null;

        String numOfRows = "20";
        String pageNo = "1";
        //TODO dep, arr -> terminal Id
        String depAirportId = dep;
        String arrAirportId = arr;
        String depPlandTime = date;
        String serviceKey = "BJgPByzRIVjyd286CSF1ySqATMF%2BaQXFH2c3FD8kXKzJXcGoiX9mIA5l9i1sifZmdTBOKIpeGkb3F7iaIG%2BtPg%3D%3D";

        String queryUrl = "http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList" +
                "?serviceKey=" + serviceKey + "&numOfRows="+ numOfRows + "&pageNo=" + pageNo + "&depAirportId=" + depAirportId +
                "&arrAirportId=" + arrAirportId + "&depPlandTime=" + depPlandTime;

        try{
            boolean charge = false;
            boolean depTime = false;
            boolean airline = false;
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
                        list = new ArrayList<Plane>();
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")){
                            plane = new Plane();
                        }
                        else if (tag.equals("economyCharge")) {
                            charge = true;
                        }
                        else if (tag.equals("airlineNm")) {
                            airline = true;
                        }
                        else if (tag.equals("arrPlandTime")) {
                            arrTime = true;
                        }
                        else if (tag.equals("depPlandTime")) {
                            depTime = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(charge){
                            plane.setCharge(xpp.getText());
                            charge = false;
                        }else if(airline){
                            plane.setAirline(xpp.getText());
                            airline = false;
                        }else if(arrTime){
                            time = xpp.getText().substring(8);
                            String temp = time.substring(0,2);
                            String temp2 = time.substring(2);
                            time = temp+":"+temp2;
                            plane.setArrTime(time);
                            arrTime = false;
                        }else if(depTime){
                            time = xpp.getText().substring(8);
                            String temp = time.substring(0,2);
                            String temp2 = time.substring(2);
                            time = temp+":"+temp2;
                            plane.setDepTime(time);
                            depTime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item") && plane != null) list.add(plane);
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
