package EvgenUlianov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main {

    private static final String PATH = "C:\\Users\\EUlyanov\\Documents\\Учеба JAVA\\JavaCore\\Games";

    public static void main(String[] args) {

        System.out.println("Задача 1: Установка");
        System.out.println("Работаем в папке:");
        System.out.println(PATH);

        File catalog = new File(PATH);
        if (!catalog.exists()) {
            System.out.println("указанная папка не существует");
            return;
        }
        StringBuilder logs = new StringBuilder();
        String nameOfLogFile = "";

        {
            // 1 level: src, res, savegames, temp
            File src = findOrCreateDirectory(catalog, "src", logs);
            if ((src != null && src.exists() && src.isDirectory())) {
                //2 level: main, test
                File main = findOrCreateDirectory(src, "main", logs);
                if ((main != null && main.exists() && main.isDirectory())) {
                    //3 level: main, test
                    findOrCreateFile(main, "Main.java", logs);
                    findOrCreateFile(main, "Utils.java", logs);
                }
                findOrCreateDirectory(src, "test", logs);
            }
            File res = findOrCreateDirectory(catalog, "res", logs);
            if ((res != null) && res.exists() && res.isDirectory()) {
                //2 level: drawables, vectors, icons
                findOrCreateDirectory(res, "drawables", logs);
                findOrCreateDirectory(res, "vectors", logs);
                findOrCreateDirectory(res, "icons", logs);
            }
            findOrCreateDirectory(catalog, "savegames", logs);
            File temp = findOrCreateDirectory(catalog, "temp", logs);
            if ((temp != null && temp.exists() && temp.isDirectory())) {
                //3 level: main, test
                File tempTxt = findOrCreateFile(temp, "temp.txt", logs);
                if ((tempTxt != null))
                    nameOfLogFile = tempTxt.getAbsolutePath();
            }
        }
        String text = logs.toString();

        if (!nameOfLogFile.isEmpty()) {
            try (FileWriter writer = new FileWriter(nameOfLogFile, false)) {
                // запись всей строки
                writer.write(text);
                // запись по символам
                writer.append('\n');
                writer.append('!');
                // дозаписываем и очищаем буфер
                writer.flush();
                System.out.println("Закончили, проверяем результат!");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println(text);
        }


    }

    public static File findOrCreateDirectory(File parent, String name, StringBuilder logs) {
        String fullName = parent.getAbsolutePath() + "\\" + name;
        File file = new File(fullName);
        if (file.exists()) // do nothing
            return file;

        Date date = new Date();
        if (file.mkdir()) {
            logs.append("Directory created (" + date + "): " + fullName + '\n');
            return file;
        } else {
            logs.append("Failed to create directory (" + date + "): " + fullName + '\n');
            return null;
        }
    }

    public static File findOrCreateFile(File parent, String name, StringBuilder logs) {
        String fullName = parent.getAbsolutePath() + "\\" + name;
        File file = new File(fullName);
        if (file.exists()) // do nothing
            return file;

        Date date = new Date();
        try {
            if (file.createNewFile()) {
                logs.append("File created (" + date + "): " + fullName + '\n');
                return file;
            } else {
                logs.append("Failed to create file (" + date + "): " + fullName + '\n');
                return null;
            }
        } catch (IOException ex) {
            logs.append("Failed to create file (" + date + "): " + fullName + '\n');
            logs.append(ex.getMessage() + '\n');
            return null;
        }
    }
}
