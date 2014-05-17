package service;

import org.junit.Test;

import com.caikee.core.enumerate.UserSourceEnum;
public class TestEnum {
    
    @Test
    public void testEnum(){
        UserSourceEnum.findUserSourceEnumBysource(1);
        System.out.println(UserSourceEnum.findUserSourceEnumBysource(1).getDesc());
    }
    

}
