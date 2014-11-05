
package com.krishagni.catissueplus.core.biospecimen.repository;

import java.util.List;

import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.biospecimen.events.CollectionProtocolSummary;
import com.krishagni.catissueplus.core.common.repository.Dao;

public interface CollectionProtocolDao extends Dao<CollectionProtocol> {

	public List<CollectionProtocolSummary> getAllCollectionProtocols(boolean includePi, boolean includeStats);

//	public CollectionProtocol getCollectionProtocol(Long cpId);
//
//	public CollectionProtocolEvent getCpe(Long cpeId);
//
//	public SpecimenRequirement getSpecimenRequirement(Long requirementId);
//
//	public com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol getCPByTitle(String title);
//	
//	public com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol getCPByShortTitle(String shortTitle);
//
//	public CollectionProtocol getCpByShortTitle(String shortTitle);
//	
//	public CollectionProtocol getCpByTitle(String shortTitle);
//	
//	public List<SpecimenRequirement> getSpecimenRequirments(Long cpeId);
//
//	public List<CollectionProtocolSummary> getChildProtocols(Long cpId);
//	
//	public CollectionProtocolEvent getCpeByCollectionPointLabel(Long cpId, String collectionPointLabel);
}
