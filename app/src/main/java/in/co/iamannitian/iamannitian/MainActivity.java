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
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Edits;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    private View notificationBadge;
    private SwitchCompat switchCompat;

    private ViewPager viewPager, viewPager2;
    private ViewPagerAdapter adapter;
    private HeadLineViewPagerAdapter adapter2;
    private TabLayout tabLayout;

    int currentPage = 0;
    int currentHeadline = 0;
    final long DELAYS_MS = 500;
    final long PERIOD_MS = 3000;


    //Setting up widgets for youtube videos
    private ArrayList<GetYoutubeData> youtubeData;
    private ImageView video1;
    private ImageView video2;
    private ImageView video3;
    private TextView title1;
    private TextView title2;
    private TextView title3;

    String url1, title_x, url2, title_y, url3, title_z,id1, id2, id3;

    RequestQueue rq;
    private List<SlideUtils> sliderImg;

    String request_url = "https://iamannitian.co.in/app/get_slider_image.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*=========>>> Setting Up dark Mode <<<==========*/
        boolean mode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        if (mode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing youtube related widgets
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        video3 = findViewById(R.id.video3);
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);


        rq = Volley.newRequestQueue(this);
        sliderImg = new ArrayList<>();

        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        sharedPreferences = getSharedPreferences("appData", MODE_PRIVATE);

        //Slider
        viewPager = findViewById(R.id.viewPager);

        viewPager2 = findViewById(R.id.viewPager2);
        adapter2 = new HeadLineViewPagerAdapter(this);
        viewPager2.setAdapter(adapter2);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager2, true);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 5)
                    currentPage = 0;
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAYS_MS, PERIOD_MS);


        //Timer for the headlines
        final Runnable headline = new Runnable() {
            @Override
            public void run() {
                if (currentHeadline == 5)
                    currentHeadline = 0;
                viewPager2.setCurrentItem(currentHeadline++, true);
            }
        };

        final Handler handler2 = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler2.post(headline);
            }
        }, 1000, 4000);


        switchCompat = (SwitchCompat) navigationView
                .getMenu()
                .findItem(R.id.dark_mode_switch)
                .getActionView();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switchCompat.setChecked(true);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        //do nothing
                        break;
                    case R.id.notification:
                        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.chat:
                        startActivity(new Intent(MainActivity.this, ChatActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                }

                return true;
            }
        });

        new RequestYoutubeAPI().execute();
        sendRequest();
        setUpToolbarMenu(mode);
        setUpDrawerMenu(mode);
        headerUpdate();
        showBadge();
    }


    /*=======>>>>>>> Setting up toolbar menu <<<<<<<<<=========*/
    private void setUpToolbarMenu(boolean mode) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (mode) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textColor2));
            actionBar.setIcon(R.drawable.app_logo_dark);
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textColor1));
            actionBar.setIcon(R.drawable.app_logo);
        }

        actionBar.setDisplayShowHomeEnabled(true);

    }


    /*=======>>>>>>> Setting up navigation drawer <<<<<<<<<=========*/
    private void setUpDrawerMenu(boolean mode) {
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerArrowDrawable(new HumbergerDrawable(this, mode));
        drawerToggle.syncState();
    }

    private void closeDrawer() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    /*=======>>>>>>> Overflow menu item Click listener <<<<<<<<<=========*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    void logout() {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(MainActivity.this, LoginOrSignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //finish all previous activities
        startActivity(intent);
    }


    /*=======>>>>>>> Updating the header in the navigation view <<<<<<<<<=========*/
    public void headerUpdate() {
        TextView user_name, nit_name;
        String name = sharedPreferences.getString("userName", "");
        View headView = navigationView.getHeaderView(0);
        user_name = headView.findViewById(R.id.user_name);
        nit_name = headView.findViewById(R.id.nit_name);
        user_name.setText(name.trim());
    }

    /*=======>>>>>>> Show notification Badge <<<<<<<<<=========*/
    public void showBadge() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);
        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        itemView.addView(notificationBadge);
    }

    /*=======>>>>>>> restart app on clicking the switch <<<<<<<<<=========*/
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void sendRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                request_url, null, new Response.Listener<JSONArray>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(JSONArray response) {

                // Log.e("<<<<<<<<<<<<=====================>>>>>>>>>\n",response.toString());

                for (int i = 0; i < response.length(); i++) {
                    SlideUtils slideUtils = new SlideUtils();
                    try {
                        JSONObject object = response.getJSONObject(i);
                        slideUtils.setSlideImageUrl
                                ("https://iamannitian.co.in/images/" + object.getString("url"));
                        slideUtils.setDescp(object.getString("descp"));

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    sliderImg.add(slideUtils);
                }

                adapter = new ViewPagerAdapter(sliderImg, MainActivity.this);
                viewPager.setAdapter(adapter);
            }

        }, new Response.ErrorListener() { //error
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        HeaderVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }


    private class RequestYoutubeAPI extends AsyncTask<Void, String, String>{

        String channelId = "UCnONMgL4R7ptGLHIK0KaMCQ";
        String apiKey = "AIzaSyCS2xPXQmwF38bORANCW8Out-tTbX5noWI";

        String channel_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="+channelId+"&key="+apiKey+"&order=date";

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(channel_url);

            try{
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            }catch(IOException ex)
            {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.e("Response:", response);

            if(response!=null)
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    youtubeJsonRequest(jsonObject);
                }catch(JSONException ex)
                {
                    ex.printStackTrace();
                }
        }
    }



    public void youtubeJsonRequest(JSONObject response) throws JSONException {

        JSONArray itemArray = (JSONArray) response.get("items");
        youtubeData = new ArrayList<>();
        for (int i = 0; i < itemArray.length(); i++) {

            GetYoutubeData getYoutubeData = new GetYoutubeData();

            JSONObject obj = itemArray.optJSONObject(i);

            JSONObject idObj = obj.getJSONObject("id"); //contains video id
            JSONObject snippetObj = obj.getJSONObject("snippet"); //contains title
            JSONObject defaultThumbNails =
                    snippetObj.getJSONObject("thumbnails")
                            .getJSONObject("high"); //contains thumbnail url
            String videoId = idObj.getString("videoId");
            String title = snippetObj.getString("title");
            String thumbNailUrl = defaultThumbNails.getString("url");

            getYoutubeData.setVideoId(videoId);
            getYoutubeData.setVideoTitle(title);
            getYoutubeData.setVideoThumbnail(thumbNailUrl);

            youtubeData.add(getYoutubeData);
        }

        setYoutubeData();

    }

    private void setYoutubeData()
    {
         url1 = youtubeData.get(0).getVideoThumbnail();
         title_x = youtubeData.get(0).getVideoTitle();
         id1 = youtubeData.get(0).getVideoId();

         url2 = youtubeData.get(1).getVideoThumbnail();
         title_y = youtubeData.get(1).getVideoTitle();
         id2 = youtubeData.get(1).getVideoId();

         url3 = youtubeData.get(2).getVideoThumbnail();
         title_z = youtubeData.get(2).getVideoTitle();
         id3 = youtubeData.get(2).getVideoId();

        Glide.with(this)
                .load(url1)
                .fitCenter()
                .centerInside()
                .into(video1);

        Glide.with(this)
                .load(url2)
                .fitCenter()
                .centerInside()
                .into(video2);

        Glide.with(this)
                .load(url3)
                .fitCenter()
                .centerInside()
                .into(video3);

        title1.setText(title_x);
        title2.setText(title_y);
        title3.setText(title_z);
    }

    //on click videos
    public void firstVideo(View view)
    {
        Intent   intent = new Intent(this,YoutubePlayer.class);
       intent.putExtra("videoId",id1);
       startActivity(intent);
    }

    public void secondVideo(View view)
    {
        Intent   intent = new Intent(this,YoutubePlayer.class);
        intent.putExtra("videoId",id2);
        startActivity(intent);
    }

    public void  thirdVideo(View view)
    {
        Intent   intent = new Intent(this,YoutubePlayer.class);
        intent.putExtra("videoId",id3);
        startActivity(intent);
    }

}
