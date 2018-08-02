package edu.xidian.sselab.cloudcourse.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;


@Data                     //注解在类上，为类提供读写属性
@ToString
public class MeetRecord {

    private String eid1;
    private Integer count;
    private String eid2;

    //getter
    public String getEid1() {
        // System.out.println("getEid1"+eid1);
        return eid1;
    }

    public void setEid1(String eid1) {
        // System.out.println("setEid1"+eid1);
        this.eid1 = eid1;
    }

    public Integer getCount() {
        // System.out.println("getcount"+eid1);
        return count;
    }

    public void setCount(Integer count) {
        //   System.out.println("setcount"+count);

        this.count = count;
    }

    public String getEid2() {
        //  System.out.println("geteid2"+eid2);
        return eid2;
    }

    //setter
    public void setEid2(String eid2) {
        //   System.out.println("seteid2"+eid2);
        this.eid2 = eid2;
    }

    public MeetRecord mapFrom(Result result) {
        String[] rowKey = Bytes.toString(result.getRow()).split("##");
        setEid1(rowKey[0]);
        setEid2(rowKey[1]);
        //得到行键

        List<Cell> cellList = result.listCells();
        for (Cell cell : cellList) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            // String value = Bytes.toString(CellUtil.cloneValue(cell));
            byte[] b = CellUtil.cloneValue(cell);
            switch (qualifier) {
                case "count":
                    setCount(Bytes.toInt(b));
                    break;

            }
        }
        return this;
    }

}
