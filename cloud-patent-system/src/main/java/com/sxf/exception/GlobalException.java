package com.sxf.exception;


import com.sxf.result.CodeMsg;


/**
 *  @author huang_qh@suixingpay.com
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
