package com.ys.lwctestapp.tools;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author lwc
 * 2019/11/26
 * 描述：$des$
 */
public class FileServerTools {

    private static final String PATH_SDCARD = "/mnt/sdcard";
    public static final String FOLDER_SERVER = "/MachineData/Server";
    public static final String FILENAME_SERVER_MACHINE = "Machine.txt";
    private static final String DEFAULT_ID = "88888888";
    private static final String DEFAULT_KEY = "))))))))))))))))))))))))))))))))))))))))))))";

    public static String getAesKey() {
        String machineInfo = null;
        String strKey = DEFAULT_KEY;
        String strData = readFileLineUseful(FOLDER_SERVER, FILENAME_SERVER_MACHINE);
        if (null == strData) {
            return strKey;
        }
        if ((!strData.startsWith("AAA")) || (!strData.endsWith("BBB")) || (!strData.contains("|"))) {
            return strKey;
        }
        int iStart = strData.indexOf("|");
        int iEnd = strData.lastIndexOf("|");
        String msgBody = strData.substring(iStart, iEnd + 1);
        String check = getCheckXor(msgBody);
        if (check.equals(getCheckData(strData, "AAA", "BBB"))) {
            machineInfo = strData;
        } else {
            deleteFileContentByLine(strData, FOLDER_SERVER + "/" + FILENAME_SERVER_MACHINE);
        }

        if (TextUtils.isEmpty(machineInfo)) {
            return strKey;
        }
        String[] strArr = machineInfo.split("\\|");
        if ((null == strArr) || (strArr.length < 4)) {
            return strKey;
        }
        return strArr[2].trim();
    }

    //AAA|1701070013|74647E337EXXXXXX|53BBB   53：XOR校验
    public static String getMachineID() {
        String machineInfo = null;
        String strMachineID = DEFAULT_ID;
        String strData = readFileLineUseful(FOLDER_SERVER, FILENAME_SERVER_MACHINE);
        if (null == strData) {
            return machineInfo;
        }
        if ((!strData.startsWith("AAA")) || (!strData.endsWith("BBB")) || (!strData.contains("|"))) {
            return machineInfo;
        }
        int iStart = strData.indexOf("|");
        int iEnd = strData.lastIndexOf("|");
        String msgBody = strData.substring(iStart, iEnd + 1);
        String check = getCheckXor(msgBody);
        if (check.equals(getCheckData(strData, "AAA", "BBB"))) {
            machineInfo = strData;
        } else {
            deleteFileContentByLine(strData, FOLDER_SERVER + "/" + FILENAME_SERVER_MACHINE);
        }

        if (TextUtils.isEmpty(machineInfo)) {
            return strMachineID;
        }
        String[] strArr = machineInfo.split("\\|");
        if ((null == strArr) || (strArr.length < 4)) {
            return strMachineID;
        }
        return strArr[1].trim();
    }

    //保存机器ID和密钥    AAA|1701070013|74647E337EXXXXXX|53BBB   53：XOR校验
    public static boolean saveMachineInfo(String machineInfo) {
        boolean bRet = false;
        if (!existsOrCreateFile(FOLDER_SERVER, FILENAME_SERVER_MACHINE)) {
            return bRet;
        }
        String msgBody = "|" + machineInfo + "|";
        String check = getCheckXor(msgBody);
        String msg = "AAA" + msgBody + check + "BBB";
        bRet = writeFileByLine(false, FOLDER_SERVER, FILENAME_SERVER_MACHINE, msg);

        return bRet;
    }

    private static boolean deleteFileContentByLine(String deleteLineData, String filePathAndName) {
        boolean bRet = false;
        String mStrRootPath = getExternalStorageDirectory();
        String mFilePathAndName = filePathAndName;
        if (!filePathAndName.startsWith(mStrRootPath)) {
            mFilePathAndName = mStrRootPath + "/" + filePathAndName;
        }
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        boolean bReadSuccess = false;
        try {
            File mFile = new File(mFilePathAndName.trim());
            if ((!mFile.exists()) || (!mFile.isFile())) {
                return bRet;
            }
            br = new BufferedReader(new FileReader(mFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (!line.equals(deleteLineData)) {
                    sb.append(line + "\n");
                }
            }
            bReadSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!bReadSuccess) {
            return bRet;
        }

        FileWriter fw = null;
        try {
            String strData = sb.toString();
            //createFile(mDirPath,fileName);
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            fw = new FileWriter(mFilePathAndName, false);
            String[] strArry = strData.split("\\n");
            for (String data : strArry) {
                fw.write(data + "\n");
            }
            fw.close();

            bRet = true;
        } catch (Exception e) {

        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bRet;
    }


    private static String getCheckData(String msg, String start, String end) {
        if ((null == msg) || (!(msg.startsWith(start) && msg.endsWith(end)))) {
            return null;
        }
        int indexH = msg.lastIndexOf("|");
        int indexEnd = msg.lastIndexOf(end);
        if (indexEnd <= indexH) {
            return null;
        }
        String check = msg.substring(indexH + 1, indexEnd);
        return check;
    }

    /**
     * 读取文本文件
     *
     * @param fileName
     * @return
     */
    private static String readFileLineUseful(String filePath, String fileName) {
        if ((TextUtils.isEmpty(filePath)) || (TextUtils.isEmpty(fileName))) {
            return null;
        }
        String mFilePathAndName = getFilePathAndName(filePath, fileName);
        if (TextUtils.isEmpty(mFilePathAndName)) {
            return null;
        }
        StringBuilder mStringBuilder = new StringBuilder();

        BufferedReader br = null;
        try {

            File file = new File(mFilePathAndName);
            if ((!file.exists()) || (!file.isFile())) {
                return null;
            }

            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                if ((!TextUtils.isEmpty(line.trim())) && (line.trim().length() > 5)) {
                    mStringBuilder.append(line);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mStringBuilder.toString().trim();
    }

    /**
     * 写入内容到txt文本中
     * data为内容
     */
    private static boolean writeFileByLine(boolean append, String filePath, String fileName, String data) {
        boolean bRet = false;
        if (!existsOrCreateFile(filePath, fileName)) {
            return bRet;
        }

        String mFilePathAndName = getFilePathAndName(filePath, fileName);

        data = data + "\n";

        FileWriter fw = null;

        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            fw = new FileWriter(mFilePathAndName, append);
            fw.write(data);

            bRet = true;

        } catch (Exception e) {

        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return bRet;
    }

    private static String getCheckXor(String strData) {
        byte[] datas = strData.getBytes();
        byte temp = datas[0];

        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }
        String iRet = String.valueOf((int) temp);
        return iRet;
    }

    private static boolean existsOrCreateFile(String filePath, String fileName) {
        boolean bRet = false;
        if ((TextUtils.isEmpty(filePath)) || (TextUtils.isEmpty(fileName))) {
            return bRet;
        }
        String mDirPath = filePath;
        if (TextUtils.isEmpty(mDirPath)) {
            return bRet;
        }
        String rootPath = getExternalStorageDirectory();
        if (!mDirPath.startsWith(rootPath)) {
            if (mDirPath.startsWith("/")) {
                mDirPath = rootPath + mDirPath;
            } else {
                mDirPath = rootPath + "/" + mDirPath;
            }

        }
        try {
            File mDir = new File(mDirPath.trim());
            if ((!mDir.exists()) || (!mDir.isDirectory())) {
                mDir.mkdirs();
            }
            mDir = new File(mDirPath.trim());
            if ((mDir.exists()) && (mDir.isDirectory())) {
                String mFilePathAndName = getFilePathAndName(filePath, fileName);
                File mFile = new File(mFilePathAndName.trim());
                if ((!mFile.exists()) || (!mFile.isFile())) {
                    mFile.createNewFile();
                    mFile = new File(mFilePathAndName.trim());
                    if ((mFile.exists()) && (mFile.isFile())) {
                        bRet = true;
                    }
                } else if ((mFile.exists()) && (mFile.isFile())) {
                    bRet = true;
                } else {

                }
            }
        } catch (IOException e) {
            Log.e("file","IOException message=>"+e.getMessage());
            e.printStackTrace();
        }

        return bRet;
    }

    private static String getFilePathAndName(String filePath, String fileName) {
        if ((TextUtils.isEmpty(filePath)) || (TextUtils.isEmpty(fileName))) {
            return null;
        }
        StringBuilder mStringBuilder = new StringBuilder();

        String mStrRootPath = getExternalStorageDirectory();
        if (!filePath.startsWith(mStrRootPath)) {
            mStringBuilder.append(mStrRootPath);
        }
        if ((!(mStringBuilder.toString()).endsWith("/")) && (!filePath.startsWith("/"))) {
            mStringBuilder.append("/");
        }
        mStringBuilder.append(filePath);

        if (!(mStringBuilder.toString()).endsWith("/")) {
            mStringBuilder.append("/");
        }
        mStringBuilder.append(fileName);

        return mStringBuilder.toString();
    }

    /**
     * 获取U盘或是sd卡的路径
     *
     * @return
     */
    private static String getExternalStorageDirectory() {

        String dir = new String();

        try {
            File file = new File(PATH_SDCARD);
            if (file.exists() && file.isDirectory()) {
                dir = PATH_SDCARD;
                return dir;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) continue;
                if (line.contains("asec")) continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        dir = columns[1];
                    }
                }
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dir;
    }
}
