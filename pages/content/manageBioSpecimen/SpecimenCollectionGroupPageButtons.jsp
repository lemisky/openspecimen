<!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
								
									<td>
										<table>
											<logic:equal name="<%=Constants.SUBMITTED_FOR%>" value="AddNew">
												<% 	
													isAddNew=true;
												%>
											</logic:equal>
											<tr>
												<td nowrap class="formFieldNoBorders">
													<html:button styleClass="actionButton" 
															property="submitPage" 
															title="Submit Only"
															value="<%=Constants.SPECIMEN_COLLECTION_GROUP_FORWARD_TO_LIST[0][0]%>" 
															onclick="<%=normalSubmit%>"
						  				     	    		styleId = "submitOnly">
											     	</html:button>
												</td>
											
												<logic:notEqual name="<%=Constants.PAGEOF%>" value="<%=Constants.QUERY%>">
												<td nowrap class="formFieldNoBorders">
													<html:button styleClass="actionButton"  
															property="submitPage" 
															title="Submit and Add Specimen"
															value="<%=Constants.SPECIMEN_COLLECTION_GROUP_FORWARD_TO_LIST[1][0]%>" 
															disabled="<%=isAddNew%>" 
															onclick="<%=forwardToSubmit%>"
						  				     	    		styleId = "submitAndAdd">
											     	</html:button>
												</td>
												<td nowrap class="formFieldNoBorders">
													<html:button styleClass="actionButton"  
															property="submitPage" 
															title="Submit and Add Multiple Specimen"
															value="<%=Constants.SPECIMEN_COLLECTION_GROUP_FORWARD_TO_LIST[2][0]%>" 
															disabled="<%=isAddNew%>" 
															onclick="<%=forwardToSubmitForMultipleSpecimen%>">
						  				     	    
											     	</html:button>
												</td>
												</logic:notEqual>		
											</tr>
										</table>
									</td>					
													   			
									
									<%-- td>
										<html:reset styleClass="actionButton" >
											<bean:message  key="buttons.reset" />
										</html:reset>
									</td --%>
								</tr>
							</table>
							<!-- action buttons end -->