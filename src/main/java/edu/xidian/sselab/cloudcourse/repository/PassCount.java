package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.domain.passRecord;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.domain.passRecord;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class PassCount {
    private static final String TABLE_NAME = "VehicleCount";
    private static final String FAMILY_NAME = "info";
    private final HbaseClient hbaseClient;
    @Autowired
    public PassCount(HbaseClient hbaseClient){this.hbaseClient=hbaseClient;}
    public List<passRecord> findAllByRecord(passRecord record) throws IOException {
        List<passRecord> resultRecords = new ArrayList<>();
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.47.100:2181,192.168.47.101:2181");
        HTable table = new HTable(conf, "VehicleCount");
        Get getRow = new Get(Bytes.toBytes(record.getPlaceId()));
        //ystem.out.println("key: "+record.getPlaceId());
        Result result = table.get(getRow);
        byte[] s = result.getRow();
        System.out.println("key" + Bytes.toString(s));
        passRecord temp = new passRecord().mapFrom(result);
        resultRecords.add(temp);
        return resultRecords;
    }


    public boolean save(List<passRecord> allRecords){
        List<Put> allPuts = new ArrayList<>();
        for (passRecord record : allRecords) {
            // 1.确定行键 （placeID）
            String rowKey = String.valueOf(record.getPlaceId());
            Put put = new Put(Bytes.toBytes(rowKey));
            // 2.添加列
            put.addColumn( Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("placeId"),
                    Bytes.toBytes(record.getPlaceId()));
            put.addColumn(
                    Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("address"),
                    Bytes.toBytes(record.getAddress()));

            put.addColumn(
                    Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("longitude"),
                    Bytes.toBytes(String.valueOf(record.getLongitude())));

            put.addColumn(
                    Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("latitude"),
                    Bytes.toBytes(String.valueOf(record.getLatitude())));
            put.addColumn(
                    Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("count"),
                    Bytes.toBytes(String.valueOf(record.getCount())));
            allPuts.add(put);
        }

        // 3.执行数据库插入操作
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        try {
            if (table != null) {
                table.put(allPuts);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}




