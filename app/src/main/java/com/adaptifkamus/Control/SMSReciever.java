package com.adaptifkamus.Control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.provider.Telephony.Sms.Intents;

/**
 * Created by hilmy on 19/06/2016.
 */
public class SMSReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage [] msgs = null;
        String str = "SMS from ";
        String nomorPengirim = "";
        if (bundle != null) {
            Object [] pdus = (Object[]) bundle.get("pdus");
            msgs  = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], Intents.SMS_RECEIVED_ACTION);
                if (i == 0) {
                    str += msgs[i].getOriginatingAddress();
                    nomorPengirim = msgs[i].getOriginatingAddress();
                    str += ": ";
                }

                str += msgs[i].getMessageBody().toString();
            }

            abortBroadcast();
        }
    }
}