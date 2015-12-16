package test;
import com.wenyu.jms.service.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.jms.Destination;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebMvc
@ContextConfiguration(value = {"classpath:applicationcontext-jms-test.xml"})
public class ProducerConsumerTest {

    @Autowired
    private ProducerService producerService;
    @Autowired
    private Destination destination;

    @Test
    public void testSend() {
        for (int i = 0; i < 3; i++) {
            producerService.sendMessage(destination, "你好，这是消息：" + (i + 1));
        }
    }

}
