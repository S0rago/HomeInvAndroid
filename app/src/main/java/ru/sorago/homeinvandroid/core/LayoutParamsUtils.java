package ru.sorago.homeinvandroid.core;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LayoutParamsUtils {

    public static LinearLayout.LayoutParams getLLParamWeightWCWC(float weight) {
        return new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
    }

    public static LinearLayout.LayoutParams getLLParamWeightMPWC(float weight) {
        return new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
    }
}
