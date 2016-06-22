package urils.ecaray.com.ecarutils.Utils;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 内存缓冲
 * @author bin
 *
 */
public class MemoryCache {
   public static  Map<String, SoftReference<Bitmap>> cache=
           Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());//软引用

   public Bitmap get(String id){
       if(!cache.containsKey(id))
           return null;
       SoftReference<Bitmap> ref=cache.get(id);
       return ref.get();
   }

   public void put(String id, Bitmap bitmap){
       cache.put(id, new SoftReference<Bitmap>(bitmap));
   }

   public void clear() {
       cache.clear();
   }
}