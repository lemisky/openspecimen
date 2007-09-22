package edu.wustl.catissuecore.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import edu.wustl.catissuecore.actionForm.ViewSpecimenSummaryForm;
import edu.wustl.catissuecore.bizlogic.BizLogicFactory;
import edu.wustl.catissuecore.domain.CollectionProtocol;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.common.action.BaseAction;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;

public class UpdateCollectionProtocolAction extends BaseAction {


	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String target = Constants.SUCCESS;

		try{ 
			ViewSpecimenSummaryForm specimenSummaryForm = (ViewSpecimenSummaryForm) form;
			CollectionProtocol collectionProtocol = (CollectionProtocol)
								specimenSummaryForm.getSummaryObject();
			
			IBizLogic bizLogic =BizLogicFactory.getInstance().getBizLogic(Constants.COLLECTION_PROTOCOL_FORM_ID);
			List cpList =bizLogic.retrieve(CollectionProtocol.class.getName(), "id", collectionProtocol.getId());

			if (cpList != null && !cpList.isEmpty())
			{
				CollectionProtocol oldCollectionProtocol = (CollectionProtocol) cpList.get(0);
				HttpSession session = request.getSession();
				SessionDataBean sessionDataBean = (SessionDataBean)
								session.getAttribute(Constants.SESSION_DATA);
				
				bizLogic.update(collectionProtocol, oldCollectionProtocol, 
						Constants.HIBERNATE_DAO, sessionDataBean);
			}
			
			target = Constants.SUCCESS;
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"object.edit.successOnly","Collection Protocol"));
			saveMessages(request, actionMessages);
			
		}catch (Exception exception){
			ActionErrors actionErrors = new ActionErrors();
			actionErrors.add(actionErrors.GLOBAL_MESSAGE, new ActionError(
					"errors.item",exception.getMessage()));
			saveErrors(request, actionErrors);						
			target = Constants.FAILURE;
		}
		return mapping.findForward(target);
	}
}
