package urils.ecaray.com.ecarutils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {

	/**
	 * 序列化对象
	 * 
	 * @throws IOException
	 */

	public static void serializeObject(File file, Object object) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));

			oos.writeObject(object);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 反序列化对象
	 * 
	 * @throws IOException
	 * 
	 * @throws ClassNotFoundException
	 */

	public static Object deserializeObject(File file) {
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			object = ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

}
