package com.massiva.nuevopudahuel.api.response;

import com.massiva.nuevopudahuel.model.Queue;

/**
 * Created by iaguila on 18/1/17.
 */

public class QueueResponse {

    private int international;
    private int national;

    public Queue toQueue() {
        Queue queue = new Queue();
        queue.setInternational(international);
        queue.setNational(national);
        return queue;
    }
}
