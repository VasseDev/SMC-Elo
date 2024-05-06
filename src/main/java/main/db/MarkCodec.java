package main.db;

import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import student.Mark;

/**
 * MarkCodec is a codec for encoding and decoding Mark objects to and from BSON.
 * It implements the CollectibleCodec interface, which extends the Codec interface and adds methods for working with types that have an Id.
 */
public class MarkCodec implements CollectibleCodec<Mark> {

    private final Codec<Document> documentCodec;

    /**
     * Constructs a new MarkCodec with the specified Document codec.
     *
     * @param documentCodec the codec for Document objects
     */
    public MarkCodec(Codec<Document> documentCodec) {
        this.documentCodec = documentCodec;
    }

    /**
     * Encodes a Mark object into a BSON writer.
     *
     * @param writer the BSON writer
     * @param value the Mark object to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, Mark value, EncoderContext encoderContext) {
        Document document = new Document();
        document.put("value", value.getValue());
        documentCodec.encode(writer, document, encoderContext);
    }

    /**
     * Returns the class that this codec encodes.
     *
     * @return the class that this codec encodes
     */
    @Override
    public Class<Mark> getEncoderClass() {
        return Mark.class;
    }

    /**
     * Decodes a Mark object from a BSON reader.
     *
     * @param reader the BSON reader
     * @param decoderContext the decoder context
     * @return the decoded Mark object
     */
    @Override
    public Mark decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        return new Mark(document.getDouble("value"));
    }

    /**
     * Generates an Id for a Mark object if it is absent from the document.
     *
     * @param document the Mark object
     * @return the Mark object with a generated Id if it was absent
     */
    @Override
    public Mark generateIdIfAbsentFromDocument(Mark document) {
        return document;
    }

    /**
     * Checks if a document has an Id.
     *
     * @param document the Mark object
     * @return true if the document has an Id, false otherwise
     */
    @Override
    public boolean documentHasId(Mark document) {
        return true;
    }

    /**
     * Returns the Id of a document.
     *
     * @param document the Mark object
     * @return the Id of the document
     */
    @Override
    public BsonValue getDocumentId(Mark document) {
        return new BsonString(String.valueOf(document.getValue()));
    }
}