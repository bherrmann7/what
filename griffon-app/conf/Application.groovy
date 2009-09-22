mvcGroups {
	root {
		model="WhatModel"
		controller="WhatController"
		view="WhatView"
	}
}
application {
	title="What"
	startupGroups=["root"]
	autoShutdown=true
}
