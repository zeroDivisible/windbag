"use strict";

window.Windbag = Ember.Application.create();
var attr = DS.attr;

Windbag.Store = DS.Store.extend({
	revision: 13,
	adapter: DS.RESTAdapter.create({
		namespace: 'api/v1'
	})
});


Windbag.Server = DS.Model.extend({
	id: attr("string"),
	protocol: attr("string"),
	server_address: attr("string"),
	server_port: attr("string")
});



