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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {

  private int image_resources[] =
          {
                  R.drawable.hacked,
                  R.drawable.nittrichy,
                  R.drawable.nitrkl,
                  R.drawable.hostelx,
                  R.drawable.bannermain,
                  R.drawable.nittrichy
                  };

  String str[] = {
          "NIT Srinagar suffering from worst cyber attack",
          "IIT Bombay touched highest placement record in 2020",
          "Lorem ipsum is a dummy text",
          "Are the Nitians crazy",
          "MHRD announces 100 corores budget for NIT Trichy",
          "IIIT Bhopal building collapsed 5 dead"
  };

  private Context context;
  private LayoutInflater layoutInflater;

  public ViewPagerAdapter(Context context)
  {
    this.context = context;
  }

  @Override
  public int getCount() {
    return image_resources.length;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

    return  (view == object);
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View itemView = layoutInflater.inflate(R.layout.swipe_layout, container, false);
    ImageView imageView = itemView.findViewById(R.id.imageView);
    TextView textView = itemView.findViewById(R.id.image_count);
    imageView.setImageResource(image_resources[position]);
    textView.setText(str[position]);
    container.addView(itemView);
    return itemView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}
