package org.eclipse.paho.android.service.sample;



public class WifiData {
    private String BSSID; // Mac주소(고유번호)
    private String SSID; // 보여줄 이름
    private boolean isOn; // 허용or차단

    public WifiData(String BSSID, String SSID, boolean isOn) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.isOn = isOn;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}