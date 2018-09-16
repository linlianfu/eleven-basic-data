package cn.eleven.basic.data.RandomTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * @author eleven
 * @date 2018/9/16
 * @description 随机数测试单元
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/common.xml")
public class RandomTest {

    @Test
    public void test(){
        Random random = new Random();
        long l = random.nextLong();
        log.info(l+"");

    }
}
