package edu.xidian.sselab.cloudcourse.hbase;

import org.apache.hadoop.conf.Configuration;

/**
 * Author: cwz
 * Time: 2017/9/20
 * Description: 设置hbase连接配置
 */
public class HbaseConf {
    // 配置hbase的主节点ip

    private final static String master = "192.168.47.100";
//    private final static String master = "192.168.1.149";
    // 配置hbase的zookeeper节点ip
    private final static String zookeeper = "192.168.47.100,192.168.47.101";
 //   private final static String zookeeper = "192.168.1.110,192.168.1.112,192.168.1.149";
    // 配置hbase的zookeeper连接端口，默认值为2181
    private final static String port = "2181";

    public static Configuration getConf() {
        Configuration hbaseConf = new Configuration();
        hbaseConf.set("hbase.master", master);
        hbaseConf.set("hbase.zookeeper.quorum", zookeeper);
        hbaseConf.set("hbase.zookeeper.property.clientPort", port);
        return hbaseConf;
    }

}
