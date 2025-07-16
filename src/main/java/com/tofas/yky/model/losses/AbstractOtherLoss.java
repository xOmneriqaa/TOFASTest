package com.tofas.yky.model.losses;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class AbstractOtherLoss {

	@Id
	@SequenceGenerator(name = "SEQ_YKY_LOSS_OTHER_COSTS", sequenceName = "SEQ_YKY_LOSS_OTHER_COSTS", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_YKY_LOSS_OTHER_COSTS", strategy = GenerationType.SEQUENCE)
	protected Long id;

	@Column(name = "COST")
	protected BigDecimal cost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getTotalCost() { //TODO 20 gelmeli deger 20 üzerine 20 ekliyor ve 40 dönüyor.
		return this.cost;
	}
}
