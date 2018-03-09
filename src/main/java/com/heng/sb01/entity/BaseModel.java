package com.heng.sb01.entity;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import io.ebean.config.CurrentUserProvider;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Created by hengfeihu on 2017/7/25.
 */
@MappedSuperclass
public class BaseModel extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @CreatedTimestamp
    public Timestamp createTime;
    @UpdatedTimestamp
    public Timestamp updateTime;

    @WhoCreated
    public Integer createEmpid;
    @WhoModified
    public Integer updateEmpid;

    public static class UserProvider implements CurrentUserProvider {
        @Override
        public Integer currentUser() {
            return -1;
        }
    }

    protected static <ID, T extends Model> Finder<ID, T> _getFinder(String server) {
        throw new NotImplementedException("Model not enhanced!");
    }

    public static <ID, T extends Model> Finder<ID, T> withFinder(String server) {
        Finder<ID, T> finder = _getFinder(server);
        if (finder == null) {
            throw new NullPointerException();
        }
        return finder;
    }

    public static <ID, T extends Model> Finder withFinder() {
        return withFinder(Ebean.getDefaultServer().getName());
    }
}
