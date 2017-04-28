import eu.causarano.pos.GrammarAttribute;
import eu.causarano.pos.filter.GrammarTagger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.LuceneTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by edoardocausarano on 23/04/2017.
 */
public class GrammarFilterTest extends LuceneTestCase {

    private static final Logger logger = LoggerFactory.getLogger(GrammarFilterTest.class);

    public void testPOSFilter() {
        Analyzer analyzer = new Analyzer() {
            protected TokenStreamComponents createComponents(String fieldName) {
                StandardTokenizer tokenizer = new StandardTokenizer();
                GrammarTagger grammarTagger = new GrammarTagger(tokenizer);

                return new TokenStreamComponents(tokenizer, grammarTagger);
            }
        };

        try (TokenStream stream = analyzer.tokenStream("unused", "this is the real thing")) {
            stream.reset();
            while (stream.incrementToken()) {

                GrammarAttribute grammarAttribute = stream.getAttribute(GrammarAttribute.class);
                CharTermAttribute termAttribute = stream.getAttribute(CharTermAttribute.class);
                logger.debug("Grammar history at \"{}\": {}", termAttribute.toString(), grammarAttribute.getGrammar());
            }
            stream.end();
        } catch (IOException e) {
            // nope
        }


    }
}
