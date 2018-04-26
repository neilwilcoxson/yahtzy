package com.neilwilcoxson.yahtzy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HighScores")
@XmlAccessorType(XmlAccessType.FIELD)
public class HighScores {
	public static final String FILENAME = "high_scores.xml"; 

	@XmlElementWrapper
	@XmlElement(name = "Record")
	protected ArrayList<Record> recordList;
	
	public void sort() {
		Collections.sort(recordList, new Record());
	}
	
	public HighScores() {
		recordList = new ArrayList<Record>();
	}
	
	public void add(Record r) {
		recordList.add(r);
	}

	public ArrayList<Record> getRecordList() {
		return recordList;
	}

	public void setRecordList(ArrayList<Record> recordList) {
		this.recordList = recordList;
	}
	
	public static HighScores load(){
		HighScores hs = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(HighScores.class);
			Unmarshaller um = context.createUnmarshaller();
			
			hs = (HighScores) um.unmarshal(new FileReader(FILENAME));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return hs;
	}
	
	public static void save(HighScores hs) {
		try {
			JAXBContext context = JAXBContext.newInstance(HighScores.class);
			Marshaller m = context.createMarshaller();
			m.marshal(hs, new File(FILENAME));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
