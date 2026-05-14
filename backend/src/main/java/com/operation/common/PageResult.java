package com.operation.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends Result<List<T>> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long total;
    private Long pageNum;
    private Long pageSize;
    private Long pages;

    public PageResult() {
    }

    public static <T> PageResult<T> success(List<T> data, Long total, Long pageNum, Long pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMessage("查询成功");
        result.setData(data);
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages((total + pageSize - 1) / pageSize);
        return result;
    }

    public static <T> PageResult<T> success(List<T> data, Long total) {
        return success(data, total, 1L, (long) data.size());
    }
}
