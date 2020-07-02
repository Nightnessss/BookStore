package com.fehead.needle.security.properties;

import lombok.Data;

/**
 * 写代码 敲快乐
 * だからよ...止まるんじゃねぇぞ
 * ▏n
 * █▏　､⺍
 * █▏ ⺰ʷʷｨ
 * █◣▄██◣
 * ◥██████▋
 * 　◥████ █▎
 * 　　███▉ █▎
 * 　◢████◣⌠ₘ℩
 * 　　██◥█◣\≫
 * 　　██　◥█◣
 * 　　█▉　　█▊
 * 　　█▊　　█▊
 * 　　█▊　　█▋
 * 　　 █▏　　█▙
 * 　　 █
 *
 * @author Nightnessss 2019/7/16 21:01
 */
@Data
public class BrowserProperties {
    private String loginPage = "/signIn.html";

    private String formLoginUrl = "/user/login";

    private String otpLoginUrl = "/loginByOtp";

    private String sendOtpCode = "/send/otpCode";

    private String telParameter = "tel";

    private String codeParameter = "code";

    private String jwtKey="fehead";


}
