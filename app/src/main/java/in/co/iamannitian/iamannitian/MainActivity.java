package in.co.iamannitian.iamannitian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    }

    //setting up toolbar menu
    private void setUpToolbarMenu()
    {
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.app_logo);

        TextView title = (TextView) toolbar.getChildAt(0);

        //title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
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

      drawerToggle.setDrawerArrowDrawable(new HumbergerDrawable(this));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

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

}
