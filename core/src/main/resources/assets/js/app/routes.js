Windbag.Router.map(function () {
	this.resource('index', { path: '/' });
});


Windbag.IndexRoute = Ember.Route.extend({
	model: function () {
		this.store.find('server').then(function (servers) {
			console.log(servers);
		});
	}
});
