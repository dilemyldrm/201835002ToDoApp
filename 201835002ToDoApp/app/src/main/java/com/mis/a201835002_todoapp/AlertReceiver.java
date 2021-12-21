package com.mis.a201835002_todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class AlertReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

            Uri alarmMelody = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmMelody == null) {
                alarmMelody = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmMelody);
            ringtone.play();

    }
}
