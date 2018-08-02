package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.domain.MeetRecord;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeetCount {
    private static final String TABLE_NAME = "MeetCount";
    private static final String FAMILY_NAME = "info";
    private final HbaseClient hbaseClient;

    @Autowired
    public MeetCount(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }
    public List<MeetRecord> findAllByRecord(MeetRecord record) throws IOException {
        List<MeetRecord> resultRecords = new ArrayList<>();
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.47.100:2181,192.168.47.101:2181");
        HTable table = new HTable(conf, "MeetCount");
        Get getRow = new Get(Bytes.toBytes(record.getEid1() + "##" + record.getEid2()));
        Result result = table.get(getRow);
        byte[] s = result.getRow();
        System.out.println("key" + Bytes.toString(s));
        MeetRecord temp = new MeetRecord().mapFrom(result);
        resultRecords.add(temp);
        return resultRecords;
    }

    public boolean save(List<MeetRecord> allRecords) {
        List<Put> allput = new ArrayList<>();
        for (MeetRecord record : allRecords) {
            String rowkey = String.valueOf(record.getEid1());
            Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("count"),
                    Bytes.toBytes(record.getCount()));
            put.addColumn(Bytes.toBytes(FAMILY_NAME),
                    Bytes.toBytes("eid2"),
                    Bytes.toBytes(record.getEid2()));
            allput.add(put);
        }

        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        try {
            if (table != null) {
                table.put(allput);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
