package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.processor;

import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model.ExamResult;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

    @Override
    public ExamResult process(ExamResult item) throws Exception {
        // System.out.println(item.toString());
        if (item.getPercentage() >= 90.0F) {
            //item.setId(null);
            item.setPercentage(101D);
            return item;
        } else {
            return null;
        }

    }
}
