package com.enlinkmob.ucenterapi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zhaowy on 15/5/19.
 */
@Document(collection = "sequence")
public class Sequence {

    @Id
    private ObjectId _id;

    private Long seq;
    @Indexed(unique = true, dropDups = true, background = true, direction = IndexDirection.ASCENDING, name = "className")
    private String className;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}