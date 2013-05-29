package es.upm.dit.gsi.semantic.similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class Repository {

	private Model model;

	// local rdf file name
	private String fileName;

	// remote sparql service url
	private String remoteUrl;

	// construct sparql query file
	private String consQueryFile;

	// resource sparql query file
	private String resQueryFile;

	// rdf resource name
	private String resName;

	// resource url list
	private List<Resource> resList = null;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	// read model from local file
	public Model getModelFromLocal() {
		setModel(readFromXML(fileName));
		return getModel();
	}

	// TODO:read model from remote server
	public Model getModelFromRemote() {

		File queryFile = new File(this.getConsQueryFile());
		SparqlClient client = SparqlClient.getSparqlClient();
		Query query = client.getQueryFromFile(queryFile);
		Model constructModel = client.executeConstructQuery(query, getModel());
		setModel(constructModel);
		return getModel();

	}

	public List<Resource> getResList() {
		if (resList == null) {
			SparqlClient client = SparqlClient.getSparqlClient();
			File queryFile = new File(this.getResQueryFile());
			Query query = client.getQueryFromFile(queryFile);
			ResultSet rset = client.executeSelectQuery(query, getModel());
			setResList(client.asResourceList(rset, resName));
		}
		return resList;
	}

	public void setResList(List<Resource> resourceList) {
		this.resList = resourceList;
	}

	public String getModelFile() {
		return fileName;
	}

	public void setModelFile(String modelFile) {
		this.fileName = modelFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getConsQueryFile() {
		return consQueryFile;
	}

	public void setConsQueryFile(String consQueryFile) {
		this.consQueryFile = consQueryFile;
	}

	public String getResQueryFile() {
		return resQueryFile;
	}

	public void setResQueryFile(String resQueryFile) {
		this.resQueryFile = resQueryFile;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	// writing the RDF model to XML file
	public static void writeToXML(Model model, String fileName) {

		File file = null;
		FileOutputStream out = null;
		try {
			file = new File(fileName);
			out = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			model.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// reading the RDF model from the XML file
	public static Model readFromXML(String fileName) {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + fileName
					+ " not found");
		}

		model.read(in, "");
		return model;
	}

	public static Model readFromXML(InputStream in) {
		Model model = ModelFactory.createDefaultModel();
		model.read(in, "");
		return model;
	}

	// writing the json to the file for generating taxonomy graph
	public static void writeJSON(String fileName, JSONObject json) {

		try {
			FileWriter file = new FileWriter(fileName);
			file.write(json.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read json from the file
	public static JSONObject readJSON(String fileName) {
		JSONObject jsonObject = null;
		try {
			FileReader reader = new FileReader(fileName);
			String jsonString = IOUtils.toString(reader);
			jsonObject = JSONObject.fromObject(jsonString);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}