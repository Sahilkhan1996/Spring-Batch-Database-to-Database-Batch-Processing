package com.sahil.DbToDbBatchProcessing.DbToDbBatchProcessing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dob;
    private Integer semester;
    private Double percentage;
    @OneToMany(mappedBy = "examResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamMarks> examMarksList;

    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", dob=" + dob +
                ", semester=" + semester +
                ", percentage=" + percentage +
                ", examMarksList=" + examMarksList +
                '}';
    }
}
