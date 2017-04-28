## Custom Lucene Analyzer filter example

Small playground to explore the analysis API of Lucene 6.x

## End goal

In the end the library should be able to:
* tag basic grammatical elements such as articles, pronouns, verbs, gender, multiplicity, etc...
* use a matrix to infer the remaining grammatical elements (nouns, verbs, etc...)
* load another matrix to infer POS shingles (article-adjective-noun, adverb-verb, etc...)
* load another matrix to classify these POS shingles

But that's quite an ambitious feat, I'll be happy to better understand Lucene as I try implementing all this.

## License

GPLv3