package de.yalama.hackerrankindexer.DocumentGenerator.Controller;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.DocumentGenerator.Service.DocumentGeneratorService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/downloadSubmissions")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentGeneratorController {


    private DocumentGeneratorService documentGeneratorService;

    public DocumentGeneratorController(DocumentGeneratorService documentGeneratorService) {
        this.documentGeneratorService = documentGeneratorService;
    }

    @PostMapping
    public List<DownloadFile> downloadSubmissions(Collection<Long> submissionIDs) {
        return this.documentGeneratorService.downloadSubmissionsFromCollection(submissionIDs);
    }
}
