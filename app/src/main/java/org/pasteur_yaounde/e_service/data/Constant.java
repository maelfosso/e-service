package org.pasteur_yaounde.e_service.data;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.pasteur_yaounde.e_service.model.DemandeCotation;
import org.pasteur_yaounde.e_service.model.Exam;
import org.pasteur_yaounde.e_service.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by maelfosso on 8/26/16.
 */
public class Constant {

    public static Resources getStrRes(Context context){
        return context.getResources();
    }

    public static String formatTime(long time){
        // income time
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        // current time
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat dateFormat = null;
        if(date.get(Calendar.YEAR)==curDate.get(Calendar.YEAR)){
            if(date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR) )
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            else    dateFormat = new SimpleDateFormat("MMM d", Locale.US);
        }
        else    dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        return dateFormat.format(time);
    }

    /**
     *
     * @param ctx
     * @param path
     * @return
     */
    public static String getFromAsset(Context ctx, String path) {
        String json = null;
        try {
            InputStream is = ctx.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     *
     * @return
     */
    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }

    /**
     *
     * @param max
     * @return
     */
    public static int getRandomInt(int max) {
        Random generator = new Random();

        if (max > 0) {
            return generator.nextInt(max);
        }
        return generator.nextInt();
    }

    /**
     *
     * @param seconds
     * @return
     */
    public static String fromSecondToHM(int seconds) {
        long h = TimeUnit.MILLISECONDS.toHours(new Long(seconds));
        long m = TimeUnit.MILLISECONDS.toMinutes(new Long(seconds));
        long s = TimeUnit.MILLISECONDS.toSeconds(new Long(seconds));

        return String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(new Long(seconds)),
                TimeUnit.MILLISECONDS.toSeconds(new Long(seconds)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(new Long(seconds))));
    }

    /**
     *
     * @param ctx
     * @param fjson
     * @return
     */
    public static String loadJSONFromAsset(Context ctx, String fjson) {
        String json = null;
        try {
            InputStream is = ctx.getAssets().open(fjson);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     *
     * @param ctx
     * @return
     */
    public static List<Exam> getExamsData(Context ctx) {
        String ISO_FORMAT = "dd-MM-yyyy'T'HH:mm";
        Type contactCollectionType = new TypeToken<List<Exam>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();
        List<Exam> items = gson.fromJson(loadJSONFromAsset(ctx, "exams.json"), contactCollectionType);
        return items;
    }

    /**
     *
     * @param ctx
     * @return
     */
    public static ArrayList<DemandeCotation> getDemandeCotationData(Context ctx) {
        String usersPath = "data/demande_cotation.json";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        Type contactTypeList = new TypeToken<Collection<DemandeCotation>>(){}.getType();
        ArrayList<DemandeCotation> items = gson.fromJson(Constant.getFromAsset(ctx, usersPath), contactTypeList);
        return items;
    }

    /**
     *
     * @param ctx
     * @return
     */
    public static ArrayList<User> getUserData(Context ctx) {
        String usersPath = "data/users.json";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        Type contactTypeList = new TypeToken<Collection<User>>(){}.getType();
        ArrayList<User> items = gson.fromJson(Constant.getFromAsset(ctx, usersPath), contactTypeList);
        return items;
    }
}