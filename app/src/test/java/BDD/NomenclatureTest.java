package BDD;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.Display;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.MainActivity;

/**
 * Created by Christophe on 16/07/2015. For PlastProd Project on purpose
 */
public class NomenclatureTest extends TestCase {

    public void testGetId() throws Exception {
        Nomenclature n = new Nomenclature();
        assertEquals(0,n.getId());
    }

    public void testGetNom() throws Exception {
        Nomenclature n = new Nomenclature();
        assertEquals(null,n.getNom());
    }

    public void testGetListeMatiere() throws Exception {
        Nomenclature n = new Nomenclature();
        assertEquals(null,n.getListeMatiere());
    }

    public void testSetId() throws Exception {
        Nomenclature n = new Nomenclature();
        n.setId(666);
        assertEquals(666,n.getId());

    }

    public void testSetNom() throws Exception {
        Nomenclature n = new Nomenclature();
        n.setNom("TABLE PLASTIQUE");
        assertEquals("TABLE PLASTIQUE",n.getNom());

    }

    public void testSetListeMatiere() throws Exception {

        Nomenclature n = new Nomenclature();
        Matiere m = new Matiere();
        ArrayList<Matiere> listeMatiere = new ArrayList<>();
        listeMatiere.add(m);
        n.setListeMatiere(listeMatiere);
        assertEquals(1, n.getListeMatiere().size());

    }

    public void testGetPrixTotal(DataBaseHandler db) throws Exception {
        Nomenclature n = new Nomenclature();
        assertEquals(0.0,n.getPrixTotal(db, n.getId()));
    }

    public void testGetQuantite() throws Exception {
        Nomenclature n = new Nomenclature();
        assertEquals(0,n.getQuantite());
    }

    public void testSetQuantite() throws Exception {

        Nomenclature n = new Nomenclature();
        n.setQuantite(1235);
        assertEquals(1235,n.getQuantite());
    }
}