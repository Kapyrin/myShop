package kapyrin.myshop.servlets.util.utilinterface;

import java.io.File;

public interface SaveToFile {
    File saveStringToFile(String content, String fileName);
}
