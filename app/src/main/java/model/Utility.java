package model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christophe on 01/06/2015. For PlastProd Project
 * Classe utilitaire qui permet de taper dans Google Calendar
 */
public class Utility {

    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    public static final int INTERVALLE = 86400;

    /**
     * Renvoi une liste de RDV
     * @param context Context de Création
     * @return une liste d'objet RDV
     */
    public static ArrayList<RDV> readCalendarEvent(Context context) {
        ArrayList<RDV> listeRDV = new ArrayList<RDV>();
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {
            Long time = System.currentTimeMillis()/ 1000;
            Long tDebut = Long.parseLong(cursor.getString(3)) /1000;
            String date = getDate(Long.parseLong(cursor.getString(3)));
            if((tDebut - time) < INTERVALLE && (tDebut - time) > 0)
            {
                RDV rdv = new RDV(cursor.getString(1), getHeure(Long.parseLong(cursor.getString(3))), getHeure(Long.parseLong(cursor.getString(4))),date);
                listeRDV.add(rdv);
            }

            CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return listeRDV;
    }

    /**
     * Renvoie une heure
     * @param milliSeconds TimeStamp
     * @return une heure formatée("hh:mm:ss a")
     */
    public static String getHeure(long milliSeconds)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Renvoie une date
     * @param milliSeconds TimeStamp
     * @return une date formatée("dd/MM/yyyy")
     */
    public static String getDate(long milliSeconds)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}