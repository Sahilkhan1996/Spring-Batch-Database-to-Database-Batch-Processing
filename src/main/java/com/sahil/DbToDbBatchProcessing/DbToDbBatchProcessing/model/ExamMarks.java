package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExamMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_result_id")
    private ExamResult examResult;
    private String subject;
    private Integer marks;

    @Override
    public String toString() {
        return "ExamMarks{" +
                "id=" + id +
                ", examResult=" + examResult +
                ", subject='" + subject + '\'' +
                ", marks=" + marks +
                '}';
    }
}
