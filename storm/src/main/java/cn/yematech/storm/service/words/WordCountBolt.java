package cn.yematech.storm.service.words;

import com.google.common.collect.Maps;
import org.apache.storm.jdbc.common.Column;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.common.JdbcClient;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1> WordCountBolt </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/15
 */
public class WordCountBolt extends BaseRichBolt {

    private OutputCollector collector;
    Map<String,Integer> map = new HashMap<String,Integer>();
    private JdbcClient jdbcClient;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;

        Map hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", "jdbc:mysql://localhost:3307/storm");
        hikariConfigMap.put("dataSource.user","root");
        hikariConfigMap.put("dataSource.password","root");
        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

        connectionProvider.prepare();
        jdbcClient = new JdbcClient(connectionProvider,30);
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Integer count = map.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        map.put(word, count);


        //查询该word是否存在
        List<Column> list = new ArrayList<Column>();
        //创建一列将值传入   列名  值  值的类型
        list.add(new Column("word",word, Types.VARCHAR));
        List<List<Column>> select = jdbcClient.select("select word from wc where word = ?",list);
        //计算出查询的条数
        Long n = select.stream().count();
        if(n>=1){
            //update
            jdbcClient.executeSql("update wc set word_count = "+map.get(word)+" where word = '"+word+"'");
        }else{
            //insert
            jdbcClient.executeSql("insert into wc values( '"+word+"',"+map.get(word)+")");
        }

//        this.collector.emit(new Values(word,map.get(word)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word","word_count"));
    }
}
