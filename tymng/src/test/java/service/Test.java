package service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * 项目名称：CKhome   
 * 类名称：Test   
 * 类描述：
 * 创建人：luyujian
 * 创建时间：2013年12月16日 下午3:18:30   
 * 修改备注：
 */
public class Test {
	
	public static void ch( Map a) {
		a.put("aa", "cc");
	}
	
	public static void ch( StringBuffer b) {
		b.append("222");
	}
/*	public static void main(String ... arg) {
		Map  a = new HashMap();
		a.put("aa", "bb");
		System.out.println(a.get("aa"));
		ch(a);
		System.out.println(a.get("aa"));
		
		StringBuffer  b = new StringBuffer("111");
		ch(b);
		System.out.println(b);
		
	}*/
	
	
	public static void main(String ... arg){
		
	//	 String sdf=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	      //  Calendar cal = Calendar.getInstance();
		//  Date signTimeEnd=DateUtils.addMonths(new Date(), 24);
		  //  System.out.println(DateUtils.addDays(signTimeEnd, -1));
		     
		//System.out.println(CalculateTools.calculateActualAnnuaRateByMonthlyRate(0.01));
	//	System.out.println(CalculateTools.calculateMonthlyInterest(0.13098, interestRatio));
	       /*Date df= DateUtils.addMonths(new Date(), 24);
	       df=DateUtils.addDays(df, -1);
	       System.out.println(new Date().toString());
	       System.out.println(df);*/
	       // cal.add(Calendar.MONTH,24);
	        //System.out.println(cal.toString());
	        //Date date=cal.getTime();
	        //System.out.println( cal.getTime());
	       

	       // String reStr = sdf.format(rightNow.getTime());
	       // System.out.println(reStr);
	        
	      //  System.out.println(new BigDecimal("" + 12.006).setScale(2,RoundingMode.HALF_EVEN).doubleValue());
	      //  BigDecimal decimalAmount = new BigDecimal("" + 12.99666).s;
	        // 月利率
	       // BigDecimal decimalMonthlyInterestRatio = BigDecimal.valueOf(interestRatio);
	        // 月利息，四舍五入，保留小数点后两位
	      /*  BigDecimal decimalInterest = decimalAmount.setScale(2,
	                RoundingMode.HALF_EVEN);
	        System.out.println(decimalInterest.doubleValue());*/
		 Map<String, Object> staticsMp = new HashMap<String, Object>();   
		 System.out.println(staticsMp.get("totalLoanAmount"));
		System.out.println(new DecimalFormat("######0.00").format(staticsMp.get("totalLoanAmount"))  ); 
	        
	
	
}
	
	
}
