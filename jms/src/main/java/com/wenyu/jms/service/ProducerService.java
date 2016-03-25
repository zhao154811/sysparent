package com.wenyu.jms.service;

import javax.jms.Destination;

/**
 */
public interface ProducerService {
    void sendMessage(Destination destination, String message);
}
