package service;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.caikee.client.util.LoanQueue;
import com.caikee.core.model.Loan;
public class TestLoanQueue {  
    static long randomTime() {  
        return (long) (Math.random() * 1000);  
    }  
    
    public static void main(String[] args) {  
        //线程池  
        final ExecutorService exec = Executors.newFixedThreadPool(1000);  
        //读个数  
        final AtomicInteger rc = new AtomicInteger();  
        //写个数  
        final AtomicInteger wc = new AtomicInteger();  
      //读线程  
        for(int i = 0 ;i<10 ;i++){
            final int _x=i; 
            Runnable read = new Runnable() {  
                public void run() {   
                    try {
                        Thread.sleep(1000);//每隔1秒取出一个
                        System.out.println("读取前：" + LoanQueue.getSize());
                        Loan loan = LoanQueue.take();
                        System.out.println("读取后：" + LoanQueue.getSize());
                        rc.addAndGet(_x);
                        System.out.println(_x + "次读取到id：" + loan.getId() + "标");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }   
            };  
            exec.submit(read);  
        }
        //写线程
        for (int index = 0; index < 100; index++) {  
            // write thread    
            final int NO = index;  
            Runnable write = new Runnable() {  
                String threadName = "Write" + NO;  
                public void run() {  
                    while (true) {  
                        try {  
                            //当no >100以后每隔1秒中放入1个
                            if(NO>100){
                                Thread.sleep(500);
                            }
                            Loan loan = new Loan();
                            loan.setId(Long.valueOf(NO));
                            wc.addAndGet(NO);
                            System.out.println("放入前：" + LoanQueue.getSize());
                            LoanQueue.put(loan);
                            System.out.println("写入成功：" + NO + "个标");
                            System.out.println("放入后：" + LoanQueue.getSize());
                        } catch (Exception e) {  
                            System.out.println("写入失败：" + NO + "个标");
                        }  
                    }  
                }  
            };  
            exec.submit(write); 
        }  
        exec.shutdown(); 
    }  
} 
