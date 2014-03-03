"use strict";

window.Windbag = Ember.Application.create();
var attr = DS.attr;

Windbag.adapter = DS.RESTAdapter.extend({
	namespace: 'api/v1'
});

Windbag.Store = DS.Store.extend({
	revision: 14,
	adapter: Windbag.adapter.create()
});


Windbag.Server = DS.Model.extend({
	id: attr("string"),
	protocol: attr("string"),
	server_address: attr("string"),
	server_port: attr("string")
});



