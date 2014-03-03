"use strict";

window.Windbag = Ember.Application.create();

/*
Windbag.adapter = DS.RESTAdapter.extend({
	namespace: 'api/v1'
});
*/

Windbag.Store = DS.Store.extend({
	revision: 13,
	adapter: DS.FixtureAdapter
});


var attr = DS.attr;

Windbag.Server = DS.Model.extend({
	protocol: attr("string"),
	server_address: attr("string"),
	server_port: attr("string")
});

Windbag.Server.FIXTURES = [
	{ id: "devvm", protocol: "EPP", server_address: "192.168.33.15", server_port: "700"},
	{ id: "devvm2", protocol: "EPP", server_address: "192.168.33.20", server_port: "9700"}
];
