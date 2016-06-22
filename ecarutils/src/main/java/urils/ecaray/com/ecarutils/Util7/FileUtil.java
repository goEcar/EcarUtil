package urils.ecaray.com.ecarutils.Util7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件读写类
 * @author Administrator
 *
 */
public class FileUtil {
	/**
	 * 读取文件中字符串
	 * @param file 目标文件
	 * @return 结果字符
	 */
	public static String readTextFile(File file)throws Exception{
		StringBuilder result = new StringBuilder();
		InputStream instream = new FileInputStream(file); 
		if (instream != null) 
		{
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;
			//分行读取
			while (( line = buffreader.readLine()) != null) {
				result.append(line + "\n");
			}                
			instream.close();
		}
		return result.toString();
	}
	/**
	 * 向文件写入数据
	 * @param file 目标文件
	 * @param data 字符数据
	 */
	public static void writeTextFile(File file, String data)throws Exception{
	            FileWriter filerWriter = new FileWriter(file, false);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖  
	            BufferedWriter bufWriter = new BufferedWriter(filerWriter);  
	            bufWriter.write(data);  
	            bufWriter.newLine();  
	            bufWriter.close();  
	            filerWriter.close();  
	}
}
