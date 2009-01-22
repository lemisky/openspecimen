package edu.wustl.catissuecore.printservicemodule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wustl.catissuecore.domain.Specimen;
import edu.wustl.catissuecore.printserviceclient.LabelPrinter;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.catissuecore.domain.User;
/**
 * This Class is used to define method for Specimen label printing
 * @author falguni_sachde
 */
public class SpecimenLabelPrinterImpl implements LabelPrinter {


	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.printserviceclient.LabelPrinter#printLabel(edu.wustl.common.domain.AbstractDomainObject, java.lang.String, gov.nih.nci.security.authorization.domainobjects.User)
	 */
	public boolean printLabel(AbstractDomainObject abstractDomainObject, String ipAddress, User userObj,String printerType,String printerLocation) {
		
		ArrayList listMap = new ArrayList ();
		//createObjectMap(abstractDomainObject,listMap);
		createObjectMap(abstractDomainObject,listMap,printerType,printerLocation);
		try
		{
			  PrintServiceInputParserInterface objParser = new PrintServiceInputXMLParser();
			  return objParser.callPrintService(listMap);
			
		}
		catch(Exception exp)
		{
			return false;
			
		}
		
	}	

	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.print.LabelPrinter#printLabel(java.util.List, java.lang.String, gov.nih.nci.security.authorization.domainobjects.User)
	 */
	public boolean printLabel(List<AbstractDomainObject> abstractDomainObjectList, String ipAddress, User userObj,String printerType,String printerLocation)
	{
		//Iterate through all objects in List ,crate map of each object.
		ArrayList listMap = new ArrayList ();

		for(int cnt=0;cnt < abstractDomainObjectList.size();cnt++)
		{
			AbstractDomainObject abstractDomainObject = abstractDomainObjectList.get(cnt); 
			//createObjectMap(abstractDomainObject,listMap);
			createObjectMap(abstractDomainObject,listMap,printerType,printerLocation);
		}
		try
		{
			PrintServiceInputParserInterface objParser = new PrintServiceInputXMLParser();
			return objParser.callPrintService(listMap);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return false;	
		}
	}
	/**
	 * @param abstractDomainObject Specimen Object
	 * @param listMap List of Specimen details including all child specimen.
	 */
	void createObjectMap(AbstractDomainObject abstractDomainObject,ArrayList listMap,String printerType,String printerLocation)
	{
		
		if(abstractDomainObject instanceof Specimen)
		{
					
			Specimen objSpecimen = (Specimen)abstractDomainObject;			
			Collection specimenCollection  = objSpecimen.getChildSpecimenCollection();
			Iterator itr = specimenCollection.iterator();
			ArrayList specimenList = new ArrayList();
			specimenList.add(objSpecimen);
			getAllSpecimenList(objSpecimen,specimenList);
			for(int cnt=0;cnt < specimenList.size();cnt++)
			{
				Specimen obj = (Specimen)specimenList.get(cnt);
				LinkedHashMap dataMap = new LinkedHashMap();
				String label= obj.getLabel();
				String barcode = obj.getBarcode();
		
				dataMap.put("class",obj.getClassName());
				dataMap.put("id",obj.getId().toString());
				dataMap.put("label",label);
				dataMap.put("barcode",barcode);
				dataMap.put("printerType",printerType);
				dataMap.put("printerLocation",printerLocation);
				listMap.add(dataMap);
			}
		}
	
	}
	/**
	 * @param objSpecimen Specimen Object
	 * @param specimenList List of Specimen including all child specimen.
	 */
	void getAllSpecimenList(Specimen objSpecimen,List specimenList)
	{
		
		Collection childSpecimen = objSpecimen.getChildSpecimenCollection();
		if(childSpecimen!= null && childSpecimen.size() >0)
		{
			
			Iterator itr = childSpecimen.iterator();
			while(itr.hasNext())
			{
				Specimen specimen = (Specimen)itr.next();
				specimenList.add(specimen);
				getAllSpecimenList(specimen,specimenList);
			}
		}
	}
	
}
