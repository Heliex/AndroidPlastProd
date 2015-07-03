package model;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import BDD.AffectationCommande;
import BDD.AffectationMatiere;
import BDD.Client;
import BDD.DataBaseHandler;
import BDD.Matiere;
import BDD.Nomenclature;
import BDD.Prospect;
import adapter.NavDrawerListAdapter;
import barbeasts.plastprod.R;
import menu.AjoutClient;
import menu.AjoutProspect;
import menu.BonCommande;
import menu.FormulaireSatisfaction;
import menu.HomeFragment;
import menu.InfosClient;
import menu.InfosProspect;
import menu.ListeProduits;
import menu.SuiviClient;
import menu.SuiviProspect;

public class MainActivity extends Activity {


    // Déclaration des variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mDrawerRightList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    private static String nomFragment;

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

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        final View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        ImageButton imageButton = (ImageButton) mCustomView.findViewById(R.id.action_bar_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() { // Gestion de la déconnexion
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Déconnecté",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        final TextView mTitleTextView = (TextView)mCustomView.findViewById(R.id.action_bar_title);
        mTitleTextView.setText("Accueil");
        TypedArray navMenuIcons;
        TypedArray navMenuIconsRight;

        ArrayList<NavDrawerItem> navDrawerItems;
        ArrayList<NavDrawerItem> navDrawerRightItems;
        NavDrawerListAdapter adapter;
        NavDrawerListAdapter adapterRight;

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
        new NavDrawerItem(navMenuTitles[7],navMenuIcons.getResourceId(7,-1));

        // Slider de droite
        navDrawerRightItems = new ArrayList<>();
        // Adding Nav Drawer items to array
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[0],navMenuIconsRight.getResourceId(0,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[1],navMenuIconsRight.getResourceId(1,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[2],navMenuIconsRight.getResourceId(2,-1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[3], navMenuIconsRight.getResourceId(3, -1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[4], navMenuIconsRight.getResourceId(4, -1)));
        navDrawerRightItems.add(new NavDrawerItem(navMenuTitlesRight[5], navMenuIconsRight.getResourceId(5, -1)));
        new NavDrawerItem(navMenuTitlesRight[6], navMenuIconsRight.getResourceId(6, -1));
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
              if(mActionBar != null)
              {
                  TextView tx = (TextView)mCustomView.findViewById(R.id.action_bar_title);
                  Fragment fragment = getFragmentManager().findFragmentByTag("Fragment");
                  if(fragment != null)
                  {
                      String nomFragment = fragment.getClass().getName().replace("menu.","");
                      switch(nomFragment)
                      {
                          case "HomeFragment" :
                              mTitle = navMenuTitles[0];
                              break;
                          case "AjoutClient" :
                              mTitle = navMenuTitles[1];
                              break;
                          case "AjoutProspect":
                              mTitle = navMenuTitlesRight[1];
                              break;
                          case "BonCommande":
                              mTitle = navMenuTitles[5];
                              break;
                          case "FormulaireSatisfaction":
                              mTitle = navMenuTitles[6];
                              break;
                          case "InfosClient":
                              mTitle = navMenuTitles[4];
                              break;
                          case "ListeProduits":
                              mTitle = navMenuTitles[2];
                              break;
                          case "SuiviClient":
                              mTitle = navMenuTitles[3];
                              break;

                          case "ModifierClient":
                              mTitle = navMenuTitles[7];
                              break;
                      }
                  }
                  tx.setText(mTitle);
                  setTitle(mTitle.toString());
              }
              invalidateOptionsMenu();
          }

            public void onDrawerOpened(View drawerView)
            {
                if(drawerView.findViewById(R.id.list_Rightslidermenu) != null)
                {
                    if(mActionBar != null)
                    {
                        TextView tx = (TextView)mCustomView.findViewById(R.id.action_bar_title);
                        tx.setText(mDrawerTitleRight);
                        setTitle(mDrawerTitleRight);
                    }
                }
                else
                {
                    if(mActionBar != null)
                    {
                        TextView tx = (TextView)mCustomView.findViewById(R.id.action_bar_title);
                        tx.setText(mDrawerTitle);
                        setTitle(mDrawerTitle);
                    }
                }
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null)
        {
            displayView(0);
        }
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
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
            // Ces deux lignes permettent de remplacer un fragment par un autre.

            // Update selected item and title, then close the drawer
            if(fragment instanceof HomeFragment)
            {
                mDrawerRightList.setItemChecked(position,true);
                mDrawerRightList.setSelection(position);
            }
            else
            {
                for(int i = 0 ; i < mDrawerRightList.getCount(); i++)
                {
                    mDrawerRightList.setItemChecked(i,false);
                }
            }
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else
        {
            // Error in creating fragment
            Log.i("MainActivity", "Error in creating fragment");
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
                fragment = new InfosProspect();
                break;

            case 3:
                fragment = new SuiviProspect();
                break;

            case 4:
               // fragment = new InfosClient();
                break;

            case 5:
                fragment = new FormulaireSatisfaction();
                break;

            default:
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
            // Ces deux lignes permettent de remplacer un fragment par un autre.

            if(fragment instanceof HomeFragment)
            {
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
            }
            else
            {
                for(int i =0; i < mDrawerList.getCount();i++)
                {
                    mDrawerList.setItemChecked(i,false);
                }
            }
            // Update selected item and title, the close +the drawer
            mDrawerRightList.setItemChecked(position, true);
            mDrawerRightList.setSelection(position);
            setTitle(navMenuTitlesRight[position]);
            mDrawerLayout.closeDrawer(mDrawerRightList);
        }
        else
        {
            // Error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
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
    public void onBackPressed()
    {

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(getFragmentManager().findFragmentByTag("Fragment") != null)
        nomFragment = getFragmentManager().findFragmentByTag("Fragment").getClass().getName();
        else
        nomFragment= null;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(nomFragment != null)
        {
            String instance = nomFragment.replace("menu.","");
            switch(instance)
            {
                case "HomeFragment":
                    Fragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container,fragment,"Fragment").commit();
                    TextView tx = (TextView)mActionBar.getCustomView().findViewById(R.id.action_bar_title);
                    tx.setText("Accueil");
                    break;
            }
        }
    }
}
