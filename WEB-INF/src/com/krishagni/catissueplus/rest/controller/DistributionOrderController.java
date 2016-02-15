package com.krishagni.catissueplus.rest.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.krishagni.catissueplus.core.administrative.events.DistributionOrderDetail;
import com.krishagni.catissueplus.core.administrative.events.DistributionOrderListCriteria;
import com.krishagni.catissueplus.core.administrative.events.DistributionOrderSummary;
import com.krishagni.catissueplus.core.administrative.events.ReturnedSpecimensDetail;
import com.krishagni.catissueplus.core.administrative.events.SpecimenReturnDetail;
import com.krishagni.catissueplus.core.administrative.events.StorageContainerPositionDetail;
import com.krishagni.catissueplus.core.administrative.services.DistributionOrderService;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.de.events.QueryDataExportResult;

@Controller
@RequestMapping("/distribution-orders")
public class DistributionOrderController {
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private DistributionOrderService distributionService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DistributionOrderSummary> getDistributionOrders(			
		@RequestParam(value = "query", required = false, defaultValue = "") 
		String searchTerm,
		
		@RequestParam(value = "dpShortTitle", required = false, defaultValue = "")
		String dpShortTitle,
		
		@RequestParam(value = "dpId", required = false)
		Long dpId,
		
		@RequestParam(value = "requestor", required = false, defaultValue = "")
		String requestor,
		
		@RequestParam(value = "requestorId", required = false)
		Long requestorId,
		
		@RequestParam(value = "executionDate", required = false) 
		@DateTimeFormat(pattern="yyyy-MM-dd")
		Date executionDate,
		
		@RequestParam(value = "receivingSite", required = false, defaultValue = "")
		String receivingSite,
		
		@RequestParam(value = "receivingInstitute", required = false, defaultValue = "")
		String receivingInstitute,
		
		@RequestParam(value = "startAt", required = false, defaultValue = "0") 
		int startAt,
			
		@RequestParam(value = "maxResults", required = false, defaultValue = "50") 
		int maxResults,

		@RequestParam(value = "includeStats", required = false, defaultValue = "false") 
		boolean includeStats) {
		
		
		DistributionOrderListCriteria listCrit = new DistributionOrderListCriteria()
			.query(searchTerm)
			.dpShortTitle(dpShortTitle)
			.dpId(dpId)
			.requestor(requestor)
			.requestorId(requestorId)
			.executionDate(executionDate)
			.receivingSite(receivingSite)
			.receivingInstitute(receivingInstitute)
			.startAt(startAt)
			.maxResults(maxResults)
			.includeStat(includeStats);
			
		ResponseEvent<List<DistributionOrderSummary>> resp = distributionService.getOrders(getRequest(listCrit));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DistributionOrderDetail getDistribution(@PathVariable("id") Long id) {
		ResponseEvent<DistributionOrderDetail> resp = distributionService.getOrder(getRequest(id));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DistributionOrderDetail createDistribution(@RequestBody DistributionOrderDetail order) {
		order.setId(null);
		ResponseEvent<DistributionOrderDetail> resp = distributionService.createOrder(getRequest(order));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DistributionOrderDetail updateDistribution(
		@PathVariable("id") 
		Long distributionId,
		
		@RequestBody 
		DistributionOrderDetail order) {
		
		order.setId(distributionId);
		ResponseEvent<DistributionOrderDetail> resp = distributionService.updateOrder(getRequest(order));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}/report")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public QueryDataExportResult exportDistributionReport(@PathVariable("id") Long orderId) {
		ResponseEvent<QueryDataExportResult> resp = distributionService.exportReport(getRequest(orderId));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/return-specimens")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DistributionOrderDetail returnSpecimen(
		@PathVariable("id")
		Long orderId,

		@RequestBody
		List<Map<String, Object>> valueMapList) {
		ReturnedSpecimensDetail returnedSpecimensDetail = new ReturnedSpecimensDetail();
		returnedSpecimensDetail.setOrderId(orderId);
		returnedSpecimensDetail.setReturnedSpecimens(getSpecimenReturnDetail(valueMapList));

		ResponseEvent<DistributionOrderDetail> resp = distributionService.returnSpecimen(
			getRequest(returnedSpecimensDetail));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
	
	private <T> RequestEvent<T> getRequest(T payload) {
		return new RequestEvent<T>(payload);
	}

	private List<SpecimenReturnDetail> getSpecimenReturnDetail(List<Map<String, Object>> valueMapList) {
		List<SpecimenReturnDetail> specReturnDetails = new ArrayList<>();
		for (Map<String, Object> valueMap : valueMapList) {
			Map<String, Object> appData = (Map<String, Object>) valueMap.get("appData");
			SpecimenReturnDetail detail = new SpecimenReturnDetail();

			detail.setItemId(Long.parseLong(appData.get("id").toString()));
			detail.setQuantity(new BigDecimal(valueMap.get("quantity").toString()));

			Object location = valueMap.get("location");
			if (location != null) {
				StorageContainerPositionDetail positionDetail = new StorageContainerPositionDetail();
				positionDetail.setContainerId(Long.parseLong(location.toString()));
				detail.setLocation(positionDetail);
			}

			Object userId = valueMap.get("user");
			if (userId != null) {
				detail.setUserId(Long.parseLong(userId.toString()));
			}

			Object time = valueMap.get("time");
			if (time != null) {
				detail.setTime(new Date(Long.parseLong(time.toString())));
			}

			detail.setComments((String)valueMap.get("comments"));

			specReturnDetails.add(detail);
		}

		return specReturnDetails;
	}
}
