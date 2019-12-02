package com.sxf.log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteLog {
    public void WriteLogByDate(String string) {
        String LogPathOld = "D:\\logCase\\";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        String LogPath = LogPathOld + dateStr + ".txt";
        File f = new File(LogPath);
        System.out.println(LogPath);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(string, 0, string.length() - 1);
            out.newLine();
            out.close();
            System.out.println("日志写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
