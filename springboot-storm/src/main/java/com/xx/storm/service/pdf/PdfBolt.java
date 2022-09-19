package com.xx.storm.service.pdf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.xx.storm.StormApplication;
import com.xx.storm.mapper.PicfileMapper;
import com.xx.storm.pojo.Picfile;
import com.xx.storm.service.PicfileService;
import com.xx.storm.utils.SpringBeanUtils;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.common.JdbcClient;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private static final Logger logger = LoggerFactory.getLogger(PdfBolt.class);

    private PicfileService picfileService;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        //启动spring容器
//        StormApplication.run();
        this.collector = outputCollector;
        picfileService = SpringBeanUtils.getBean(PicfileService.class);

    }

    @Override
    public void execute(Tuple tuple) {

        String location = tuple.getStringByField("location");
        String name = tuple.getStringByField("name");
        String ext = tuple.getStringByField("ext");
        String size = tuple.getStringByField("size");

        //封装对象
        Picfile picfile = new Picfile();
        picfile.setLocation(location);
        picfile.setName(name);
        picfile.setExt(ext);
        picfile.setSize(Long.valueOf(size));

        //查询是否存在
        QueryWrapper<Picfile> wrapper = new QueryWrapper<>();
        wrapper.eq("location",location);
        Picfile pic = picfileService.findByPicfile(picfile);

        logger.info("查到一条已存在数据:"+pic);

        //更新或者新增
        if(pic != null){
            logger.info("执行更新操作");
            picfileService.updatePicfile(picfile);
        }else {
            logger.info("执行插入操作");
            picfileService.insertPicfile(picfile);
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
