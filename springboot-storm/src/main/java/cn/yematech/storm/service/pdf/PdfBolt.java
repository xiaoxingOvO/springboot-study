package cn.yematech.storm.service.pdf;

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

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1> PdfBolt </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/16
 */
public class PdfBolt extends BaseRichBolt {

    private OutputCollector collector;
    Map<String,Integer> map = new HashMap<String,Integer>();
    private JdbcClient jdbcClient;
    private ConnectionProvider connectionProvider;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;

        Map hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", "jdbc:mysql://localhost:3307/storm");
        hikariConfigMap.put("dataSource.user","root");
        hikariConfigMap.put("dataSource.password","root");
        connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

        connectionProvider.prepare();
        jdbcClient = new JdbcClient(connectionProvider,30);
    }

    @Override
    public void execute(Tuple tuple) {
        String location = tuple.getStringByField("location");
        String name = tuple.getStringByField("name");
        String ext = tuple.getStringByField("ext");
        String size = tuple.getStringByField("size");

//        //查询该word是否存在
//        List<Column> list = new ArrayList<Column>();
//        //创建一列将值传入   列名  值  值的类型
//        list.add(new Column("location",location, Types.VARCHAR));
//        List<List<Column>> select = jdbcClient.select("select location from picfile where location = ?",list);
//        //计算出查询的条数
//        Long n = select.stream().count();
//        if(n>=1){
//            //update
//            jdbcClient.executeSql("update picfile set name = '"+name+"',ext='"+ext+"',size='"+size+"', where location = '"+location+"'");
//        }else{
//            //insert
//            jdbcClient.executeSql("insert into picfile values( '"+location+"','"+name+"','"+ext+"','"+size+"')");
//        }



        Connection connection = connectionProvider.getConnection();
        PreparedStatement ps = null;
        ResultSet result=null;
        //查询是否存在
        try {
            String sql="select * from picfile where location = ?";
            ps=connection.prepareStatement(sql);
            ps.setString(1,location);
            result = ps.executeQuery();
            System.out.println("查询成功！");
            System.out.println("result.isBeforeFirst() = " + result.isBeforeFirst());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更新或者新增
        try {
            if(result.isBeforeFirst()){
                System.out.println("执行更新操作！");
                String sql ="update picfile set name=?,ext=?,size=?"+"where location=?";;
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,name);
                    ps.setString(2,ext);
                    ps.setString(3,size);
                    ps.setString(4,location);
                    int a=ps.executeUpdate();
                    if(a>0){
                        System.out.println("修改成功，该记录是："+location);
                    }else{
                        System.out.println("修改失败");
                    }
                }finally{
                    try{
                        if(ps!=null){
                            ps.close();
                        }if(connection!=null){
                            connection.close();
                        }
                    }catch(Exception e2){
                        e2.printStackTrace();
                    }
                }

            }else{
                System.out.println("执行添加操作！");
                String sql = "insert into picfile values(?,?,?,?)";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,location);
                    ps.setString(2,name);
                    ps.setString(3,ext);
                    ps.setString(4,size);
                    int a=ps.executeUpdate();
                    if(a>0){
                        System.out.println("添加成功，该记录是："+location);
                    }else{
                        System.out.println("添加失败");
                    }
                } finally{
                    try{
                        if(ps!=null){
                            ps.close();
                        }if(connection!=null){
                            connection.close();
                        }
                    }catch(Exception e2){
                        e2.printStackTrace();
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("location","name","ext","size"));
    }
}
