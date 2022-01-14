package com.example.esc_walker;

public class Bus {
    String depPlaceNm;
    String arrplaceNm;
    String charge;
    String arrPlandTime;

    public String getDepPlaceNm(){
        return depPlaceNm;
    }

    public String getArrplaceNm() {
        return arrplaceNm;
    }

    public String getCharge() {
        return charge;
    }

    public String getArrPlandTime() {
        return arrPlandTime;
    }

    public void setDepPlaceNm(String depPlaceNm) {
        this.depPlaceNm = depPlaceNm;
    }

    public void setArrplaceNm(String arrplaceNm) {
        this.arrplaceNm = arrplaceNm;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public void setArrPlandTime(String arrPlandTime) {
        this.arrPlandTime = arrPlandTime;
    }
}
