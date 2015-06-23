package model;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import adapter.NavDrawerListAdapter;
import barbeasts.plastprod.R;
import menu.AjoutClient;
import menu.AjoutProspect;
import menu.BonCommande;
import menu.FormulaireSatisfaction;
import menu.HomeFragment;
import menu.InfosClient;
import menu.ListeProduits;
import menu.SuiviClient;
import menu.SuiviProspect;

public class MainActivity extends Activity {


    // Déclaration des variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mDrawerRightList;
    private ActionBarDrawerToggle mDrawerToggle;

    // Nav drawer title
    private CharSequence mDrawerTitle;
    private CharSequence mDrawerTitleRight;

    // used to store app title
    private CharSequence mTitle;

    // Slide menu items
    private String[] navMenuTitles;
    private String[] navMenuTitlesRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypedArray navMenuIcons;
        TypedArray navMenuIconsRight;

        ArrayList<NavDrawerItem> navDrawerItems;
        ArrayList<NavDrawerItem> navDrawerRightItems;
        NavDrawerListAdapter adapter;
        NavDrawerListAdapter adapterRight;

        mTitle = getTitle();
        mDrawerTitle  = "Client";
        mDrawerTitleRight ="Prospect" ;
        // Load slides menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuTitlesRight = getResources().getStringArray(R.array.nav_drawer_Right_items);

        // Nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        navMenuIconsRight = getResources().obtainTypedArray(R.array.nav_drawer_Right_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerRightList = (ListView) findViewById(R.id.list_Rightslidermenu);

        // Slider de gauche
        navDrawerItems = new ArrayList<>();

        // Adding Nav Drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],navMenuIcons.getResourceId(0,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(1,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],navMenuIcons.getResourceId(2,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],navMenuIcons.getResourceId(3,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],navMenuIcons.getResourceId(4,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],navMenuIcons.getResourceId(5,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],navMenuIcons.getResourceId(6,-1)));

        // Slider de droite
        navDrawerRightItems = new ArrayList<>();
        // Adding Nav Drawer items to array
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[0],navMenuIconsRight.getResourceId(0,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[1],navMenuIconsRight.getResourceId(1,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[2],navMenuIconsRight.getResourceId(2,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[3],navMenuIconsRight.getResourceId(3,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[4],navMenuIconsRight.getResourceId(4,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[5],navMenuIconsRight.getResourceId(5,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[6],navMenuIconsRight.getResourceId(6,-1)));
        // Recycle Typed array
        navMenuIcons.recycle();
        navMenuIconsRight.recycle();
        // Listener on the menu
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerRightList.setOnItemClickListener(new SlideRightMenuClickListener());

        // Settings the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);

        adapterRight = new NavDrawerListAdapter(getApplicationContext(),navDrawerRightItems);
        mDrawerRightList.setAdapter(adapterRight);

        Toolbar toolbar = new Toolbar(getApplicationContext());
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name)
        {
          public void onDrawerClosed(View view)
          {
              if(getActionBar() != null)
                  getActionBar().setTitle(mTitle);
              invalidateOptionsMenu();
          }

            public void onDrawerOpened(View drawerView)
            {
                if(drawerView.findViewById(R.id.list_Rightslidermenu) != null)
                {
                    if(getActionBar() != null)
                    getActionBar().setTitle(mDrawerTitleRight);
                }
                else
                {
                    if(getActionBar() != null)
                    getActionBar().setTitle(mDrawerTitle);
                }
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null)
        {
            displayView(0);
        }
        // Chargement du calendar pour test
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Display view for selected nav drawer item
            displayView(position);
        }
    }

    private class SlideRightMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Display view for selected nav drawer item
            displayRightView(position);
        }
    }

    //Menu client
    private void displayView(int position)
    {
        // Update the main content by replacing fragment
        Fragment fragment = null;
        String TAG = "";
        switch(position)
        {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new AjoutClient();
                break;

            case 2:
                fragment = new ListeProduits();
                break;

            case 3:
                fragment = new SuiviClient();
                break;

            case 4:
                fragment = new InfosClient();
                break;

            case 5:
                fragment = new BonCommande();
                break;

            case 6:
                fragment = new FormulaireSatisfaction();
                break;

            default:
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            // Ces deux lignes permettent de remplacer un fragment par un autre.

            // Update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position,true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else
        {
            // Error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    // Menu prospect
    private void displayRightView(int position)
    {
        // Update the main content by replacing fragment
        Fragment fragment = null;
        switch(position)
        {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new AjoutProspect();
                break;

            case 2:
              //  fragment = new ListeProduits();
                break;

            case 3:
                fragment = new SuiviProspect();
                break;

            case 4:
               // fragment = new InfosClient();
                break;

            case 5:
                //fragment = new BonCommande();
                break;

            case 6:
               // fragment = new FormulaireSatisfaction();
                break;

            default:
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            // Ces deux lignes permettent de remplacer un fragment par un autre.

            // Update selected item and title, the close +the drawer
            mDrawerRightList.setItemChecked(position,true);
            mDrawerRightList.setSelection(position);
            setTitle(navMenuTitlesRight[position]);
            mDrawerLayout.closeDrawer(mDrawerRightList);
        }
        else
        {
            // Error in creating fragment
            Log.e("MainActivity","Error in creating fragment");
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toggle nav drawer on selecting action bar app icon/title

        if(mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch(item.getItemId())
        {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // If nav Drawer is opened, hide the action items
        mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        if(getActionBar() != null)
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostResume()
    {
        super.onPostResume();
        Fragment fragment =  new HomeFragment();
        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            ListView mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
            String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
            if(mDrawerList != null) {
                mDrawerList.setItemChecked(0, true);
                mDrawerList.setSelection(0);
            }
            setTitle(navMenuTitles[0]);
            // Ces deux lignes permettent de remplacer un fragment par un autre.
        }
    }

    @Override
    public void onBackPressed()
    {

    }
}
