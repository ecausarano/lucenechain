package eu.causarano.pos;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by edoardocausarano on 23/04/2017.
 */
public class GrammarAttributeImpl extends AttributeImpl implements GrammarAttribute {

    private static final Logger logger = LoggerFactory.getLogger(GrammarAttributeImpl.class);

    private ThreadLocal<LinkedList<Grammar>> pos = new ThreadLocal<>();

    public GrammarAttributeImpl() {
        pos.set(new LinkedList<>());
    }

    public void clear() {
    }

    @Override
    public void end() {
        pos.get().clear();
    }

    public void reflectWith(AttributeReflector reflector) {
        reflector.reflect(GrammarAttribute.class, "Grammar item", pos.get());
    }

    public void copyTo(AttributeImpl target) {

    }

    @Override
    public Optional<Grammar> getGrammarAtOffset(int offset) {
        LinkedList<Grammar> pos = this.pos.get();
        if (pos.size() == 0) {
            return Optional.empty();
        } else if (offset == 0) {
            return Optional.of(pos.getLast());
        } else if (pos.size() >= offset -1) {
            return Optional.of(pos.getLast());
        } else if (pos.size() > offset - 1) {
            return Optional.of(pos.get(pos.size() - offset + 1));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Grammar> getGrammar() {
        return Collections.unmodifiableList(pos.get());
    }

    @Override
    public void appendGrammar(Grammar pos) {
        this.pos.get().add(pos);
    }
}
