package com.perspikyliator.user.accelerometer.model;

public class AccelData {

    private String date;
    private String x;
    private String y;
    private String z;

    public AccelData(String date, String x, String y, String z) {
        this.date = date;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "AccelData{" +
                "date='" + date + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccelData accelData = (AccelData) o;

        if (date != null ? !date.equals(accelData.date) : accelData.date != null) return false;
        if (x != null ? !x.equals(accelData.x) : accelData.x != null) return false;
        if (y != null ? !y.equals(accelData.y) : accelData.y != null) return false;
        return !(z != null ? !z.equals(accelData.z) : accelData.z != null);

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        return result;
    }
}
