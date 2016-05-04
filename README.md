# Content-Enrichment-in-Text-Retrieval

#The objective of the Project
It is to significantly enrich the metadata, and automatically extracted text and entities from the TREC Polar Dataset, and to make the dataset easily to relate to and to interact with. To do so, you will apply and leverage knowledge gained from context extraction, metadata, information similarity and clustering, and from the named entity recognition concepts.
#Key Steps
1. Context Extraction Enrichment – We applied the Tag Ratios algorithm to identify text, and constructed a Tika parser to extract Measurement mentions from text automatically.
2. Metadata Enrichment – We applied the GROBID journal parser with Tika, and extract TEI metadata, and also scientific publication metadata using the Google Scholar API to develop a network of related scientific publications to the Polar dataset, and to map publications to the data. In addition, we classified the data using a common Earth science domain model, ontology, called SWEET, for
Semantic Web for Earth and Environmental Terminology (http://sweet.jpl.nasa.gov/). We also createed Digital Object Identifiers (DOIs)
for the data.
3. Information Similarity and Clustering – We created clusters of the Polar data using the enriched measurements extracted, and using the enriched metadata, and demonstrated information using Data-Driven-Documents visualizations after ingesting data into Apache Solr.
4. Named Entity Recognition (NER) – We applied geospatial NER using the GeoTopicParser in Apache Tika and using the MEMEX GeoParser tools
