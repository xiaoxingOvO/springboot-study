package cn.yematech.storm.service.words;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * <h1> WordCountSpout </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/15
 */
public class WordCountSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private static final String[] words = new String[]{"aaa","bbb","ccc","ddd","eee","fff"};

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        //初始化随机函数
        Random random = new Random();
        //随机从words数组里面取出数
        String word = words[random.nextInt(words.length)];
        //发射随机得到的单词
        this.collector.emit(new Values(word));
        //打印发射的内容
        System.out.println("emit: " + word);
        //每发射一个睡一会
        Utils.sleep(1000);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
