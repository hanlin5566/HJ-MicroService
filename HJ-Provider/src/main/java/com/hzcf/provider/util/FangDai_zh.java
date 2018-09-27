package com.hzcf.provider.util;

import java.text.DecimalFormat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Create by hanlin on 2018年8月7日
 **/
public class FangDai_zh {
	
	private static final DecimalFormat FORMAT = new DecimalFormat("#.00");
	 /**
     * 计算等额本息还款
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static JSONObject calculateEqualPrincipalAndInterest(double principal, int months, double rate) {
        JSONObject ret = new JSONObject();
        double monthRate = rate / (100 * 12);//月利率
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);//每月还款金额
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        ret.put("还款总额",FORMAT.format(totalMoney));//还款总额
        ret.put("贷款总额",FORMAT.format(principal));//贷款总额
        ret.put("还款总利息",FORMAT.format(interest));//还款总利息
        ret.put("每月还款金额",FORMAT.format(preLoan));//每月还款金额
        ret.put("还款期限",String.valueOf(months));//还款期限
        return ret;
    }
    
    /**
     * 计算等额本息还款详情
     * 等额本息还贷，先算每月还贷本息：BX=Mr(1+r)^N÷[(1+r)^N-1]
     * 等额本息还贷第n个月还贷本金为：B=Mr(1+r)^(n-1)÷[(1+r)^N-1]
     * 其中：
     * BX=等额本息还贷每月所还本金和利息总额，
     * B=等额本息还贷每月所还本金，
     * M=贷款总金额
     * r=贷款月利率（年利率除12），
     * N=还贷总期数（即总月数）
     * n=第n期还贷数
     * ^=乘方计算（即X^12=X的12次方）		
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static JSONObject calculateEqualPrincipalAndInterestDetail(double principal, int months, double rate) {
        JSONObject ret = new JSONObject();
        double monthRate = rate / (100 * 12);//月利率
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);//每月还款金额
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        ret.put("还款总额",FORMAT.format(totalMoney));//还款总额
        ret.put("贷款总额",FORMAT.format(principal));//贷款总额
        ret.put("还款总利息",FORMAT.format(interest));//还款总利息
        ret.put("每月还款金额",FORMAT.format(preLoan));//每月还款金额
        ret.put("还款期限",String.valueOf(months));//还款期限
        JSONArray detail = new JSONArray();
        double totalPrincipal = 0.0;//还款总本金
        double totalInterest = 0.0;//还款总利息
        for (int i = 1; i <= months; i++) {
			JSONObject eachMonth = new JSONObject();
			//每月还贷本金 B=Mr(1+r)^(n-1)÷[(1+r)^N-1]
			double eachPrincipal = (principal * monthRate * Math.pow((1 + monthRate), (i-1))) / (Math.pow((1 + monthRate), months) - 1);
			double eachInterest = preLoan - eachPrincipal;//每月偿还利息
			//还款总本金
			totalPrincipal+=eachPrincipal;
			totalInterest+=eachInterest;
			double remainPrincipal = principal - totalPrincipal;//剩余本金
			double remainInterest = interest - totalInterest;//剩余利息
			eachMonth.put("还款期数", i);
			eachMonth.put("剩余本金", FORMAT.format(remainPrincipal));
			eachMonth.put("剩余利息", FORMAT.format(remainInterest));
			eachMonth.put("已还总本金", FORMAT.format(totalPrincipal));
			eachMonth.put("已还总利息", FORMAT.format(totalInterest));
			eachMonth.put("每月本息", FORMAT.format(preLoan));
			eachMonth.put("每月本金", FORMAT.format(eachPrincipal));
			eachMonth.put("每月利息", FORMAT.format(eachInterest));
			detail.add(eachMonth);
		}
        ret.put("还款详情", detail);
        return ret;
    }
    
    
    /**
     * 计算等额本金还款
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static JSONObject calculateEqualPrincipal(double principal, int months, double rate) {
    	JSONObject ret = new JSONObject();
        double monthRate = rate / (100 * 12);//月利率
        double prePrincipal = principal / months;//每月还款本金
        double firstMonth = prePrincipal + principal * monthRate;//第一个月还款金额
        double decreaseMonth = prePrincipal * monthRate;//每月利息递减
        double interest = (months + 1) * principal * monthRate / 2;//还款总利息
        double totalMoney = principal + interest;//还款总额
        ret.put("还款总额",FORMAT.format(totalMoney));//还款总额
        ret.put("贷款总额",FORMAT.format(principal));//贷款总额
        ret.put("还款总利息",FORMAT.format(interest));//还款总利息
        ret.put("首月还款金额",FORMAT.format(firstMonth));//首月还款金额
        ret.put("每月递减利息",FORMAT.format(decreaseMonth));//每月递减利息
        ret.put("还款期限",String.valueOf(months));//还款期限
        return ret;
    }
    
    /**
     * 计算等额本金还款详情
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static JSONObject calculateEqualPrincipalDetail(double principal, int months, double rate) {
    	JSONObject ret = new JSONObject();
        double monthRate = rate / (100 * 12);//月利率
        double eachPrincipal = principal / months;//每月还款本金
        double firstMonth = eachPrincipal + principal * monthRate;//第一个月还款金额
        double decreaseMonth = eachPrincipal * monthRate;//每月利息递减
        double interest = (months + 1) * principal * monthRate / 2;//还款总利息
        double totalMoney = principal + interest;//还款总额
        ret.put("还款总额",FORMAT.format(totalMoney));//还款总额
        ret.put("贷款总额",FORMAT.format(principal));//贷款总额
        ret.put("还款总利息",FORMAT.format(interest));//还款总利息
        ret.put("首月还款金额",FORMAT.format(firstMonth));//首月还款金额
        ret.put("每月递减利息",FORMAT.format(decreaseMonth));//每月递减利息
        ret.put("还款期限",String.valueOf(months));//还款期限
        
        JSONArray detail = new JSONArray();
        double totalPrincipal = 0.0;//还款总本金
        double totalInterest = 0.0;//还款总利息
        for (int i = 1; i <= months; i++) {
			JSONObject eachMonth = new JSONObject();
			double eachInterest = (principal - (eachPrincipal * (i -1)))* monthRate;//每个月偿还的利息
			double eachLoan = eachPrincipal + eachInterest;//每月偿还的本息
			
			totalInterest+=eachInterest;
			totalPrincipal+=eachPrincipal;
			
			double remainPrincipal = principal - eachPrincipal * i ;//剩余本金
			double remainInterest = interest - totalInterest;//剩余利息
			
			
			eachMonth.put("还款期数", i);
			eachMonth.put("剩余本金", FORMAT.format(remainPrincipal));
			eachMonth.put("剩余利息", FORMAT.format(remainInterest));
			eachMonth.put("已还总本金", FORMAT.format(totalPrincipal));
			eachMonth.put("已还总利息", FORMAT.format(totalInterest));
			eachMonth.put("每月本息", FORMAT.format(eachLoan));
			eachMonth.put("每月本金", FORMAT.format(eachPrincipal));
			eachMonth.put("每月利息", FORMAT.format(eachInterest));
			detail.add(eachMonth);
		}
        ret.put("还款详情", detail);
        
        return ret;
    }
    
    
    /**
     * 一次性提前还款计算（等额本息）
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param payTimes  已还次数
     * @param rate      贷款利率
     * @return
     */
    public static JSONObject calculateEqualPrincipalAndInterest(double principal, int months, int payTimes, double rate) {
    	JSONObject ret = new JSONObject();
        double monthRate = rate / (100 * 12);//月利率
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);//每月还款金额
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        double leftLoan = principal * Math.pow(1 + monthRate, payTimes) - preLoan * (Math.pow(1 + monthRate, payTimes) - 1) / monthRate;//n个月后欠银行的钱
        double payLoan = principal - leftLoan;//已还本金
        double payTotal = preLoan * payTimes;//已还总金额
        double payInterest = payTotal - payLoan;//已还利息
        double totalPayAhead = leftLoan * (1 + monthRate);//剩余一次还清
        double saveInterest = totalMoney - payTotal - totalPayAhead;
        ret.put("原还款总额",FORMAT.format(totalMoney));//原还款总额
        ret.put("贷款总额",FORMAT.format(principal));//贷款总额
        ret.put("原还款总利息",FORMAT.format(interest));//原还款总利息
        ret.put("原还每月还款金额",FORMAT.format(preLoan));//原还每月还款金额
        ret.put("已还总金额",FORMAT.format(payTotal));//已还总金额
        ret.put("已还本金",FORMAT.format(payLoan));//已还本金
        ret.put("已还利息",FORMAT.format(payInterest));//已还利息
        ret.put("一次还清支付金额",FORMAT.format(totalPayAhead));//一次还清支付金额
        ret.put("节省利息",FORMAT.format(saveInterest));//节省利息
        ret.put("剩余还款期限",String.valueOf(0));//剩余还款期限
        return ret;
    }
    
    public static void main(String[] args) {
		//贷款65万,360期,利率5.39%
    	double principal = 65*10000;
    	int months = 360;
    	double rate = 5.39;
    	System.out.println("等额本息:"+calculateEqualPrincipalAndInterest(principal,months,rate).toJSONString());
    	System.out.println("等额本金:"+calculateEqualPrincipal(principal,months,rate).toJSONString());
    	System.out.println("等额本息详情:"+calculateEqualPrincipalAndInterestDetail(principal,months,rate).toJSONString());
    	System.out.println("等额本金详情:"+calculateEqualPrincipalDetail(principal,months,rate).toJSONString());
	}
}
