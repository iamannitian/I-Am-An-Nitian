package in.co.iamannitian.iamannitian;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {

  public static final String EXTRA_URL = "imageUrl";
  public static final String EXTRA_NEWS_TITLE = "newsTitle";
  public static final String EXTRA_NEWS_DESCRIPTION = "newDescription";

  private int image_resources[] =
          {
                  R.drawable.hacked,
                  R.drawable.nittrichy,
                  R.drawable.nitrkl,
                  R.drawable.hostelx,
                  R.drawable.nittrichy
                  };

  private List<SlideUtils> sliderImg;
  private ImageLoader imageLoader;

  String str[] = {
          "NIT Srinagar suffering from worst Cyber attack",
          "IIT Bombay touched highest placement record",
          "Jee Mains 2020 dates announced by NAT",
          "NIT Delhi has now its own building",
          "MHRD announces 10 million budget for NIT Trichy"
  };

  private Context context;
  private LayoutInflater layoutInflater;

  public ViewPagerAdapter(List<SlideUtils> sliderImg,Context context)
  {
    this.context = context;
    this.sliderImg = sliderImg;
  }

  @Override
  public int getCount() {
    //return image_resources.length;
    return  sliderImg.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

    return  (view == object);
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, final int position) {
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View itemView = layoutInflater.inflate(R.layout.swipe_layout, container, false);

    SlideUtils utils = sliderImg.get(position);

    final ImageView imageView = itemView.findViewById(R.id.imageView);
    imageLoader = HeaderVolleyRequest.getInstance(context).getImageLoader();
    imageLoader.get(utils.getSlideImageUrl(), ImageLoader.getImageListener
         (imageView,R.color.viewPagerDefaultColor,android.R.drawable.ic_dialog_alert));

    final TextView textView = itemView.findViewById(R.id.image_count);
    //imageView.setImageResource(image_resources[position]);
    textView.setText(sliderImg.get(position).descp);

    itemView.setOnClickListener(new View.OnClickListener(){
        @Override
        public  void onClick(View view)
        {
          Intent intent =  new Intent(context, OnViewPagerClick.class);
          SlideUtils clickedItem =  sliderImg.get(position);
          intent.putExtra(EXTRA_URL, clickedItem.getSlideImageUrl());
          intent.putExtra(EXTRA_NEWS_TITLE, clickedItem.getDescp());
          context.startActivity(intent);
        }

    });

    ViewPager vp = (ViewPager) container;
    vp.addView(itemView, 0);
    return itemView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}
