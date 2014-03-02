"use strict";

window.Windbag = Ember.Application.create();

Windbag.Store = DS.Store.extend({
	revision: 13,
	adapter: DS.RESTAdapter.create({
		namespace: 'api/v1'
	})
});

var attr = DS.attr;

Windbag.Server = DS.Model.extend({
	server_name: attr("string")
});


