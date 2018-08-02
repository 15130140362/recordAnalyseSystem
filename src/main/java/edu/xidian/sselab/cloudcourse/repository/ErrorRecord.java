package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.domain.MeetRecord;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ErrorRecord {
    private static final String TABLE_NAME = "Error_1";
    private static final String FAMILY_NAME = "info";
    private final HbaseClient hbaseClient;

    @Autowired
    public ErrorRecord(HbaseClient hbaseClient) {
       this.hbaseClient = hbaseClient;
    }
    public List<Record> findAllByRecord() throws IOException {
        List<Record> resultRecords = new ArrayList<>();
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        Scan scan = new Scan();
        ResultScanner rs;
        try {
            if (table != null) {
                rs = table.getScanner(scan);//封装scan中的
                for (Result result : rs) {
                    Record tempRecord = new Record().mapFrom(result);
                    resultRecords.add(tempRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("查询出错，返回一个空的列表!");
        }
        return resultRecords;
    }

}
