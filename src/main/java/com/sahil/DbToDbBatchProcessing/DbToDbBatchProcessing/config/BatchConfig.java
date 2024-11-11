package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.config;

import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.listener.JobMonitoringListener;
import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model.ExamResult;
import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.processor.ExamResultItemProcessor;
import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.repository.ExamMarksRepository;
import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.repository.ExamResultRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jbFactory;
    @Autowired
    private StepBuilderFactory sbFactory;
    @Autowired
    private JobMonitoringListener listener;
    @Autowired
    private ExamResultItemProcessor processor;
    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private ExamMarksRepository examMarksRepository;

    @Autowired
    private DataSource ds;

    @Bean
    public JobMonitoringListener createlistener() {
        return new JobMonitoringListener();
    }


//    @Bean
//    public FlatFileItemWriter<ExamResult> createWriter() {
//        FlatFileItemWriter<ExamResult> writer = new FlatFileItemWriter<ExamResult>();
//        //specify the location of destination
//        writer.setResource(new FileSystemResource("E:\\csv"));
//        //create Field Extractor Obj
//        BeanWrapperFieldExtractor<ExamResult> extractor = new BeanWrapperFieldExtractor<>();
//        extractor.setNames(new String[]{"id", "dob", "semester", "percentage"});
//        //create Line Aggregator that builds the line having model class obj data
//        DelimitedLineAggregator<ExamResult> lineAggregator = new DelimitedLineAggregator<>();
//        lineAggregator.setDelimiter(",");
//        lineAggregator.setFieldExtractor(extractor);
//        //set line aggregator to writer obj
//        writer.setLineAggregator(lineAggregator);
//        return writer;
//    }


    @Bean
    public RepositoryItemReader<ExamResult> createReader() {
        RepositoryItemReader<ExamResult> reader = new RepositoryItemReader<>();
        try {

            //specify the repository to use for fetching data
            reader.setRepository(examResultRepository);
            //define a method use to retrieve data
            reader.setMethodName("findAll");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("pageable", PageRequest.of(0, 1000, Sort.by(Sort.Order.asc("id"))));  // Sort by 'id'

            // Set the page size for fetching data
            reader.setPageSize(1000);
            // Create a map for sorting: e.g., sort by 'id' in ascending order
            Map<String, Sort.Direction> sortMap = new HashMap<>();
            sortMap.put("id", Sort.Direction.ASC);  // Sort by 'id' in ascending order
            // Set the sort order in the reader
            reader.setSort(sortMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }


//    @Bean(name = "writer")
//    public ItemWriter<ExamResult> createWriter(ExamResult examResult) {
//       examResult.setId(examResult.getId()+100000);
//        return new JdbcBatchItemWriterBuilder<ExamResult>().dataSource(ds).sql("INSERT INTO EXAM_RESULT VALUES(:id,:dob,:semester,:percentage)").beanMapped().build();
//
//    }

    @Bean
    public ItemWriter<ExamResult> createWriter() {

        RepositoryItemWriter<ExamResult> writer = new RepositoryItemWriter<>();
        writer.setRepository(examResultRepository);  // Set the repository to save the data
        writer.setMethodName("save");  // 'save' method will handle both insert and update
        return writer;
    }

    @Bean
    public ItemProcessor<ExamResult, ExamResult> createProcessor() {
        return new ExamResultItemProcessor();
    }


    //step creation
    @Bean(name = "step1")
    public Step createStep1() {
        return sbFactory.get("step1").<ExamResult, ExamResult>chunk(1000).reader(createReader()).writer(createWriter()).processor(createProcessor()).build();
    }

    @Bean(name = "job1")
    public Job createJob() {
        return jbFactory.get("job1").incrementer(new RunIdIncrementer()).listener(listener).start(createStep1()).build();
    }


}
