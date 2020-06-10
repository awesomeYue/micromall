package com.mmall.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本的entity
 *
 * @author suny
 */
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -6718838800112233445L;

  @Setter private long id = -1L;

  @Setter @Getter private Date createTime;

  @Setter @Getter private Date updateTime;

  public long getId() {
    if (id == -1L) {
      SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(512);
      id = snowflakeIdWorker.nextId();
    }
    return id;
  }
}
