package cn.yematech.storm.service.words;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.Column;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.common.JdbcClient;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.apache.storm.topology.TopologyBuilder;

import java.util.List;
import java.util.Map;

/**
 * <h1> WordCountTopology </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/15
 */
public class WordCountTopology {

    public static void main(String[] args) throws Exception {

        //通过TopologyBuilder根据Spout和Bolt构建Topolog
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("WordCountSpout",new WordCountSpout());
        builder.setBolt("WordCountBolt",new WordCountBolt()).shuffleGrouping("WordCountSpout");

//        Map hikariConfigMap = Maps.newHashMap();
//        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
//        hikariConfigMap.put("dataSource.url", "jdbc:mysql://localhost:3307/storm");
//        hikariConfigMap.put("dataSource.user","root");
//        hikariConfigMap.put("dataSource.password","root");
//        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);
//
//        String tableName = "wc";
//        List<Column> columnSchema = Lists.newArrayList(
//                new Column("word", java.sql.Types.VARCHAR),
//                new Column("word_count", java.sql.Types.INTEGER));
//        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(columnSchema);
//
//        JdbcInsertBolt PersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
//                .withInsertQuery(" insert into wc(word,word_count) values (?,?) ")
//                .withQueryTimeoutSecs(30);
//
//        builder.setBolt("JdbcInsertBolt",PersistanceBolt).shuffleGrouping("WordCountBolt");

        Config config = new Config();
        config.setNumWorkers(3);

        //创建本地集群
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("LocalWordCountJDBCStormTopology",config, builder.createTopology());

    }

}
