package in.co.iamannitian.iamannitian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //For Nav-Drawer
    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frame;
    NavigationView navView;
    ActionBarDrawerToggle abdt;

    //For Menu Options
    ExpandableListView menuView;
    ExpandableListAdapter menuAdapter;
    List<String> menuItems;
    Map<String,List<String>> subMenuItems;
    List<Integer> menuIcons;

    //For Images in Navigation Menu
    //Image Links for insta,fb,youtube and web
    final String[] linksList = new String[]{"https://www.instagram.com/i_am_an_nitian/","https://m.facebook.com/iamannitian","https://www.youtube.com/channel/UCnONMgL4R7ptGLHIK0KaMCQ","https://iamannitian.co.in/",};
    ImageButton img1,img2,img3,img4;


    //TextView for now , further it will become a whole activity
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding items
        drawerLayout        = findViewById(R.id.drawerLayout);
        coordinatorLayout   = findViewById(R.id.coordinatorLayout);
        toolbar             = findViewById(R.id.toolbar);
        frame               = findViewById(R.id.frame);
        navView             = findViewById(R.id.navView);
        menuView            = findViewById(R.id.expandedMenu);
        img1                = findViewById(R.id.imgInsta);
        img2                = findViewById(R.id.imgFB);
        img3                = findViewById(R.id.imgYoutube);
        img4                = findViewById(R.id.imgWebpage);
        textView            = findViewById(R.id.randomtext);
        //setup toolbar for the activity
        setUpToolBar();
        //setup menu
        setUpMenu();
        //setup imagelinks
        ImageButton[] imageList = new ImageButton[]{img1,img2,img3,img4};
        for(int i=0; i<imageList.length ;i++ ) {
            final int index = i;
            imageList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index!=1) {
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(linksList[index])));
                    }else {
                        startActivity(getOpenFacebookIntent(getBaseContext()));
                    }
                }
            });
        }

        //OnItemClickListener For Menu Items
        menuView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                switch(groupPosition) {
                    case 0 :    //For Home
                        textView.setText("Home Page");
                        break;
                    case 1 :    //For Blogs
                        textView.setText("Blog Page");
                        break;
                    case 2 :    //For Posts
                        textView.setText("Post Page");
                        break;
                    case 3 :    //For My Preferences
                        textView.setText("My Preferences");
                        break;
                    case 4 :    //For My Blogs
                        textView.setText("My Blogs");
                        break;
                    case 5 :    //For More
                        ImageView img = v.findViewById(R.id.imgParent);
                        float angle = img.getRotation();
                        angle += 180;
                        if(angle>=360) angle -= 360;
                        img.setRotation(angle);
                        break;
                }
                if(!(groupPosition ==5)) {
                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });

        //OnClickListener For Child elements : Feedback,share,logout etc.

        menuView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //if More is clicked
                if(groupPosition == 5) {
                    switch (childPosition) {
                        case 0 :    //For FeedBack
                            textView.setText("FeedBack Page");
                            break;
                        case 1 :    //For About Us
                            textView.setText("About Us");
                            break;
                        case 2 :    //For Contact Us
                            textView.setText("Contact Us");
                            break;
                        case 3 :    //For Share App
                            textView.setText("Share App Prompt");
                            break;
                        case 4 :    //For Log Out
                            textView.setText("Sending to logout Page");
                            break;
                    }
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void setUpMenu() {
        menuItems    = new ArrayList<>();
        //Indices
        menuItems.add("Home");              //0
        menuItems.add("Blogs");             //1
        menuItems.add("Posts");             //2
        menuItems.add("My Preferences");    //3
        menuItems.add("My Blogs");          //4
        menuItems.add("More");              //5

        menuIcons    = new ArrayList<>();
        //Indices
        menuIcons.add(R.drawable.ic_home);          //0
        menuIcons.add(R.drawable.ic_blogs);         //1
        menuIcons.add(R.drawable.ic_posts);         //2
        menuIcons.add(R.drawable.ic_preferences);   //3
        menuIcons.add(R.drawable.ic_myblogs);       //4
        menuIcons.add(R.drawable.ic_dropdown);      //5

        subMenuItems = new HashMap<>();

        List<String> forMore = new ArrayList<>();
        forMore.add("\u00B7 Give Feedback");   //0
        forMore.add("\u00B7 About Us");        //1
        forMore.add("\u00B7 Contact Us");      //2
        forMore.add("\u00B7 Share App");       //3
        forMore.add("\u00B7 Log Out");         //4

        //Adding sub menu items for "More" Item :
        subMenuItems.put(menuItems.get(5),forMore);

        //adding adapter for menu
        menuAdapter = new NavMenuAdapter(this,menuItems,subMenuItems,menuIcons);
        menuView.setAdapter(menuAdapter);

    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("I Am An NITian");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        abdt = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(abdt);
        abdt.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1780240678883639"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/iamannitian"));
        }
    }
}
