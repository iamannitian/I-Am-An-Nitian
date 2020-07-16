package in.co.iamannitian.iamannitian;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class ChatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    private View notificationBadge;
    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /*=========>>> Setting Up dark Mode <<<==========*/
        boolean mode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        if(mode)
        {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        sharedPreferences = getSharedPreferences("appData", MODE_PRIVATE);
        switchCompat = (SwitchCompat) navigationView
                .getMenu()
                .findItem(R.id.dark_mode_switch)
                .getActionView();

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            switchCompat.setChecked(true);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.chat);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(ChatActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.notification:
                        startActivity(new Intent(ChatActivity.this, NotificationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.chat:
                        //do nothing
                        break;
                    case R.id.profile:
                        startActivity(new Intent(ChatActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }

                return true;
            }
        });

        setUpToolbarMenu(mode);
        setUpDrawerMenu(mode);
        headerUpdate();
        showBadge();
    }

    /*=======>>>>>>> Setting up toolbar menu <<<<<<<<<=========*/
    private void setUpToolbarMenu(boolean mode)
    {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chat");

        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();

        if(mode)
        {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textColor2));
            actionBar.setIcon(R.drawable.app_logo_dark);
        }
        else
        {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textColor1));
            actionBar.setIcon(R.drawable.app_logo);
        }

        actionBar.setDisplayShowHomeEnabled(true);
    }


    /*=======>>>>>>> Setting up navigation drawer <<<<<<<<<=========*/
    private  void setUpDrawerMenu(boolean mode)
    {
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerArrowDrawable(new HumbergerDrawable(this, mode));
        drawerToggle.syncState();
    }

    private void closeDrawer()
    {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /*=======>>>>>>> Navigation Item Click Listener <<<<<<<<<========*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        closeDrawer();
        switch (menuItem.getItemId()) {
            case R.id.logout:
                logout();
            case R.id.settings:

                break;

        }
        return true;
    }

    /*=========>>>>>>> Setting up overflow menu (when toolbar used as action bar) <<<<<<<<<=========*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    /*=======>>>>>>> Overflow menu item Click listener <<<<<<<<<=========*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.about:
                Toast.makeText(this, "about is clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_info:
                Toast.makeText(this, "app-info is clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*=======>>>>>>> Logout function <<<<<<<<<=========*/
    void logout()
    {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(ChatActivity.this, LoginOrSignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //finish all previous activities
        startActivity(intent);
    }


    /*=======>>>>>>> Updating the header in the navigation view <<<<<<<<<=========*/
    public void headerUpdate()
    {
        TextView user_name,nit_name;
        String name = sharedPreferences.getString("userName","");
        View headView = navigationView.getHeaderView(0);
        user_name = headView.findViewById(R.id.user_name);
        nit_name = headView.findViewById(R.id.nit_name);
        user_name.setText(name.trim());
    }

    /*=======>>>>>>> Show notification Badge <<<<<<<<<=========*/
    public void showBadge()
    {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);
        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView,false);
        itemView.addView(notificationBadge);
    }

    /*=======>>>>>>> restart app on clicking the switch <<<<<<<<<=========*/
    public void restartApp()
    {
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
        finish();
    }

}
