package org.makkiato.arcadeclient.request;

import java.util.List;

import org.makkiato.arcadeclient.base.ArcadeClient;
import org.makkiato.arcadeclient.base.Document;
import org.makkiato.arcadeclient.base.DocumentBase;
import org.makkiato.arcadeclient.base.EdgeBase;
import org.makkiato.arcadeclient.base.EmbeddedDocumentBase;
import org.makkiato.arcadeclient.base.VertexBase;
import org.makkiato.arcadeclient.query.Select;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ArcadeTemplate {
    private final ArcadeClient arcadeClient;

    public ArcadeTemplate(ArcadeClient arcadeClient) {
        this.arcadeClient = arcadeClient;
    }

    public <T extends DocumentBase> T insertDocument(T document) throws JsonMappingException, JsonProcessingException {
        var request = new SqlCommandRequest(arcadeClient.getRestClient());
        var payload = new CommandPayload(String.format("create vertex %s content %s",
                document.getType(),
                arcadeClient.getMapper().writeValueAsString(document)));
        var result = request.apply(payload, arcadeClient.getDbName()).result();
        var mapper = arcadeClient.getMapper();
        var objetAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.valueToTree(result[0]));
        return (T) mapper.readValue(objetAsString, document.getClass());
    }

    public <E extends EdgeBase, F extends VertexBase, T extends VertexBase> E createEdge(F from, T to, Class<E> edge)
            throws JsonProcessingException, IllegalArgumentException {
        var request = new SqlCommandRequest(arcadeClient.getRestClient());
        var payload = new CommandPayload(
                String.format("create edge %s from %s to %s",
                        getDocumentType(edge), from.getRid(), to.getRid()));
        var result = request.apply(payload, arcadeClient.getDbName()).result();
        var mapper = arcadeClient.getMapper();
        var objetAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.valueToTree(result[0]));
        return (E) mapper.readValue(objetAsString, edge);
    }

    public <T extends DocumentBase> List<T> selectDocument(Select select, Class<T> clazz) throws JsonProcessingException, IllegalArgumentException {
        var request = new SqlQueryRequest(arcadeClient.getRestClient());
        var payload = new CommandPayload(select.toString());
        var result = request.apply(payload, arcadeClient.getDbName()).result();
        var mapper = arcadeClient.getMapper();
        var objetAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.valueToTree(result));
        var listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(objetAsString, listType); 
    }

    private String getDocumentType(Class<? extends EmbeddedDocumentBase> clazz) {
        Assert.notNull(clazz, "Class must not be empty");
        @SuppressWarnings("null")
        var annotatedName = AnnotationUtils.getAnnotation(clazz, Document.class);
        var documentType = annotatedName != null && annotatedName.value() != null && !annotatedName.value().isEmpty()
                ? annotatedName.value()
                : clazz.getSimpleName();
        return documentType;
    }
}
