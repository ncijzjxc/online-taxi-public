package com.mz.Controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.mz.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: mz
 * @Date: 2025/10/25 - 10 - 25 - 17:25
 * @Description: com.mz.Controller
 * @version: 6.0
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {
    
    @GetMapping("/pay")
    public String pay(@RequestParam String subject, 
                     @RequestParam String outTradeNo, 
                     @RequestParam String totalAmount) {
        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page().pay(subject,outTradeNo,totalAmount,"");
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "支付失败: " + e.getMessage();
        }
    }
    @Autowired
    AlipayService alipayService;
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {
        System.out.println("支付宝回调");
        String tradeStatus = request.getParameter("trade_status");
        if(tradeStatus.trim().equals("TRADE_SUCCESS")){
            Map<String ,String > param=new HashMap<>();
            Map<String, String[]> paramMap=request.getParameterMap();
            for(String name:paramMap.keySet()){
                param.put(name,request.getParameter(name));
            }
            if(Factory.Payment.Common().verifyNotify(param)){
                System.out.println("通过支付宝验证");
                String orderId = param.get("out_trade_no");
                alipayService.alipay(orderId);
            }else
                System.out.println("支付宝验证不通过");


        }
        return "success";
    }
}
