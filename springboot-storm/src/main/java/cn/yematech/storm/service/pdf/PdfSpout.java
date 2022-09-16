package cn.yematech.storm.service.pdf;

import cn.yematech.storm.utils.PdfToImgUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1> PdfSpout </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/16
 */
public class PdfSpout extends BaseRichSpout {

    private SpoutOutputCollector spoutOutputCollector;
    private List<File> files;

    //监控目录路径
    private File target = new File("D:/pdf");

    //目录下原有的文件
    private Collection<File> cacheFiles = null;

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector=spoutOutputCollector;
    }

    @Override
    public void nextTuple() {

        Collection<File> listFiles = FileUtils.listFiles(target, FileFilterUtils.suffixFileFilter("pdf"), null);
        for (File file:listFiles) {
            //这里可以加一个if(!cacheFiles.contains(file))排除已发送过的
            String filepath = file.getAbsolutePath();
            //转换成图片
            files = PdfToImgUtil.pdfToImage(filepath, 100);

            for (File f:files) {
                //文件路径
                String location = f.getAbsolutePath().replace("\\","/");
                //文件名
                String name = f.getName().substring(0, f.getName().lastIndexOf("."));
                //扩展路径
                String ext = name.substring(name.lastIndexOf(".") + 1);
                //文件大小
                String size = String.valueOf(f.length());
                this.spoutOutputCollector.emit(new Values(location,name,ext,size));
                System.out.println("发送数据：location="+location);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("location","name","ext","size"));
    }
}
