from sematch.semantic.graph import UMLSDataTransform, Taxonomy
from sematch.semantic.similarity import UMLSConceptSimilarity

concept = UMLSConceptSimilarity(Taxonomy(UMLSDataTransform(
    path_to_MRCONSO_file='dataset/umls/MRCONSO.RRF',
    path_to_MRREL_file='dataset/umls/MRREL.RRF'
    )))
print(concept.similarity('C0017601', 'C0232197', 'path'))

