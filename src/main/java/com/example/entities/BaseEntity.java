package com.example.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    public Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    public Date updatedDate;
}
