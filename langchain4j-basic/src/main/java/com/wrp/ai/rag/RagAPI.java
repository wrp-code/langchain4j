package com.wrp.ai.rag;

import com.wrp.ai.service.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wrp
 * @since 2025-03-19 21:14
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("rag")
public class RagAPI {

    final Assistant assistant;
    final EmbeddingStore<TextSegment> embeddingStore;
    final EmbeddingModel embeddingModel;

    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        return assistant.chat(message);
    }

    @GetMapping("load")
    public String loadDocument() {
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\temp");
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        EmbeddingStoreIngestor ingester = EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                // maxSegmentSizeInChars 文档段的最大字符数
                // maxOverlapSizeInChars 文档段的最大重叠字符数
                .documentSplitter(new DocumentByLineSplitter(100, 40))
                .build();
        ingester.ingest(documents);
        return "ok";
    }
}
