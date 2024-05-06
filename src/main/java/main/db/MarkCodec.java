package main.db;

import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import student.Mark;

public class MarkCodec implements CollectibleCodec<Mark> {

    private final Codec<Document> documentCodec;

    public MarkCodec(Codec<Document> documentCodec) {
        this.documentCodec = documentCodec;
    }

    @Override
    public void encode(BsonWriter writer, Mark value, EncoderContext encoderContext) {
        Document document = new Document();
        document.put("value", value.getValue());
        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<Mark> getEncoderClass() {
        return Mark.class;
    }

    @Override
    public Mark decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        return new Mark(document.getDouble("value"));
    }

    @Override
    public Mark generateIdIfAbsentFromDocument(Mark document) {
        return document;
    }

    @Override
    public boolean documentHasId(Mark document) {
        return true;
    }

    @Override
    public BsonValue getDocumentId(Mark document) {
        return new BsonString(String.valueOf(document.getValue()));
    }
}