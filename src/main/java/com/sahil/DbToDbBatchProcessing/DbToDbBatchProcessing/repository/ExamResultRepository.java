package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.repository;


import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {

}
