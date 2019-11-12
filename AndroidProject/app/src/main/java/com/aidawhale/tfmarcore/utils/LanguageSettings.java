package com.aidawhale.tfmarcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageSettings {

    public static void updateLocale(Context context) {

        if(context == null)
            return;

        // Update locale
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();

        SharedPreferences sharedPreferences = context.getSharedPreferences("appLanguage", Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("language", ""); // "" is returned default value if "language" doesn't exist on sharedPreferente

        if(!lang.equals("")){
            configuration.setLocale(new Locale(lang.toLowerCase()));
            res.updateConfiguration(configuration, displayMetrics);
        }
    }
}
