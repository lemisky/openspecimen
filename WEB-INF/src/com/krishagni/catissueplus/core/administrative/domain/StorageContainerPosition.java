package com.krishagni.catissueplus.core.administrative.domain;

import org.springframework.beans.BeanUtils;

import com.krishagni.catissueplus.core.biospecimen.domain.Specimen;

public class StorageContainerPosition {
	private Long id;
	
	private int posOneOrdinal;
	
	private int posTwoOrdinal;
	
	private String posOne;
	
	private String posTwo;
	
	private StorageContainer container;
	
	private Specimen occupyingSpecimen;
	
	private StorageContainer occupyingContainer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPosOneOrdinal() {
		return posOneOrdinal;
	}

	public void setPosOneOrdinal(int posOneOrdinal) {
		this.posOneOrdinal = posOneOrdinal;
	}

	public int getPosTwoOrdinal() {
		return posTwoOrdinal;
	}

	public void setPosTwoOrdinal(int posTwoOrdinal) {
		this.posTwoOrdinal = posTwoOrdinal;
	}

	public String getPosOne() {
		return posOne;
	}

	public void setPosOne(String posOne) {
		this.posOne = posOne;
	}

	public String getPosTwo() {
		return posTwo;
	}

	public void setPosTwo(String posTwo) {
		this.posTwo = posTwo;
	}

	public StorageContainer getContainer() {
		return container;
	}

	public Specimen getOccupyingSpecimen() {
		return occupyingSpecimen;
	}

	public void setOccupyingSpecimen(Specimen occupyingSpecimen) {
		this.occupyingSpecimen = occupyingSpecimen;
	}

	public StorageContainer getOccupyingContainer() {
		return occupyingContainer;
	}

	public void setOccupyingContainer(StorageContainer occupyingContainer) {
		this.occupyingContainer = occupyingContainer;
	}

	public void setContainer(StorageContainer container) {
		this.container = container;
	}
	
	public void update(StorageContainerPosition other) {
		BeanUtils.copyProperties(other, this, new String[] {"id"});
	}
}
