package com.deutsche.tradeStoreService.tradestore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name="Trade")
@IdClass(TradeIdCompositeKey.class)
public class  Trade {

    @Id
    @Column(name="tradeId")
    private String tradeId;

    @Id
    @Column(name="version")
    private Integer version;

    @Column(name="counterPartyId")
    private String counterPartyId;

    @Column(name="bookId")
    private String bookId;

    @Column(name="maturityDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern="dd/MM/yyyy")
    private Date maturityDate;

    @Column(name="createdDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date createdDate;

    @Column(name="expired")
    private Boolean expired;

}
