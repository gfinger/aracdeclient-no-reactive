package org.makkiato.arcadeclient.request;

import org.makkiato.arcadeclient.base.ArcadeClient;
import org.makkiato.arcadeclient.base.Document;
import org.makkiato.arcadeclient.base.EdgeBase;
import org.makkiato.arcadeclient.base.EmbeddedDocumentBase;
import org.makkiato.arcadeclient.base.VertexBase;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ArcadeTemplate {
    private final ArcadeClient arcadeClient;

    public ArcadeTemplate(ArcadeClient arcadeClient) {
        this.arcadeClient = arcadeClient;
    }

    public <T extends VertexBase> T insertVertex(T vertex) throws JsonMappingException, JsonProcessingException {
        var request = new SqlCommandRequest(arcadeClient.getRestClient());
        var payload = new CommandPayload(String.format("create vertex %s content %s",
                vertex.getType(),
                arcadeClient.getMapper().writeValueAsString(vertex)));
        var result = request.apply(payload, arcadeClient.getDbName()).result();
        var mapper = arcadeClient.getMapper();
        var objetAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.valueToTree(result[0]));
        return (T) mapper.readValue(objetAsString, vertex.getClass());
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
