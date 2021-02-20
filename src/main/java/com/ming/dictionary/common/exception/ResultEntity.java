package com.ming.dictionary.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultEntity<T> {
    private int code;
    private String msg;
    private T data;
}
