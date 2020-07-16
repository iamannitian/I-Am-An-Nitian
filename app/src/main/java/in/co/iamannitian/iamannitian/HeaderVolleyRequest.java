package in.co.iamannitian.iamannitian;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class HeaderVolleyRequest
{
    private static HeaderVolleyRequest headerVolleyRequest;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private  HeaderVolleyRequest(Context context)
    {
        this.context = context;
        this.requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<>(20);


            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                  cache.put(url, bitmap);
            }
        });

    }

    public static  synchronized  HeaderVolleyRequest getInstance(Context context)
    {
        if(headerVolleyRequest == null)
            headerVolleyRequest = new HeaderVolleyRequest(context);
        return headerVolleyRequest;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            Cache cache = new DiskBasedCache(context.getCacheDir(),
                    10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return  requestQueue;
    }

    public void addToRequestQueue(Request req)
    {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader()
    {
        return  imageLoader;
    }
}
