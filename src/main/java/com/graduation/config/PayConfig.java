package com.graduation.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayConfig {
    // 请填写您的AppId，例如：2019091767145019（必填）
    private static final String appID = "2021000119671168";
    //应用私钥，这里修改生成的私钥即可（必填）
    private static final String privateKey ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCyJPezJXgm8+uetrZoO010MXT4B7dvfe9PNqP/yv1ITu9NPWBrpPcoqfjjPMOXcm1UATia38ewcnqcKVOYD+e/n2fedVJbh37wP5KAlz6TWbNMhwmXtE0BIH7bROOWBDbBwNsfUdZ1xbqpTrxacaIxPKUh2Nxne5zdUhsOFahUnKaPbRxktaamDFy6el6/Xm1XjdBoW6ndb7Q/secwEKOMQO3XTaKOUIST1fMQgb0dojwNtykx1TzUASeLtF5MH+mKR4VMDKg8/N2D5wkDJevI1q5EspHg9BTehwZKhARadBz3CoeZxbg62oI+Cebycd6VcDoAnrsyLTfP4nTLDffBAgMBAAECggEANkhxehNW03fMOmaumoBHj7pqTBH94WlcfKTHwHAYUepLnRuwv9xau/dfMc8YQpPJoKlmmDrMXIXluqQVJubh1VA7lFvnpqBZG9gjSi/MD5Zrvkv/rkxwkoFWZnZNay8JiGy92INVeDmyMVkep+isZZh3MSMlthrCp+YW/Gm5C17WGUGCMjO2+Ji6GAPeOBJ+1P1gbWE57f9SbF/Ac+gNrx2TKDSTsHgHv8PVoJ+w9Obn1eD9Nw92Dixp00KrCZRo+VHsTRFEXmnxu+sEPyrWNW1SBUx76I/BOUA/UYgaNCjQs8ILgi91sLf07b6R0pTCBPusp/A0oYkqUhVkwEnwqQKBgQDl9NH+YHR4iwiX7bT0Z1rPnlHa4cd96B5Xl3dqu9ni8WeDndwFkwguJ3Q64sJu4L8B9q3UfKNsroGYt90q11VS6rIJm12ACET8eBYhu6SBkZkYWSsOkAFaNnc/R8snQP5VCrmBdzJ4pn6dHKEP9GV3UP/Sf7ehdkQbg1Vrmz5MEwKBgQDGUfOaQ6jfZDdlUBDxfCEqaIwcxLsZiL+gp3uSU/RG73c3Vbn3Xo7q32azeyIE4OyZ5A5+7wa55aB6S5dvMM4lIBkXGQnL1awpEPxBhjqcByYbnd0uY2Lzj5v8n5x8O+e1C71bGROYmA4wfFsGt2g0/l+DYKN1f1RbqUn0eZb/WwKBgQCB23Puk2eSXuk2ao0Ca48zqXMOCOwP5NGloypTgstzReGvyKhkGXjIH42aZ01nMdGLeVMGqG/dQtp6dHIX7EwJq/FoTW1KjODwNZxB5mXqqV7sCgavjZDF8fX7LT7C5wf43DqPqtE8osDjVjR5/wiBjaM5qtf2/QNPCiZlwuA2MwKBgQDEh1fhiqUh410mEAcKV5lCPmKC684ByssjeHVzl7DeTtYURubIJbN5O7O5rw8SSJcPFbnrvhErTUwPhTNnY7UVg0553j0f+vnlpEhm4o7YKhlwiqsyGhloBettl/pp/q9qcVzPlvUdZanCEKnf6QbiZSXfunehq3G8d/GqzId/OwKBgBxGLhHbWAEn33FCcCQVt6liWhqMuAhLyMvwpWJiYT/6x1xM3zylC6/KMcxtrA1oCo6yV+Y035Tkev4F3UDtI9vH1z8lPN/N4oCRPNG13N4GV3AdiMAQ9h7d6xFUaG1MH4Et5qc/9gaqvgXNRl7LcsaOlhAwtrHUzKwCxtXCbJDk";
    //支付宝公钥，而非应用公钥（必填）
    public static final String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9NfA+RWpEBiBk8bgqzIMYlYA6jprKoAfRCTRDxUCzNh/mm/0z22QsMhDOFkqFFJQekRznR0CJwddIYbJ+osteox7wMcA5D5jQCLRjBlRmOdAa/GJoNG1UBe3EzSw2g7ojtI0/IZ6QosIF6No1gX0n061I2dSti8skqZFeDe4bnF6BwQuYc0XbGt9O0GibMa+44SYnGmao6GLX9McSKJ/9nkT4VNduXoTc4YBrQ3jNe1vO9N6zCE6cE4unS/o4nvwgzcZ8mIuLddW1iRaSUzEYmugZCxIXfAGUKuVzaFDU1kFQgWxMLdrpTDB/MKk2jGSfCvlnlJ3cZLZ7kaV9FYv8wIDAQAB";
    //默认即可（必填）
    public static final String charset = "utf-8";
    //默认即可（必填）
    public static final String signType = "RSA2";
    @Bean
    public AlipayClient alipayClient(){
        //沙箱环境使用https://openapi.alipaydev.com/gateway.do，线上环境使用https://openapi.alipay.com/gateway.do
        return new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", appID, privateKey, "json", charset, publicKey, signType);
    }
    /**
     * 验签，是否正确
     */
    public static boolean checkSign(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(key +"===>"+strs);
            paramsMap.put(key, strs);
        });
        System.out.println();
        //调用SDK验证签名
        try {
            return  AlipaySignature.rsaCheckV1(paramsMap, PayConfig.publicKey, PayConfig.charset, PayConfig.signType);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("*********************验签失败********************");
            return false;
        }

    }
}