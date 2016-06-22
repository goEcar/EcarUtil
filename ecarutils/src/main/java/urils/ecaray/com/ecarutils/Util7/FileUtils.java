package urils.ecaray.com.ecarutils.Util7;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 读取文件中字符串
     *
     * @param file 目标文件
     * @return 结果字符
     */
    public static String readTextFile(File file) throws Exception {
        StringBuilder result = new StringBuilder();
        InputStream instream = new FileInputStream(file);
        if (instream != null) {
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                result.append(line + "\n");
            }
            instream.close();
        }
        return result.toString();
    }

    /**
     * 向文件写入数据
     *
     * @param file 目标文件
     * @param data 字符数据
     */
    public static void writeTextFile(File file, String data) throws Exception {
        FileWriter filerWriter = new FileWriter(file, false);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
        BufferedWriter bufWriter = new BufferedWriter(filerWriter);
        bufWriter.write(data);
        bufWriter.newLine();
        bufWriter.close();
        filerWriter.close();
    }

    /**
     * 将输入流转成输出流
     *
     * @param is
     * @param os
     */
    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
            is.close();
            os.close();
        } catch (Exception ex) {
        }
    }

    /**
     * 读取输入数据流
     *
     * @param is 输入流
     * @return 字节流
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 读取SD下某路径的所有图片信息
     *
     * @return
     */

    public static String[] listFile(String filePath) {

        File file = new File(filePath);
        File[] f = file.listFiles();
        String Path[] = new String[f.length];
        int len = f.length;
        for (int i = 0; i < len; i++) {
            Path[i] = f[i].getPath();
        }
        return Path;

    }


}
