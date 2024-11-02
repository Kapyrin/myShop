package kapyrin.myshop.servlet.util;


import jakarta.servlet.ServletContext;
import kapyrin.myshop.exception.SaveToFileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import kapyrin.myshop.servlets.util.utilinterface.GetServletContext;
import kapyrin.myshop.servlets.util.utilinterface.SaveToFile;

public enum ReportWriter implements SaveToFile, GetServletContext {
    INSTANCE;

    private ServletContext getServletContext;

    public void setServletContext(ServletContext servletContext) {
        this.getServletContext = servletContext;
    }

    public File saveStringToFile(String content, String fileName) {
        String realPath = getServletContext.getRealPath("/report");
        String filePath = Paths.get( realPath,fileName + java.time.LocalDate.now()) + ".txt";
        System.out.println("File saving to" + realPath);

        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("saved string to file");
        } catch (IOException e) {
            throw new SaveToFileException("Could not save string to file", e);
        }
        return file;
    }
}
