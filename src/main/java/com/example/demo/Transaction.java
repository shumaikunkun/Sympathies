package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long senderUserId;
    private Long destinationUserId;
    private Long goodsId;

    protected Transaction() {}

    public Transaction(Long senderUserId, Long destinationUserId, Long goodsId) {
        this.senderUserId = senderUserId;
        this.destinationUserId = destinationUserId;
        this.goodsId = goodsId;
    }

    public Long getId() { return id; }
    public Long getSenderUserId() { return senderUserId; }
    public Long getDestinationUserId() { return destinationUserId; }
    public Long getGoodsId() { return goodsId; }

    @Override
    public String toString() {
        return String.format("Transaction[id='%d', senderUserId='%d', destinationUserId='%d', goodsId='%d']" ,id ,senderUserId, destinationUserId, goodsId);
    }

}
