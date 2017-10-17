
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum StorageContainerErrorCode implements ErrorCode {
	NOT_FOUND,
	
	NAME_REQUIRED,

	ID_NAME_OR_BARCODE_REQ,
	
	SRC_ID_OR_NAME_REQ,
	
	DUP_NAME,
	
	DUP_BARCODE,

	INVALID_POSITION_LABELING_MODE,

	INVALID_DIMENSION_CAPACITY,
	
	INVALID_DIMENSION_LABELING_SCHEME,
	
	REQUIRED_SITE_OR_PARENT_CONT,
	
	INVALID_SITE_AND_PARENT_CONT,
	
	NO_FREE_SPACE,
	
	CANNOT_SHRINK_CONTAINER,
	
	PARENT_CONT_NOT_FOUND,

	CANNOT_HOLD_CONTAINER,
	
	INVALID_NUMBER_POSITION,
	
	INVALID_ALPHA_POSITION,
	
	INVALID_ROMAN_POSITION,
	
	REF_ENTITY_FOUND,
	
	HIERARCHY_CONTAINS_CYCLE,
	
	CANNOT_HOLD_SPECIMEN,
	
	INVALID_ENTITY_TYPE,
	
	OCCUPYING_ENTITY_ID_OR_NAME_REQUIRED,

	INV_POS,
	
	INVALID_CPS, 
	
	TYPE_REQUIRED,

	INCORRECT_NAME_FMT,

	INVALID_CELL_DISP_PROP,

	DL_TO_REG_NA,

	REG_TO_DL_NA,

	DIMLESS_NO_MAP,

	INVALID_CAPACITY,

	AUTOMATED_NOT_DIMENSIONLESS,

	SPMNS_RPT_NOT_CONFIGURED,

	INV_CONT_SEL_STRATEGY,

	INV_CONT_SEL_RULE,

	SITE_CONT_VIOLATED,

	DL_POS_BLK_NP,

	POS_OCCUPIED;

	@Override
	public String code() {
		return "STORAGE_CONTAINER_" + this.name();
	}

}
