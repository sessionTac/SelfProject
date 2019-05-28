package cn.springboot.osbulkparts.queuetask;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

//@Service
public class QueueCustomerTest {
	
	@JmsListener(destination = "realtimeTopic")
	public void getQueue(String info) {
		System.out.println("Get queue from testTopic is "+info);
	}
	
	@JmsListener(destination = "testDelayTopic")
	public void getDelayQueue(String info) {
		System.out.println("Get queue from testDelayTopic is "+info);
	}
}
