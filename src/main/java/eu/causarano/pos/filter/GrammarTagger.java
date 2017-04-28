package eu.causarano.pos.filter;

import eu.causarano.pos.Grammar;
import eu.causarano.pos.GrammarAttribute;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static eu.causarano.pos.Grammar.*;

public class GrammarTagger extends TokenFilter {

    private static final Logger logger = LoggerFactory.getLogger(GrammarTagger.class);

    private final GrammarAttribute grammarAttribute;
    private final CharTermAttribute termAttribute;
    private State state;

    public GrammarTagger(TokenStream input) {
        super(input);

        termAttribute = addAttribute(CharTermAttribute.class);
        grammarAttribute = addAttribute(GrammarAttribute.class);
    }

    public final boolean incrementToken() throws IOException {
        restoreState(state);
        boolean incremented = input.incrementToken();
        if (incremented) {
            String term = termAttribute.toString();
            // crude dumb logic
            switch (term) {
                case "the":
                    grammarAttribute.appendGrammar(ARTICLE);
                    break;
                default:
                    Optional<Grammar> atOffset = grammarAttribute.getGrammarAtOffset(1);
                    logger.debug("previous grammar was={}", atOffset.orElse(NONE));
                    if (atOffset.isPresent()) {
                        grammarAttribute.appendGrammar(atOffset.get().equals(ARTICLE) ? NOUN : WORD);
                    } else {
                        grammarAttribute.appendGrammar(WORD);
                    }
            }
            state = captureState();
            return true;
        } else {
            return false;
        }
    }
}

