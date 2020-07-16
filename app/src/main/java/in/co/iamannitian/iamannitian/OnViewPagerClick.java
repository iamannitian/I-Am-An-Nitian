package in.co.iamannitian.iamannitian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.logging.Handler;

import static in.co.iamannitian.iamannitian.ViewPagerAdapter.EXTRA_URL;
import static in.co.iamannitian.iamannitian.ViewPagerAdapter.EXTRA_NEWS_TITLE;

public class OnViewPagerClick extends AppCompatActivity {

    private ImageView newsImage;
    private TextView newsTitle;
    private ShimmerFrameLayout shimmer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_view_pager_click);

        newsImage = findViewById(R.id.newImage);
        newsTitle = findViewById(R.id.newsTitle);
        shimmer1 = findViewById(R.id.shimmer1);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String newsTitleX = intent.getStringExtra(EXTRA_NEWS_TITLE);

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .centerInside()
                .into(newsImage);

        newsTitle.setText(newsTitleX);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmer1.stopShimmer();
                shimmer1.setVisibility(View.GONE);
                newsImage.setVisibility(View.VISIBLE);
            }
        }, 4000);
    }
}
