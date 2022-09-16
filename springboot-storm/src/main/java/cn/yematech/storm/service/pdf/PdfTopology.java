package cn.yematech.storm.service.pdf;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * <h1> PdfTopology </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/16
 */
public class PdfTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("PdfSpout",new PdfSpout());
        builder.setBolt("PdfBolt",new PdfBolt()).shuffleGrouping("PdfSpout");

        Config config = new Config();
        config.setNumWorkers(3);

        //创建本地集群
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("PdfTopology",config, builder.createTopology());


    }

}
