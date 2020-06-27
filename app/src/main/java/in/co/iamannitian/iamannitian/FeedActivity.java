package in.co.iamannitian.iamannitian;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import in.co.iamannitian.iamannitian.adapters.ViewPagerAdapter;
import in.co.iamannitian.iamannitian.models.Slide;

public class FeedActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager2;
    private List<Slide> slideList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        createSlides();
        //viewpager = findViewById(R.id.viewPagerSlider);
        viewPager2 = findViewById(R.id.viewPagerSlider);
        viewPagerAdapter = new ViewPagerAdapter(slideList, FeedActivity.this);
        //viewpager.setAdapter(viewPagerAdapter);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        //viewPager2.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager2.setAdapter(viewPagerAdapter);
    }

    private void createSlides(){
        Slide slide1 = new Slide();
        slide1.setAuthor("Seemant");
        slide1.setDate("24th Oct");
        slide1.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide1.setHeading("This is a demo post straight from Android Studio");
        slide1.setId(1);
        slide1.setLikesCount(15);
        slide1.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");


        slideList.add(slide1);

        Slide slide2 = new Slide();
        slide2.setAuthor("Shekhar");
        slide2.setDate("24th Oct");
        slide2.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide2.setHeading("This is a demo post straight from Android Studio");
        slide2.setId(2);
        slide2.setLikesCount(15);
        slide2.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        slideList.add(slide2);
    }

    @RequiresApi(21)
    public static class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationY(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

}