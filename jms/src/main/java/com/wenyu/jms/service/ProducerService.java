package com.wenyu.jms.service;

import javax.jms.Destination;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/16.
 */
public interface ProducerService {
    void sendMessage(Destination destination, String message);
}
