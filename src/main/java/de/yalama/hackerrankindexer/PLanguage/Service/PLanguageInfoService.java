package de.yalama.hackerrankindexer.PLanguage.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PLanguageInfoService {

    private Map<String, String> fileExtensionByLanguageName;
    private Map<String, String> displayNameMap;


    public PLanguageInfoService() {
        this.initFileExtensionsMap();
        this.initDisplayNameMap();
    }

    public String getPLanguageDisplayName(PLanguage pLanguage) {
        return this.getPLanguageDisplayName(pLanguage.getLanguage());
    }

    public String getPLanguageDisplayName(String languageName) {
        return this.displayNameMap.get(languageName);
    }

    public String getFileExtension(PLanguage pLanguage) {
        return this.getFileExtension(pLanguage.getLanguage());
    }

    public String getFileExtension(String languageName) {
        return this.fileExtensionByLanguageName.get(languageName);
    }

    private void initFileExtensionsMap() {
        this.fileExtensionByLanguageName = new HashMap<String, String>();
        this.fileExtensionByLanguageName.put("java8", "java");
        this.fileExtensionByLanguageName.put("java", "java");
        this.fileExtensionByLanguageName.put("pypy", "py");
        this.fileExtensionByLanguageName.put("python3", "py3");
        this.fileExtensionByLanguageName.put("oracle", "sql");
        this.fileExtensionByLanguageName.put("mysql", "sql");
        this.fileExtensionByLanguageName.put("text", "txt");
        this.fileExtensionByLanguageName.put("tsql", "sql");
        this.fileExtensionByLanguageName.put("cpp", "cpp");
        this.fileExtensionByLanguageName.put("cpp14", "cpp");
    }

    private void initDisplayNameMap() {
        this.displayNameMap = new HashMap<String, String>();
        this.displayNameMap.put("java8", "Java 8");
        this.displayNameMap.put("java", "Java");
        this.displayNameMap.put("pypy", "Python 2");
        this.displayNameMap.put("python3", "Python 3");
        this.displayNameMap.put("oracle", "SQL (Oracle)");
        this.displayNameMap.put("mysql", "SQL (MySQL)");
        this.displayNameMap.put("text", "Plaintext");
        this.displayNameMap.put("tsql", "T-SQL");
        this.displayNameMap.put("cpp", "C++");
        this.displayNameMap.put("cpp14", "C++ 14");
    }
}
