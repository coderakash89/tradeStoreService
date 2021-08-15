package com.deutsche.tradeStoreService.tradestore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name="Trade")
public class Trade {
    @Id
    private String tradeId;

    @Column(name="version")
    private Integer version;

    @Column(name="counterPartyId")
    private String counterPartyId;

    @Column(name="bookId")
    private String bookId;

    @Column(name="maturityDate")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date maturityDate;

    @Column(name="createdDate")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date createdDate;

    @Column(name="expired")
    private boolean expired;

}
