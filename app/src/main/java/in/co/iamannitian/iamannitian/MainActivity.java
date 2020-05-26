package in.co.iamannitian.iamannitian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbarMenu();
        setUpDrawerMenu();

        //Overflow menu click listener
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                  /*  case R.id.about:

                        startActivity(new Intent(HomeActivity.this, AboutApp.class));
                        break;

                    case R.id.notifications:
                        startActivity(new Intent(HomeActivity.this, PopupNotification.class));
                        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
                        break;*/
                }
                return true;
            }
        });
    }

    //setting up toolbar menu
    private void setUpToolbarMenu()
    {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
       // toolbar.setTitleTextAppearance(FontFamily);
    }


    //setting up drawer menu
    private  void setUpDrawerMenu()
    {
      NavigationView navigationView = findViewById(R.id.navigationView);
      navigationView.setNavigationItemSelectedListener(this);
      drawerLayout = findViewById(R.id.drawerLayout);
      ActionBarDrawerToggle  drawerToggle =
              new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open_drawer, R.string.close_drawer);

      drawerLayout.addDrawerListener(drawerToggle);
      drawerToggle.syncState();
    }

    private void closeDrawer()
    {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer()
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void onBackPress()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        closeDrawer();
        switch (menuItem.getItemId()) {
          /*  case R.id.send_notify:
                startActivity(new Intent(Admin.this,SendNotification.class));
                break;

            case R.id.add_donar:
                startActivity(new Intent(Admin.this, AddDoner.class));
                break;

            case R.id.add_member:
                startActivity(new Intent(Admin.this, AddTeamMember.class));
                break;

            case R.id.book_upload:
                startActivity(new Intent(Admin.this, UploadBooks.class));
                break;*/

        }
        return true;

    }


}
