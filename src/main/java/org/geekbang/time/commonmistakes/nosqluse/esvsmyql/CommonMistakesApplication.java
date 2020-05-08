package org.geekbang.time.commonmistakes.nosqluse.esvsmyql;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.geekbang.time.commonmistakes.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = NewsESRepository.class))
//明确设置哪个是ES的Repository
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = NewsESRepository.class))
//其它的是MySQL的Repository
public class CommonMistakesApplication {


    @Autowired
    private StandardEnvironment standardEnvironment;
    @Autowired
    private NewsESRepository newsESRepository;
    @Autowired
    private NewsMySQLRepository newsMySQLRepository;

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "es.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @PostConstruct
    public void init() {
        //使用-Dspring.profiles.active=init启动程序进行初始化
        if (Arrays.stream(standardEnvironment.getActiveProfiles()).anyMatch(s -> s.equalsIgnoreCase("init"))) {
            //csv中的原始数据只有4000条
            List<News> news = loadData();
            AtomicLong atomicLong = new AtomicLong();
            news.forEach(item -> item.setTitle("%%" + item.getTitle()));
            //我们模拟100倍的数据量，也就是40万条
            IntStream.rangeClosed(1, 100).forEach(repeat -> {
                news.forEach(item -> {
                    //重新设置主键ID
                    item.setId(atomicLong.incrementAndGet());
                    //每次复制数据稍微改一下title字段，在前面加上一个数字，代表这是第几次复制
                    item.setTitle(item.getTitle().replaceFirst("%%", String.valueOf(repeat)));
                });
                initMySQL(news, repeat == 1);
                log.info("init MySQL finished for {}", repeat);
                initES(news, repeat == 1);
                log.info("init ES finished for {}", repeat);
            });

        }
    }

    //从news.csv中解析得到原始数据
    private List<News> loadData() {
        //使用jackson-dataformat-csv实现csv到POJO的转换
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader objectReader = csvMapper.readerFor(News.class).with(schema);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("news.csv").getFile());
        try (Reader reader = new FileReader(file)) {
            return objectReader.<News>readValues(reader).readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //把数据保存到ES中
    private void initES(List<News> news, boolean clear) {
        if (clear) {
            //首次调用的时候先删除历史数据
            newsESRepository.deleteAll();
        }
        newsESRepository.saveAll(news);
    }

    //把数据保存到MySQL中
    private void initMySQL(List<News> news, boolean clear) {
        if (clear) {
            //首次调用的时候先删除历史数据
            newsMySQLRepository.deleteAll();
        }
        newsMySQLRepository.saveAll(news);
    }
}

