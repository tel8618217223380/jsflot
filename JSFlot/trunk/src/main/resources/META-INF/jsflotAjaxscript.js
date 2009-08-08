if (!window.JSFlot) {
    window.JSFlot = {};
}

JSFlot.AJAX = {};

JSFlot.AJAX.getFormData = function getFormData(form, options, event) {
    var dataString = "";

    function addParam(name, value) {
        dataString += (dataString.length > 0 ? "&" : "")
            + escape(name).replace(/\+/g, "%2B") + "="
            + escape(value ? value : "").replace(/\+/g, "%2B");
    }

    if (options._ajaxSingle) {
    	var viewState = document.getElementById("javax.faces.ViewState");
    	addParam("javax.faces.ViewState", viewState.value);
    	addParam(form.id, form.id);
    } else {
	    var elemArray = form.elements;
	    for (var i = 0; i < elemArray.length; i++) {
	        var element = elemArray[i];
	        var elemType = element.type.toUpperCase();
	        var elemName = element.name;
	        if (elemName) {
	            if (elemType == "TEXT"
	                    || elemType == "TEXTAREA"
	                    || elemType == "PASSWORD"
	                    || elemType == "HIDDEN")
	                addParam(elemName, element.value);
	            else if (elemType == "CHECKBOX" && element.checked)
	                addParam(elemName, element.value ? element.value : "On");
	            else if (elemType == "RADIO" && element.checked)
	                addParam(elemName, element.value);
	            else if (elemType.indexOf("SELECT") != -1)
	                for (var j = 0; j < element.options.length; j++) {
	                    var option = element.options[j];
	                    if (option.selected)
	                        addParam(elemName,
	                            option.value ? option.value : option.text);
	                }
	        }
	    }
    }
    
    addParam("org.jsflot.AJAX_REQUEST", "true");
    if (options) {
    	addParam("clientId", options._clientId);
    	addParam("componentValue", options._componentValue);
    	if (options._clickedPosition) {
    		addParam("jsflotClickX", options._clickedPosition.x == 0 ? "0" : options._clickedPosition.x);
    		addParam("jsflotClickY", options._clickedPosition.y == 0 ? "0" : options._clickedPosition.y);
    		addParam("jsflotClickSeries", options._clickedPosition.series.label);
    		addParam("jsflotClickPointLabel", options._clickedPosition.series.data[options._clickedPosition.index][2]);
    		addParam("jsflotClickSeriesIndex", options._clickedPosition.seriesIndex == 0 ? "0" : options._clickedPosition.seriesIndex);
    		addParam("jsflotClickIndex", options._clickedPosition.index == 0 ? "0" : options._clickedPosition.index);
    	}
    }
    
    if (event) {
    	addParam("jsflotEvent", event);
    }
    
    return dataString;
}

JSFlot.AJAX.getEnclosingFormId = function(field){;
var node = field.parentNode;
      while (node != null){
       node = node.parentNode;
       jsflotlog.debug("traversing DOM: " + node.tagName + " :: " + node.id);
      }
     if (node.tagName == 'HTML') {
      return '';
     } 
    return node.id;
}

JSFlot.AJAX.RefreshChart = function(chartId) {
	jsflotlog.debug("RefreshChart called");
	var chart = document.getElementById(chartId);
	jsflotlog.debug("chart: " + chart + " :: " + chartId);

	new Ajax.Request(document.location, {
		method: 'get',
		onSuccess: function(transport) {
			jsflotlog.debug("Get XHR successful.");
			JSFlot.AJAX.processXHRResponse(transport, chartId);
		},
		onFailure: function() { alert('AJAX request to refresh chart failed.'); } 
	});
	jsflotlog.debug("Finished refreshing Chart");
}

JSFlot.AJAX.Submit = function(formId, event, url, options) {
	var query = JSFlot.AJAX.PrepareQuery(formId);
	var viewState = document.getElementById("javax.faces.ViewState");
	var encodedViewState = encodeURI(viewState.value);
	var rexp = new RegExp("\\+", "g");
	encodedViewState = encodedViewState.replace(rexp, "%2B");
	
	if (query) {
		jsflotlog.debug("NEW AJAX REQUEST !!! with form: " + (query._form.id || query._form.name || query._form) + " URL: " + url);
		var params = JSFlot.AJAX.getFormData(document.getElementById(formId), options, event);
		
		new Ajax.Request(url, {
			method: 'post',
			contentType: "application/x-www-form-urlencoded",
			parameters: params,
			onSuccess: function(transport) {
				jsflotlog.debug("XHR successful.");
				JSFlot.AJAX.processXHRResponse(transport, options._rerenderID, options._otherRerenderIDs);
			},
			onFailure: function() { alert('AJAX Request failed'); } 
		});
	}
}

JSFlot.AJAX.processXHRResponse = function(transport, rerenderId, otherRerenderIDs) {
	var ajaxResponse = Try.these(
		function() { return new DOMParser().parseFromString(transport.responseText, 'text/xml'); },
		function() { var xmldom = new ActiveXObject('Microsoft.XMLDOM'); xmldom.loadXML(transport.responseText); return xmldom; }
	);

	jsflotlog.debug('looking for the enclosingDiv: ' + rerenderId);
	JSFlot.AJAX.updateElement(ajaxResponse, rerenderId);
	//ReRender complete. Trigger Prototype Event options._rerenderID
	var elementDiv = ajaxResponse.getElementById(rerenderId);
	if (elementDiv) {
		//jsflotlog.debug('jsflot:ajaxLoaded trigger');
		//elementDiv.fire('jsflot:ajaxLoaded', '');
		//jsflotlog.debug('jsflot:ajaxLoaded triggered');
	}
	
	//Process other RerenderIDs
	jsflotlog.debug('looking for the other ReRenderIDs: ' + otherRerenderIDs);
	if (otherRerenderIDs.indexOf(",") != -1)  {
		//Multiple reRender IDs
		var rerenderIDs = otherRerenderIDs.split(",");
		jsflotlog.debug("multiple rerenderIDs: " + rerenderIDs);
		for (i = 0; i < rerenderIDs.length; i++) {
			JSFlot.AJAX.updateElement(ajaxResponse, rerenderIDs[i]);
		}
	} else {
		//Single reRender ID
		JSFlot.AJAX.updateElement(ajaxResponse, otherRerenderIDs);
	}
	
	
}

JSFlot.AJAX.updateElement = function(ajaxResponse, reRenderID) {
	jsflotlog.debug("looking for: " + reRenderID);
	var elementDiv = ajaxResponse.getElementById(reRenderID);
	jsflotlog.debug("Found: " + elementDiv);
	if (elementDiv) {
		var elemContents = elementDiv.innerHTML;
		$(reRenderID).update(elemContents);
	}
}

JSFlot.AJAX.PrepareQuery = function(formId) {
    //jsflotlog.debug("Query preparation for form '" + formId + "' requested");
    var form = window.document.getElementById(formId);
    
    var tosend = new JSFlot.AJAXQuery(form);
    return tosend;
};

JSFlot.AJAXQuery = function(form) {
    this._query = {
        AJAXREQUEST: form.id
    };
    this._form = form;
    this._actionUrl = (this._form.action) ? this._form.action: this._form;
};

JSFlot.AJAX.yaxisConverter = function(yValue) {
	var retVal = yValue;
	if (yValue > 1000 && yValue < 1000000) {
		retVal = yValue / 1000;
		retVal = retVal + "k";
	} 
	if (yValue > 1000000 && yValue < 1000000000) {
		retVal = yValue / 1000000;
		retVal = retVal + "m";
	}
	if (yValue > 1000000000 && yValue < 1000000000000) {
		retVal = yValue / 1000000000;
		retVal = retVal + "t";
	}
	return retVal;
}

JSFlot.AJAX.pointLabelFormatter = function(pointObject) {
	var retVal = "x = " + pointObject.x + "<br />y = " + JSFlot.AJAX.yaxisConverter(pointObject.y);
	
	if (pointObject.series.data[pointObject.index][2]) {
		retVal = retVal + "<br/>Label = " + pointObject.series.data[pointObject.index][2];
	}
	return retVal;
}


JSFlot.AJAXQuery.prototype = {
	_form: null,
    _actionUrl: null,
    _query: {},
    _pageBase: window.location.protocol + "//" + window.location.host
}

JSFlot.Options = function(clientId, componentValue, flotContainerId, rerenderId) {
	this._clientId = clientId;
	this._componentValue = componentValue;
	this._flotContainerID = flotContainerId;
	this._rerenderID = rerenderId;
};

JSFlot.Options.prototype = {
	_clientId: null,
	_componentValue: null,
	_clickedPosition: null,
	_rerenderID: null,
	_flotContainerID: null,
	_ajaxSingle: false,
	_otherRerenderIDs: null
}