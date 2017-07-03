package com.example.leakdemo.wiring;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

import com.example.leakdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/3.
 */

public class LogAdapter extends ArrayAdapter<String> {
    public LogAdapter(Context context, List<String> logs) {
        super(context, R.layout.item_log,R.id.item_log,logs);
    }

}
