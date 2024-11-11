package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.repository;


import com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model.ExamMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamMarksRepository extends JpaRepository<ExamMarks, Integer> {

}
