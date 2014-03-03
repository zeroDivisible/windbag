Windbag.Router.map(function () {
	this.resource('index', { path: '/' });
});


Windbag.IndexRoute = Ember.Route.extend({
	model: function () {
		return this.store.find('server');
	}
});
