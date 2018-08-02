package edu.xidian.sselab.cloudcourse.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;
@Data
@ToString
public class passRecord {

    private String placeId;

    private String address;

    private String  longitude;

    private String latitude;

    private Integer count;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    //setter
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer   count) {
        this.count = count;
    }

    public passRecord mapFrom(Result result) {
     //   String[] rowKey = Bytes.toString(result.getRow()).split(" ");

        setPlaceId(Bytes.toString(result.getRow()));

        List<Cell> cellList = result.listCells();
        for (Cell cell : cellList) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
           // String value = Bytes.toString(CellUtil.cloneValue(cell));
         byte[] b= CellUtil.cloneValue(cell);
            switch (qualifier) {
                case "address":
                    setAddress(Bytes.toString(b));
                    break;
                case "longitude":
                    setLongitude(Bytes.toString(b));
                    break;
                case "latitude":
                    setLatitude(Bytes.toString(b));
                    break;
                case "count":
                    setCount(Bytes.toInt(b));
                    break;
            }

        }
        return this;
    }
}








