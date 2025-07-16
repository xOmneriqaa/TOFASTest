package com.tofas.yky.model.losses.qualitylab;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_QUALITY_LAB_LOSSES_TESTS")
public class QualityLabLossTest extends AbstractQualityLabLossTest {

    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    @JsonIgnore
    private QualityLabLoss loss;


    @ManyToOne
    @JoinColumn(name = "QUALITY_LAB_TEST_ID")
    private QualityLabTest qualityLabTest;


    public QualityLabLoss getLoss() {
        return loss;
    }

    public void setLoss(QualityLabLoss loss) {
        this.loss = loss;
    }

    public QualityLabTest getQualityLabTest() {
        return qualityLabTest;
    }

    public void setQualityLabTest(QualityLabTest qualityLabTest) {
        this.qualityLabTest = qualityLabTest;
    }


    @Override
    public String toString() {
        return "QualityLabLossTest{" +
                "id=" + id +
                ", qualityLabTest=" + qualityLabTest +
                ", qty=" + qty +
                ", qualityTestNameCached='" + qualityTestNameCached + '\'' +
                ", qualityTestPriceCached='" + qualityTestPriceCached + '\'' +
                '}';
    }


}
