package com.frozy.autil.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A collection of file-processing utilities methods
 */
@SuppressWarnings("unused")
public class FileUtil {
    /**
     * Read strings from a file. You should make sure that the content in the
     * file can be convert to a strings
     *
     * @param fileName The name of the file that is read from, it should use the full
     *                 path.
     * @return The strings read from the file
     * @throws IOException If the file doesn't exist.
     */
    public static String readStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        return readStringFromFile(file);
    }

    /**
     * Write a text string into a file.
     *
     * @param fileName The name of the file that will be written in, it should use
     *                 the full path
     * @param text     The text that will be written.
     * @throws IOException If the file doesn't exist or denies to be access
     */
    public static void writeStringToFile(String fileName, String text) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(text.getBytes());
        } finally {
            if (fos != null) {
                fos.flush();
                fos.close();
            }
        }
    }

    /**
     * Write a stream into a file.
     *
     * @param stream       the stream that will be written.
     * @param destFilePath The name of the file that will be written in. It should use
     *                     the full path.
     * @throws Exception If the file doesn't exist or denies to be access
     */
    public static void wirteStreamToFile(InputStream stream, String destFilePath) throws Exception {
        int hasRead;
        byte[] buffer = new byte[1024 << 3];
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(destFilePath));
            while ((hasRead = stream.read(buffer)) > 0) {
                fos.write(buffer, 0, hasRead);
            }
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static String readStringFromStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String text;
            while ((text = reader.readLine()) != null) {
                sb.append(text);
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void copyFile(String fromPath, String toPath) throws IOException {

        if (fromPath == null || toPath == null)
            return;

        File srcFile = new File(fromPath);
        File destFile = new File(toPath);
        copyFile(srcFile, destFile);

    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        try {
            int hasRead;
            byte[] buffer = new byte[1024 << 3];
            while ((hasRead = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, hasRead);
            }
        } finally {
            fis.close();
            fos.flush();
            fos.close();
        }
    }

    public static String readStringFromFile(File file) throws IOException {
        if (!file.exists())
            return null;
        StringBuilder outBuffer = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = br.readLine()) != null) {
                outBuffer.append(temp);
            }
            return outBuffer.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) return file.delete();
            else {
                String[] filePaths = file.list();
                for (String path : filePaths) {
                    deleteFile(filePath + File.separator + path);
                }
                return file.delete();
            }
        }
        return false;
    }

    /**
     * Get the specific files that have extension in the specific folder.
     *
     * @param folder    In which folder to get the specific files.
     * @param extension On which extension text should we based.
     * @return A string array contains the files' names that have the specific
     * extension
     */
    public static String[] getFilesWithExtension(String folder, final String extension) {
        File filesDir = new File(folder);

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extension);
            }
        };

        return filesDir.list(filter);
    }

    public static String getFilePath(Context context, Uri uri) {
        if (uri != null) {
            final String pathName = MediaStore.Images.Media.DATA;
            String[] projection = {pathName};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int imagePathIndex = cursor.getColumnIndex(pathName);
                    return cursor.getString(imagePathIndex);
                }
            } finally {
                if (cursor != null) cursor.close();
            }
        }
        return null;
    }
}
