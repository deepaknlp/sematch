from sematch.semantic.graph import UMLSDataTransform, Taxonomy
from sematch.semantic.similarity import UMLSConceptSimilarity
import pickle
import os
pickle_obj_path='/data/guptadk/Documents/CHiQA-Question-Understanding/data/KG/concept-obj-SNOMEDCT-US-MSH.pkl'
if os.path.exists(pickle_obj_path):
    print("Loading concept object...")
    with open(pickle_obj_path, 'rb') as handle:
        concept = pickle.load(handle)
else:
    concept = UMLSConceptSimilarity(Taxonomy(UMLSDataTransform(
        path_to_MRCONSO_file='/data/guptadk/Documents/CHiQA-Question-Understanding/data/KG/2023AA/META/MRCONSO.RRF',
        path_to_MRREL_file='/data/guptadk/Documents/CHiQA-Question-Understanding/data/KG/2023AA/META/MRREL.RRF'
        )))
    print("Saving concept object...")
    # Open a file and use dump()
    with open(pickle_obj_path, 'wb') as file:
        # A new file will be created
        pickle.dump(concept, file)

print(concept.similarity('C0017601', 'C0232197', 'path'))

# export PYTHONPATH=$PYTHONPATH:/data/guptadk/Documents/sematch