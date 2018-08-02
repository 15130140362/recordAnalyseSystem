package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import edu.xidian.sselab.cloudcourse.hbase.HbaseProperties;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;

public class VehivlePass {
    private static final String TABLE_NAME = "MeetCount";

    private static final String FAMILY_NAME = "info";
    private final HbaseClient hbaseClient;

    public VehivlePass(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    public static void main(String[] args) throws IOException {
        HbaseClient cli=new HbaseClient(new HbaseProperties());
        VehivlePass v = new VehivlePass(cli);
        v.scan();
    }

    public void scan() throws IOException {
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        Iterator<org.apache.hadoop.hbase.client.Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            org.apache.hadoop.hbase.client.Result result = iterator.next();
            System.out.println(Bytes.toString(result.getRow()));
            byte[] by1 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("count"));
        //    byte[] by2 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("count"));
          //  byte[] by3 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("latitude"));
            byte[] by4 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("eid2"));
            System.out.println("Count:  " + Bytes.toInt(by1));
           /// System.out.println("count:  " + Bytes.toInt(by2));
            //System.out.println("latitude:  " + Bytes.toString(by3));
            System.out.println("eid2:  " + Bytes.toString(by4));

        }
    }

}
