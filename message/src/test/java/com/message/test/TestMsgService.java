package com.message.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.message.config.WebSocketConfig;
import com.message.entity.MsgDeliveryWay;
import com.message.entity.MsgTitle;
import com.message.service.IMsgService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = { MessageDataConfig.class })
@ContextConfiguration(classes = { WebSocketConfig.class })
public class TestMsgService {
	@Autowired
	private IMsgService msgService;

	@Test
	public void test1() throws Exception {
		System.out.println("--- Begin ---");
		String deliveryWay = "email";
		MsgDeliveryWay deliverWay = msgService.getDeliverWay(deliveryWay);
		System.out.println(deliverWay.getMsgObject());
		System.out.println("--- End ---");
	}

	@Test
	public void InsetTitle() throws Exception {
		MsgTitle title = new MsgTitle();
		title.setTitleId("T005");
		title.setTitleName("未读消息");
		title.setTitleType("TP04");
		title.setTitleContent("您有新的信息，请注意查收");
		msgService.saveTitle(title);
	}
}
