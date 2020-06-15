//this class is used to increase the size of the humberger Icon
package in.co.iamannitian.iamannitian;

import android.content.Context;
import android.graphics.Canvas;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

class HumbergerDrawable  extends DrawerArrowDrawable
{
    public HumbergerDrawable(Context context) {
        super(context);
        setColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        setBarLength(50.0f);
        setBarThickness(6.0f);
        setGapSize(11.0f);
    }
}
