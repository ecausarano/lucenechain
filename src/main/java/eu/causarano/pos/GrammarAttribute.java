package eu.causarano.pos;

import org.apache.lucene.util.Attribute;

import java.util.List;
import java.util.Optional;

/**
 * Created by edoardocausarano on 23/04/2017.
 */
public interface GrammarAttribute extends Attribute {

    Optional<Grammar> getGrammarAtOffset(int offset);

    List<Grammar> getGrammar();

    void appendGrammar(Grammar pos);
}
