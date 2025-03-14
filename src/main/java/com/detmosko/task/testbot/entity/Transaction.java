package com.detmosko.task.testbot.entity;

import com.detmosko.task.testbot.TRANSACTION_TYPES;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TRANSACTIONS")
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USERNAME")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TRANSACTION_TYPES type;

    @Column(name = "DATE")
    private String date;

    @Column(name = "TIME")
    private String time;


    //без питань, в цьому проєкті ломбок відмовляється працювати(при компіляції не бачить геттери/сеттери), часу чинити нема :(
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(TRANSACTION_TYPES type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
