package de.yalama.hackerrankindexer.DocumentGenerator.Controller;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.DocumentGenerator.Service.DocumentGeneratorService;
import de.yalama.hackerrankindexer.Security.model.CollectionWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/downloadSubmissions")
public class DocumentGeneratorController {


    private DocumentGeneratorService documentGeneratorService;

    public DocumentGeneratorController(DocumentGeneratorService documentGeneratorService) {
        this.documentGeneratorService = documentGeneratorService;
    }

    @PostMapping
    public List<DownloadFile> downloadSubmissions(@RequestBody CollectionWrapper<Long> submissionIDs) {
        return this.documentGeneratorService.downloadSubmissionsFromCollection(submissionIDs.getCollection());
    }
}
