package com.adaptifkamus.Control;

import android.telephony.SmsManager;

import java.util.List;

/**
 * Created by hilmy on 19/06/2016.
 */
public class SMSAdapter {

    SmsManager smsManager;
    SMSReciever smsReciever;

    public SMSAdapter() {
        smsManager = SmsManager.getDefault();
        smsReciever = new SMSReciever();
    }

    public void KirimSMS(String nomorTelepon, String pesan) {
        smsManager.sendTextMessage(nomorTelepon, null, pesan, null, null);
    }

    public void BroadcastSMS(List<String> members,String pesan) {
        for (String member:members) {
            KirimSMS(member, pesan);
        }
    }
}