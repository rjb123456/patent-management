package com.sxf.result;




/**
 * @author huang_qh@suixingpay.com
 */
public class CodeMsg {

    private int code;
	private String msg;
	
	//通用异常
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	public static CodeMsg EXCEPTION_ERROR = new CodeMsg(500104, "后端发生异常：%s");
	public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求违法");
	public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500103, "访问太频繁");


	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(5000210,"Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(5000211,"密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(5000212,"账号不能为空");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(5000214,"该账号不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(5000215,"密码错误");
	public static CodeMsg MOBILE_EXISTED = new CodeMsg(5000216,"账号已经存在");
	public static CodeMsg REGISTER_SUCCESS = new CodeMsg(5000217,"注册成功");

	//文件模块 5003XX
	public static CodeMsg UPLOAD_FAILED = new CodeMsg(5000317,"文件上传失败");
	public static CodeMsg DOWNLOAD_FAILED = new CodeMsg(5000318,"文件下载失败");
	public static CodeMsg SECOND_CHECK_FAILED = new CodeMsg(5000318,"交底书上传未通过，请重新上传");

	//对表操作模块 5004XX
	public static CodeMsg CHANGE_CS_FAILED = new CodeMsg(5000417,"对专利状态表操作失败");
	public static CodeMsg CHANGE_CI_FAILED = new CodeMsg(5000417,"对专利信息表操作失败");

	//登录模块 5005XX
	public static CodeMsg IS_NOT_ADMIN = new CodeMsg(5000500,"非管理员无法操作");

	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}


	public CodeMsg fillArgs(Object...args){
		int code = this.code;
		String message = String.format(this.msg,args);
		return new CodeMsg(code,message);
	}

	@Override
	public String toString() {
		return "CodeMsg{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';
	}
}
