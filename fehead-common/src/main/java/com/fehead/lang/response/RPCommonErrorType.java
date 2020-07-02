package com.fehead.lang.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author lmwis
 * @Date 2019-11-16 21:11
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RPCommonErrorType  extends FeheadResponse{

    private ErrorMsgType data;

    private String status;

}
