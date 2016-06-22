package urils.ecaray.com.ecarutils.Utils;/*
 *===============================================
 *
 * 文件名:${type_name}
 *
 * 描述: 
 *
 * 作者:
 *
 * 版权所有:深圳市亿车科技有限公司
 *
 * 创建日期: ${date} ${time}
 *
 * 修改人:   金征
 *
 * 修改时间:  ${date} ${time} 
 *
 * 修改备注: 
 *
 * 版本:      v1.0 
 *
 *===============================================
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class AssetsCopyToSd {
    private String assetName = "";
    private String targetPatch = "";

    private final SoftReference<Context> mContext;
    private final AssetManager mAssetManager;


    public AssetsCopyToSd(Context context, String assetFileName, String targetPatch) {
        mContext = new SoftReference<Context>(context);
        mAssetManager = context.getAssets();
        this.assetName = assetFileName;
        this.targetPatch =targetPatch;
    }

    /**
     * 将assets目录下指定的文件拷贝到sdcard中
     *
     * @return 是否拷贝成功, true 成功；false 失败
     * @throws IOException
     */
    public boolean copy() throws IOException {

        List<String> srcFiles = new ArrayList<>();

        //获取系统在SDCard中为app分配的目录，eg:/sdcard/Android/data/$(app's package)
        //该目录存放app相关的各种文件(如cache，配置文件等)，unstall app后该目录也会随之删除
        if (TextUtils.isEmpty(targetPatch)) {
            return false;
        }

        //读取assets/$(subDirectory)目录下的assets.lst文件，得到需要copy的文件
        String assetFile = new File(assetName).getPath();
//        List<String> assets = getAssetsList();
//        for (String asset : assets) {
//            //如果不存在，则添加到copy列表
//            if (!new File(mAppDirectory, asset).exists()) {
//                srcFiles.add(asset);
//            }
//        }

        //拷贝到App的安装目录下
        copy(assetFile);

        return true;
    }

    /**
     * 获取需要拷贝的文件列表（记录在assets/assets.lst文件中）
     *
     * @return 文件列表
     * @throws IOException
     */
    protected List<String> getAssetsList() throws IOException {

        List<String> files = new ArrayList<String>();

        InputStream listFile = mAssetManager.open(new File(assetName).getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(listFile));
        String path;
        while (null != (path = br.readLine())) {
            files.add(path);
        }

        return files;
    }

    /**
     * 执行拷贝任务
     *
     * @param asset 需要拷贝的assets文件路径
     * @return 拷贝成功后的目标文件句柄
     * @throws IOException
     */
    protected File copy(String asset) throws IOException {

        InputStream source = mAssetManager.open(new File(asset).getPath());
        File destinationFile = new File(targetPatch, assetName);
        OutputStream destination = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[1024];
        int nread;

        while ((nread = source.read(buffer)) != -1) {
            if (nread == 0) {
                nread = source.read();
                if (nread < 0)
                    break;
                destination.write(nread);
                continue;
            }
            destination.write(buffer, 0, nread);
        }
        destination.close();

        return destinationFile;
    }

}
