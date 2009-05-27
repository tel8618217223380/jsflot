if (!window.JSFlot) {
    window.JSFlot = {};
}

JSFlot.AJAX = {};

JSFlot.AJAX.getFormData = function getFormData(form, options) {
    var dataString = "";

    function addParam(name, value) {
        dataString += (dataString.length > 0 ? "&" : "")
            + escape(name).replace(/\+/g, "%2B") + "="
            + escape(value ? value : "").replace(/\+/g, "%2B");
    }

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
    
    addParam("org.jsflot.AJAX_REQUEST", "true");
    addParam("clientId", options._clientId);
    addParam("componentValue", options._componentValue);
    
    return dataString;
}

JSFlot.AJAX.Submit = function(formId, event, url, options) {
	var query = JSFlot.AJAX.PrepareQuery(formId);
	var viewState = document.getElementById("javax.faces.ViewState");
	var encodedViewState = encodeURI(viewState.value);
	var rexp = new RegExp("\\+", "g");
	encodedViewState = encodedViewState.replace(rexp, "%2B");
	
	if (query) {
		log.debug("NEW AJAX REQUEST !!! with form: " + (query._form.id || query._form.name || query._form) + " URL: " + url);
		var params = JSFlot.AJAX.getFormData(document.getElementById(formId), options);
		
		new Ajax.Request(url, {
			method: 'post',
			contentType: "application/x-www-form-urlencoded",
			parameters: params,
			onSuccess: function(transport) {
				log.debug("XHR successful.");
				JSFlot.AJAX.processXHRResponse(transport);
			},



			onFailure: function() { alert('AJAX Request failed'); } 
		});
	}
}

JSFlot.AJAX.processXHRResponse = function(transport) {
	var ajaxResponse = Try.these(
		function() { return new DOMParser().parseFromString(transport.responseText, 'text/xml'); },
		function() { var xmldom = new ActiveXObject('Microsoft.XMLDOM'); xmldom.loadXML(transport.responseText); return xmldom; }
	);

	log.debug('looking for the enclosingDiv');
	var flotchartdiv = ajaxResponse.getElementById('valueTimeChart_enclosingDiv');
	log.debug("Found: " + flotchartdiv);
	
	log.debug('looked for the enclosingDiv');
	$('valueTimeChart_enclosingDiv').update(flotchartdiv.innerHTML);
	
	//log.debug("enclosingDiv: " + flotchartdivcontents);
	
	//var json = transport.responseText.evalJSON();
	//if(json.series && json.options && json.componentId) {
	//	var f = Flotr.draw($(json.componentId), json.series, json.options);
	//}
}

JSFlot.AJAX.PrepareQuery = function(formId) {
    //log.debug("Query preparation for form '" + formId + "' requested");
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


JSFlot.AJAXQuery.prototype = {
	_form: null,
    _actionUrl: null,
    _query: {},
    _pageBase: window.location.protocol + "//" + window.location.host
}

JSFlot.Options = function(clientId, componentValue, flotContainerId) {
	this._clientId = clientId;
	this._componentValue = componentValue;
	this._flotContainerID = flotContainerId;
};

JSFlot.Options.prototype = {
	_clientId: null,
	_componentValue: null,
	_rerenderID: null,
	_flotContainerID: null
}
