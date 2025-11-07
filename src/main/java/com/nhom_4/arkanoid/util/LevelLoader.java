package com.nhom_4.arkanoid.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nhom_4.arkanoid.util.Pair; 

public class LevelLoader {

    public static Pair<Integer, Integer>[][] loadMap(String filename) {
    List<List<Pair<Integer, Integer>>> rows = new ArrayList<>();
    
    // Đường dẫn đầy đủ, bắt đầu từ gốc Classpath
    // Ví dụ: /levels/level1.csv
    String resourcePath = "/levels/" + filename; 

    // SỬ DỤNG CLASS.getResourceAsStream() VÀ ĐƯỜNG DẪN TUYỆT ĐỐI (GIỐNG CÁCH AUDIO HOẠT ĐỘNG)
    // Audio dùng Class.getResource() trả về URL, ta dùng Class.getResourceAsStream() trả về InputStream
    java.io.InputStream is = LevelLoader.class.getResourceAsStream(resourcePath);
    
    try {
        if (is == null) {
            // Nếu stream là null, ném ra lỗi chỉ rõ đường dẫn đã tìm kiếm
            throw new IOException("Tài nguyên không tồn tại trong Classpath: " + resourcePath);
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";", -1); 
                List<Pair<Integer, Integer>> row = new ArrayList<>();
                
                for (String val : values) {
                    row.add(getBrickData(val.trim())); 
                }
                rows.add(row);
            }
        } // br tự đóng
        
    } catch (Exception e) {
        System.err.println("LỖI: Không thể tải map: " + resourcePath);
        e.printStackTrace();
        return null; 
    }
    
    return convertListToPairArray(rows);
    }
    
    
    private static Pair<Integer, Integer> getBrickData(String key) {
        if (key.isEmpty() || key.equals("0")) {
            return null; 
        }
        
        switch (key) {
            // Gạch 1 Máu (Health 1)
            case "A1": return new Pair<>(1, 1);
            case "A2": return new Pair<>(1, 2);
            case "A3": return new Pair<>(1, 3);
            case "A4": return new Pair<>(1, 4);
            
            // Gạch 2 Máu (Health 2)
            case "B1": return new Pair<>(2, 1);
            case "B2": return new Pair<>(2, 2);
            case "B3": return new Pair<>(2, 3);
            case "B5": return new Pair<>(2, 5);
            default: 
                System.err.println("CẢNH BÁO: Ký tự map không xác định: " + key);
                return new Pair<>(1, 1);
        }
    }

    @SuppressWarnings("unchecked")
    private static Pair<Integer, Integer>[][] convertListToPairArray(List<List<Pair<Integer, Integer>>> rows) {
        if (rows.isEmpty()) return new Pair[0][0];
        int numRows = rows.size();
        int numCols = rows.get(0).size(); 
        
        Pair<Integer, Integer>[][] mapArray = new Pair[numRows][numCols];
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (j < rows.get(i).size()) {
                    mapArray[i][j] = rows.get(i).get(j);
                } else {
                    mapArray[i][j] = null;
                }
            }
        }
        return mapArray;
    }
}