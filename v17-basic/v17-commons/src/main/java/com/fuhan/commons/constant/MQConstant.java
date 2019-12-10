package com.fuhan.commons.constant;

public interface MQConstant {

    class EXCHANGE{
        public static final String PRODUCT_EXCHANGE = "product_exchange";
        public static final String SMS_EXCHANGE = "sms_exchange";
        public static final String MAIL_EXCHANGE = "mail_exchange";
    }

    class QUEUE{
        public static final String ITEM_QUEUE = "item_queue";
        public static final String SEARCH_QUEUE = "search_queue";
        public static final String MESSAGE_QUEUE = "message_queue";
        public static final String MAIL_QUEUE = "mail_queue";
        public static final String CART_MERGE_QUEUE="cart_marge_queue";
    }

    class ROUNTING{
        public static final String PRODUCT_KEY = "product.*";
        public static final String MESSAGE_KEY = "message.*";
        public static final String MAIL_KEY = "mail.*";
    }

}
